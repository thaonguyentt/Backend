package base.api.book.service;

import base.api.book.dto.*;
import base.api.book.entity.*;
import base.api.book.entity.support.CopyStatus;
import base.api.book.entity.support.LeaseOrderStatus;
import base.api.book.entity.support.ListingStatus;
import base.api.book.exception.LeaseCanNotCancel;
import base.api.book.exception.ListingNotAvailableException;
import base.api.book.mapper.LeaseOrderDetailMapper;
import base.api.book.mapper.LeaseOrderMapper;
import base.api.book.repository.CopyRepository;
import base.api.book.repository.LeaseOrderRepository;
import base.api.book.repository.ListingRepository;
import base.api.payment.dto.PaymentDto;
import base.api.payment.entity.Payment;
import base.api.payment.entity.PaymentMethod;
import base.api.payment.entity.PaymentStatus;
import base.api.payment.repository.PaymentRepository;
import base.api.payment.service.PaymentService;
import base.api.system.security.JwtAuthenticationToken;
import base.api.system.security.SecurityUtils;
import base.api.user.UserDto;
import base.api.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class LeaseOrderService {

  final LeaseOrderRepository leaseOrderRepository;
  final ListingRepository listingRepository;
  final LeaseOrderDetailMapper leaseOrderDetailMapper;
  final LeaseOrderMapper leaseOrderMapper;
  private final CopyRepository copyRepository;
  private final PaymentService paymentService;
  private final UserService userService;
  private final ListingService listingService;
  private final CopyService copyService;
  private final BookService bookService;
  private final ReviewService reviewService;
  private PaymentRepository paymentRepository;

  public List<LeaseOrderDto> getLeaseOrderByLesseeId(Long id) {
    return leaseOrderRepository.findLeaseOrderByLesseeId(id)
      .stream()
      .map(leaseOrderMapper::toDto)
      .collect(Collectors.toList());
  }

  public List<LeaseOrderDto> getLeaseOrderByLesseeIdAndStatus(Long id, List<LeaseOrderStatus> leaseOrderStatus) {
    return leaseOrderRepository.findByLesseeIdAndStatusIn(id, leaseOrderStatus)
            .stream()
            .map(leaseOrderMapper::toDto)
            .collect(Collectors.toList());
  }

  public LeaseOrderDto updateLeaseOrderStatus (Authentication auth, Long id, LeaseOrderStatus newStatus) {
    LeaseOrder leaseOrder = leaseOrderRepository.findById(id).get();
    LeaseOrder updatedLeaseOrder = switch (newStatus) {
        case ORDERED_PAYMENT_PENDING -> leaseOrder;
        case CANCELED -> changeOrderStatusCancel(auth, leaseOrder);
        case USER_PAID -> changeOrderStatusUserPaid(auth, leaseOrder);
        case PAYMENT_SUCCESS -> changeOrderStatusPaymentSuccess(auth, leaseOrder);
        case DELIVERED -> null;
        case LATE_RETURN -> changeOrderStatusLateReturn(auth, leaseOrder);
        case RETURNING -> changeOrderStatusReturning(auth, leaseOrder);
        case RETURNED -> changeOrderStatusReturned(auth, leaseOrder);
        case DEPOSIT_RETURNED -> changeOrderStatusDepositReturned(auth, leaseOrder);
        case PAID_OWNER -> changeOrderStatusPaidOwner(auth, leaseOrder);
    };

    return leaseOrderMapper.toDto(updatedLeaseOrder);
  }


  private LeaseOrder changeOrderStatusCancel(@NonNull Authentication auth, LeaseOrder leaseOrder) {
//    SecurityUtils.requireHasRoleAny(auth, "USER", "ADMIN");

    if (LeaseOrderStatus.ORDERED_PAYMENT_PENDING.equals(leaseOrder.getStatus())) {
      Listing listing = listingRepository.findById(leaseOrder.getListingId()).get();
      listing.setListingStatus(ListingStatus.AVAILABLE);
      Copy copy = listing.getCopy();
      copy.setCopyStatus(CopyStatus.LISTED);
      leaseOrder.setStatus(LeaseOrderStatus.CANCELED);
      return leaseOrderRepository.save(leaseOrder);
    } else {
      throw new LeaseCanNotCancel("lease order can not cancel");
    }
  }

  private LeaseOrder changeOrderStatusUserPaid(@NonNull Authentication auth, LeaseOrder leaseOrder) {
    SecurityUtils.requireHasRoleAny(auth, "SYSTEM", "ADMIN");

    // Check if user already paid
    PaymentDto userPay = paymentService.getPaymentById(leaseOrder.getLeaseAndDepositPaymentId());
    if (!PaymentStatus.SUCCEEDED.equals(userPay.paymentStatus())) {
      throw new IllegalStateException("Lease and deposit payment of order " + leaseOrder.getId() + " haven't succeeded");
    }
    leaseOrder.setStatus(LeaseOrderStatus.USER_PAID);
    return leaseOrderRepository.save(leaseOrder);
  }

  private LeaseOrder changeOrderStatusPaymentSuccess(@NonNull Authentication auth, LeaseOrder leaseOrder) {
    SecurityUtils.requireHasRoleAny(auth, "SYSTEM", "ADMIN");

    leaseOrder.setStatus(LeaseOrderStatus.PAYMENT_SUCCESS);
    return leaseOrderRepository.save(leaseOrder);
  }

  private LeaseOrder changeOrderStatusLateReturn(Authentication auth, LeaseOrder leaseOrder) {
    SecurityUtils.requireHasRoleAny(auth, "SYSTEM", "ADMIN");

    // TODO Maybe check if order is really late return
    leaseOrder.setStatus(LeaseOrderStatus.LATE_RETURN);
    return leaseOrderRepository.save(leaseOrder);
  }

  private LeaseOrder changeOrderStatusReturning(Authentication auth, LeaseOrder leaseOrder) {
    SecurityUtils.requireHasRoleAny(auth, "USER", "SYSTEM", "ADMIN");

    Listing listing = listingRepository.findById(leaseOrder.getListingId()).get();
    if (leaseOrder.getToDate().isAfter(LocalDate.now())
            && leaseOrder.getFromDate().isBefore(LocalDate.now())) {
      leaseOrder.setToDate(LocalDate.now());
      BigDecimal totalLeaseFee = listing.getLeaseRate()
              .multiply(BigDecimal.valueOf(Duration.between(
                              leaseOrder.getFromDate().atStartOfDay(),
                              leaseOrder.getToDate().atStartOfDay())
                      .toDays()));
      leaseOrder.setTotalLeaseFee(totalLeaseFee);
      leaseOrder.setStatus(LeaseOrderStatus.RETURNING);
      LeaseOrder savedLeaseOrder = leaseOrderRepository.save(leaseOrder);
      return savedLeaseOrder;
    }
    else if ((leaseOrder.getFromDate().isEqual(LocalDate.now()))) {
      leaseOrder.setToDate(LocalDate.now());
      leaseOrder.setTotalLeaseFee(listing.getLeaseRate());
      leaseOrder.setStatus(LeaseOrderStatus.RETURNING);
      LeaseOrder savedLeaseOrder = leaseOrderRepository.save(leaseOrder);
      return savedLeaseOrder;
    } else if (leaseOrder.getFromDate().isEqual(LocalDate.now())) {
      return null;
    }
      else {
      throw new LeaseCanNotCancel("lease order can not return");
    }
  }

  //TODO calculate amount of fee
  private LeaseOrder changeOrderStatusReturned(Authentication auth, LeaseOrder leaseOrder) {
    SecurityUtils.requireHasRoleAny(auth, "USER", "SYSTEM", "ADMIN");

    Listing listing = listingRepository.findById(leaseOrder.getListingId()).get();
    listing.setListingStatus(ListingStatus.AVAILABLE);
    Copy copy = listing.getCopy();
    copy.setCopyStatus(CopyStatus.LISTED);
    leaseOrder.setStatus(LeaseOrderStatus.RETURNED);

    // TODO create 2 payment
    if (auth == null || !auth.isAuthenticated()) {
      auth = SecurityContextHolder.getContext().getAuthentication();
    }
    // Create Pay-Owner payment
    PaymentDto payOwner = paymentService.create(
      auth,
      PaymentDto.builder()
        .payerId(0L)
        .payeeId(leaseOrder.getLessorId())
        .amount(BigDecimal.valueOf(999999L))
        .currency("VND")
        .paymentMethod(PaymentMethod.BANK_TRANSFER)
        .description("Pay books owner of order " + leaseOrder.getId())
        .build()
    );
    // Refund to renter payment
    PaymentDto refundDeposit = paymentService.create(
      auth,
      PaymentDto.builder()
        .payerId(0L)
        .payeeId(leaseOrder.getLesseeId())
        .amount(BigDecimal.valueOf(9999999L))
        .currency("VND")
        .paymentMethod(PaymentMethod.BANK_TRANSFER)
        .description("Return deposit of order " + leaseOrder.getId())
        .build()
    );

    leaseOrder.setPayOwnerPaymentId(payOwner.id());
    leaseOrder.setRefundDepositPaymentId(refundDeposit.id());
    return leaseOrderRepository.save(leaseOrder);
  }

  private LeaseOrder changeOrderStatusDepositReturned(Authentication auth, LeaseOrder leaseOrder) {
    SecurityUtils.requireHasRoleAny(auth, "SYSTEM", "ADMIN");

    Long refundPaymentId = leaseOrder.getRefundDepositPaymentId();
    Payment refundPayment = paymentRepository.getReferenceById(refundPaymentId);
    refundPayment.setPaymentStatus(PaymentStatus.SUCCEEDED);
    paymentRepository.save(refundPayment);

    leaseOrder.setStatus(LeaseOrderStatus.DEPOSIT_RETURNED);
    return leaseOrderRepository.save(leaseOrder);
  }

  private LeaseOrder changeOrderStatusPaidOwner(Authentication auth, LeaseOrder leaseOrder) {
    SecurityUtils.requireHasRoleAny(auth, "SYSTEM", "ADMIN");

    Long payOwnerPaymentId = leaseOrder.getPayOwnerPaymentId();
    Payment payOwnerPayment = paymentRepository.getReferenceById(payOwnerPaymentId);
    payOwnerPayment.setPaymentStatus(PaymentStatus.SUCCEEDED);
    paymentRepository.save(payOwnerPayment);

    leaseOrder.setStatus(LeaseOrderStatus.PAID_OWNER);
    return leaseOrderRepository.save(leaseOrder);
  }

  public List<LeaseOrderDto> getLeaseOrderByLessorIdAndStatus(Long id, List<LeaseOrderStatus> leaseOrderStatus) {
    return leaseOrderRepository.findByLessorIdAndStatusIn(id, leaseOrderStatus)
            .stream()
            .map(leaseOrderMapper::toDto)
            .collect(Collectors.toList());
  }


  public List<LeaseOrderDto> getLeaseOrderByLessorId (Long id) {
    return leaseOrderRepository.findLeseOrderByLessorId(id)
            .stream()
            .map(leaseOrderMapper::toDto)
            .collect(Collectors.toList());
  }

  public LeaseOrderDto getLeaseOrderById (Long id) {
    return leaseOrderRepository.findById(id)
            .map(leaseOrderMapper::toDto).orElse(null);
  }



  public LeaseOrderDto createLeaseOrder(Authentication auth, LeaseOrderCreateRequest requestDto) {
    SecurityUtils.requireAuthentication(auth);
    // TODO tạo service handle anonymous user
    // FIXME anonymous user => auth.getPrincipal == "anonymousUser" => parse số => lỗi
    Long userId = Long.valueOf((String)auth.getPrincipal());

    Listing listing = listingRepository.findById(requestDto.listingId()).get();
    if (! ListingStatus.AVAILABLE.equals(listing.getListingStatus())) {
      throw new ListingNotAvailableException("Listing " + requestDto.listingId() + " is not available.");
    }

    Long lessorId = listing.getOwner().getId();
    listing.setListingStatus(ListingStatus.LEASED);
    Copy copy = listing.getCopy();
    copy.setCopyStatus(CopyStatus.LEASED);

    Listing updatedListing = listingRepository.save(listing);
    Copy updatedCopy = copyRepository.save(copy);
    Book book = copy.getBook();

    // Tính các loại phí
    BigDecimal totalLeaseFee = listing.getLeaseRate()
      .multiply(BigDecimal.valueOf(Duration.between(
        requestDto.fromDate().atStartOfDay(),
        requestDto.toDate().atStartOfDay())
        .toDays()));
    BigDecimal totalDeposit = listing.getDepositFee();
    BigDecimal totalPenaltyRate = listing.getPenaltyRate();

    LeaseOrder newLeaseOrder = new LeaseOrder();
    newLeaseOrder.setListingId(updatedListing.getId());
    newLeaseOrder.setStatus(LeaseOrderStatus.ORDERED_PAYMENT_PENDING);
    if (requestDto.paymentMethod().equals(PaymentMethod.VNPAY)) {
      newLeaseOrder.setStatus(LeaseOrderStatus.PAYMENT_SUCCESS);
    }
    newLeaseOrder.setLessorId(lessorId);
    newLeaseOrder.setLessorAddress(listing.getAddress());
    newLeaseOrder.setLesseeId(userId);
    newLeaseOrder.setLesseeAddress(requestDto.lesseeAddress());
    newLeaseOrder.setFromDate(requestDto.fromDate());
    newLeaseOrder.setToDate(requestDto.toDate());
    newLeaseOrder.setTotalLeaseFee(totalLeaseFee);
    newLeaseOrder.setTotalDeposit(totalDeposit);
    newLeaseOrder.setTotalPenaltyRate(totalPenaltyRate);
    newLeaseOrder.setPaymentMethod(requestDto.paymentMethod());
    newLeaseOrder.setCreatedDate(LocalDate.now());
    newLeaseOrder.setLeaseOrderDetails(
      // Tạo lease order detail
      Set.of(LeaseOrderDetail.builder()
              .title(book.getTitle())
          .leaseOrder(newLeaseOrder)
          .listing(listing)
          .copy(listing.getCopy())
          .leaseRate(listing.getLeaseRate())
          .depositFee(listing.getDepositFee())
          .penaltyRate(listing.getPenaltyRate())
        .build()
      )
    );

    // Create Payment
    PaymentDto newPayment = paymentService.create(
      auth,
      PaymentDto.builder()
        .amount(totalLeaseFee.add(totalDeposit))
        .currency("VND")
        .payerId(userId) // Pay từ userId cho hệ thống
        .payeeId(0L) // Pay cho hệ thống tạm cho payeeId = 0
        .description("Lease fee and deposit")
        .paymentMethod(PaymentMethod.COD)
        .build()
    );
    newLeaseOrder.setLeaseAndDepositPaymentId(newPayment.id());

    var createdLO = leaseOrderRepository.save(newLeaseOrder);

    var newLeaseOrderDto = leaseOrderMapper.toDto(createdLO);
    // Add more info

    return newLeaseOrderDto;
  }

  public LeaseOrderDtoDetail getDetailLeaseOrderById (Long id) {
    LeaseOrder leaseOrder = leaseOrderRepository.findById(id).get();
    LeaseOrderDto leaseOrderDto = leaseOrderMapper.toDto(leaseOrder);
    UserDto lessor = userService.getUserById(leaseOrder.getLessorId());
    UserDto lessee = userService.getUserById(leaseOrder.getLesseeId());
    ListingDto listingDto = listingService.getListingById(leaseOrder.getListingId());
    if (listingDto == null) {
      return null;
    }
    CopyDto copy = copyService.getCopyById(listingDto.copyId());
    BookDto book = bookService.getBookById(copy.bookId());
    List<ReviewDto> reviews = reviewService.getReviewByOwnerId(listingDto.ownerId());
    Long bookOwned = listingService.countListingByOwner(listingDto.ownerId());
    Long bookLeasing = listingService.countListingByOwnerAndStatus(listingDto.ownerId());
    UserDto user = userService.getUserById(listingDto.ownerId());
    BigDecimal totalPenaltyFee = leaseOrder.getTotalPenaltyRate()
            .multiply(BigDecimal.valueOf(Duration.between(
                            leaseOrder.getToDate().atStartOfDay(),
                            LocalDate.now().atStartOfDay())
                    .toDays()));
    ListingDetailDto listing = new ListingDetailDto(
            listingDto.id(),
            user,
//                userService.getUserById(listingDto.ownerId()).id(),
            listingDto.quantity(),
            listingDto.address(),
            listingDto.leaseRate(),
            listingDto.depositFee(),
            listingDto.penaltyRate(),
            listingDto.description(),
            copy,
            book,
            reviews,
            bookOwned,
            bookLeasing
    );
    LeaseOrderDtoDetail result = new LeaseOrderDtoDetail(leaseOrderDto, listing, lessor, lessee,totalPenaltyFee);
    return result;
  }

  public void setStatusOnLateReturnOrder() {
    List<LeaseOrder> lateLeaseOrders = leaseOrderRepository.findLateReturnLeaseOrder();
    lateLeaseOrders.forEach(order -> {
      order.setStatus(LeaseOrderStatus.LATE_RETURN);
    });
    leaseOrderRepository.saveAll(lateLeaseOrders);
  }

  public void cancelOrderOnLatePayment() {
    List<LeaseOrder> latePaymentOrders = leaseOrderRepository.findLatePaymentLeaseOrder();
    latePaymentOrders.forEach(order -> changeOrderStatusCancel(order));
    leaseOrderRepository.saveAll(latePaymentOrders);
  }

//  public void chargeLateFees () {
//    List<LeaseOrder> listReturnLate = leaseOrderRepository.findLeaseOrderByStatus(LeaseOrderStatus.LATE_RETURN);
//    listReturnLate.forEach(order -> {
//      BigDecimal totalPenaltyRate = order.getTotalPenaltyRate();
//      BigDecimal leaseOrderPenaltyRate = order.getLeaseOrderDetails().stream().findFirst().get().getPenaltyRate();
//      order.setTotalPenaltyRate(totalPenaltyRate.add(leaseOrderPenaltyRate));
//    });
//  }

  
}
