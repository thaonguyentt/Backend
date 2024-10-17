package base.api.book.controller;

import base.api.book.dto.*;
import base.api.book.dto.search.LeaseOrderUpdateRequest;
import base.api.book.entity.support.LeaseOrderStatus;
import base.api.book.repository.LeaseOrderRepository;
import base.api.book.service.*;
import base.api.system.security.SecurityUtils;
import base.api.user.UserDto;
import base.api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8082", "https://the-flying-bookstore.vercel.app","https://the-flying-bookstore-dashboard-fe.vercel.app"})
public class LeaseOrderController {
  @Autowired
  LeaseOrderRepository leaseOrderRepository;

  @Autowired
  LeaseOrderService leaseOrderService;

  @Autowired
  LeaseOrderDetailService leaseOrderDetailService;

  @Autowired
  UserService userService;
  @Autowired
  CopyService copyService;
  @Autowired
  ListingService listingService;
  @Autowired
  BookService bookService;
  @Autowired
  ReviewService reviewService;



//  @PostMapping("/api/leaseOrder")
//  public LeaseOrderDto createLeaseOrder(@RequestBody LeaseOrderDto leaseOrderDto) {
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    return leaseOrderService.createLeaseOrder(auth, leaseOrderDto);
//  }

  @PostMapping("/api/leaseOrder")
  public LeaseOrderDto createLeaseOrder2(@RequestBody LeaseOrderCreateRequest request) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return leaseOrderService.createLeaseOrder(auth, request);
  }

  @GetMapping("/api/leaseOrder")
  public ResponseEntity<Object> getLeaseOrder() {
    var obj = leaseOrderRepository.getReferenceById(1L);
//    obj.getDepositPaymentId();
    return new ResponseEntity<>(obj.toString(), HttpStatusCode.valueOf(200));
  }
  @GetMapping("/api/leaseOrder/search/lessor/{id}")
  public ResponseEntity<List<LeaseOrderDtoDetail>> getLeaseOrderByLessorId (@PathVariable Long id) {
    List<LeaseOrderDto> leaseOrderDto = leaseOrderService.getLeaseOrderByLessorId(id);
    return ResponseEntity.ok(leaseOrderDto.stream().map(dto->{
      UserDto lessor = userService.getUserById(dto.lessorId());
      UserDto lessee = userService.getUserById(dto.lesseeId());
      ListingDto listingDto = listingService.getListingById(dto.listingId());
      CopyDto copy = copyService.getCopyById(listingDto.copyId());
      BookDto book = bookService.getBookById(copy.bookId());
      List<ReviewDto> reviews = reviewService.getReviewByOwnerId(listingDto.ownerId());
      Long bookOwned = listingService.countListingByOwner(listingDto.ownerId());
      Long bookLeasing = listingService.countListingByOwnerAndStatus(listingDto.ownerId());
      UserDto user = userService.getUserById(listingDto.ownerId());
      BigDecimal totalPenaltyFee = dto.totalPenaltyRate()
              .multiply(BigDecimal.valueOf(Duration.between(
                              dto.toDate().atStartOfDay(),
                              LocalDate.now().atStartOfDay())
                      .toDays()));
      ListingDetailDto listing = new ListingDetailDto(
              listingDto.id(),
              user,
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
      return new LeaseOrderDtoDetail(dto, listing, lessor, lessee, totalPenaltyFee);
    }).collect(Collectors.toList()));
//            ResponseEntity.ok(leaseOrderService.getLeaseOrderByLessorId(id));
  }

  @GetMapping("/api/leaseOrder/search/lessee/{id}")
  public ResponseEntity<List<LeaseOrderDtoDetail>> getLeaseOrderByLesseeId (@PathVariable Long id) {
    List<LeaseOrderDto> leaseOrderDto = leaseOrderService.getLeaseOrderByLesseeId(id);
    return ResponseEntity.ok(leaseOrderDto.stream().map(dto->{
      UserDto lessor = userService.getUserById(dto.lessorId());
      UserDto lessee = userService.getUserById(dto.lesseeId());
      ListingDto listingDto = listingService.getListingById(dto.listingId());
      CopyDto copy = copyService.getCopyById(listingDto.copyId());
      BookDto book = bookService.getBookById(copy.bookId());
      List<ReviewDto> reviews = reviewService.getReviewByOwnerId(listingDto.ownerId());
      Long bookOwned = listingService.countListingByOwner(listingDto.ownerId());
      Long bookLeasing = listingService.countListingByOwnerAndStatus(listingDto.ownerId());
      UserDto user = userService.getUserById(listingDto.ownerId());
      BigDecimal totalPenaltyFee = dto.totalPenaltyRate()
              .multiply(BigDecimal.valueOf(Duration.between(
                              dto.toDate().atStartOfDay(),
                              LocalDate.now().atStartOfDay())
                      .toDays()));
      ListingDetailDto listing = new ListingDetailDto(
              listingDto.id(),
              user,
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
      return new LeaseOrderDtoDetail(dto, listing, lessor, lessee, totalPenaltyFee);
    }).collect(Collectors.toList()));
  }

  @GetMapping ("/api/leaseOrder/search/lessor/status/{id}")
  public ResponseEntity<List<LeaseOrderDtoDetail>> getLeaseOrderByLessorIdAndStatus (@PathVariable Long id, @RequestParam(name="status") Long status) {
    List<LeaseOrderStatus> leaseOrderStatus = new ArrayList<>();
    if (status == 1) {
      leaseOrderStatus.add(LeaseOrderStatus.ORDERED_PAYMENT_PENDING);
      leaseOrderStatus.add(LeaseOrderStatus.PAYMENT_SUCCESS);
      leaseOrderStatus.add(LeaseOrderStatus.USER_PAID);
    } else if (status == 2) {
      leaseOrderStatus.add(LeaseOrderStatus.DELIVERED);
      leaseOrderStatus.add(LeaseOrderStatus.RETURNING);
    } else if (status == 3) {
      leaseOrderStatus.add(LeaseOrderStatus.LATE_RETURN);
    } else if (status == 5) {
      leaseOrderStatus.add(LeaseOrderStatus.CANCELED);
    } else {
      leaseOrderStatus.add(LeaseOrderStatus.RETURNED);
      leaseOrderStatus.add(LeaseOrderStatus.DEPOSIT_RETURNED);
      leaseOrderStatus.add(LeaseOrderStatus.PAID_OWNER);
    }
    List<LeaseOrderDto> leaseOrderDto = leaseOrderService.getLeaseOrderByLessorIdAndStatus(id,leaseOrderStatus);
    return ResponseEntity.ok(leaseOrderDto.stream().map(dto->{
      UserDto lessor = userService.getUserById(dto.lessorId());
      UserDto lessee = userService.getUserById(dto.lesseeId());
      ListingDto listingDto = listingService.getListingById(dto.listingId());
      CopyDto copy = copyService.getCopyById(listingDto.copyId());
      BookDto book = bookService.getBookById(copy.bookId());
      List<ReviewDto> reviews = reviewService.getReviewByOwnerId(listingDto.ownerId());
      Long bookOwned = listingService.countListingByOwner(listingDto.ownerId());
      Long bookLeasing = listingService.countListingByOwnerAndStatus(listingDto.ownerId());
      UserDto user = userService.getUserById(listingDto.ownerId());
      BigDecimal totalPenaltyFee = dto.totalPenaltyRate()
              .multiply(BigDecimal.valueOf(Duration.between(
                              dto.toDate().atStartOfDay(),
                              LocalDate.now().atStartOfDay())
                      .toDays()));
      ListingDetailDto listing = new ListingDetailDto(
              listingDto.id(),
              user,
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
      return new LeaseOrderDtoDetail(dto, listing, lessor, lessee, totalPenaltyFee);
    }).collect(Collectors.toList()));

  }

  @GetMapping ("/api/leaseOrder/edit/status")
  public ResponseEntity<LeaseOrderDto> updateStatus (@RequestParam(name="id") Long id, @RequestParam(name="status") LeaseOrderStatus status) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    SecurityUtils.requireAuthentication(auth);
//    try {status
      return ResponseEntity.ok(leaseOrderService.updateLeaseOrderStatus(auth, id, status));
//    } catch (Exception e) {
//      return new ResponseEntity("Error update status", HttpStatus.LOCKED);
//    }
  }

  @GetMapping ("/api/leaseOrder/search/lessee/status/{id}")
  public ResponseEntity<List<LeaseOrderDtoDetail>> getLeaseOrderByLesseeIdAndStatus (@PathVariable Long id, @RequestParam(name="status") Long status) {
    List<LeaseOrderStatus> leaseOrderStatus = new ArrayList<>();
    if (status == 1) {
      leaseOrderStatus.add(LeaseOrderStatus.ORDERED_PAYMENT_PENDING);
      leaseOrderStatus.add(LeaseOrderStatus.PAYMENT_SUCCESS);
      leaseOrderStatus.add(LeaseOrderStatus.USER_PAID);
    } else if (status == 2) {
      leaseOrderStatus.add(LeaseOrderStatus.DELIVERED);
      leaseOrderStatus.add(LeaseOrderStatus.RETURNING);
    } else if (status == 3) {
      leaseOrderStatus.add(LeaseOrderStatus.LATE_RETURN);
    } else if (status == 5) {
      leaseOrderStatus.add(LeaseOrderStatus.CANCELED);
    }
    else {
      leaseOrderStatus.add(LeaseOrderStatus.RETURNED);
      leaseOrderStatus.add(LeaseOrderStatus.DEPOSIT_RETURNED);
      leaseOrderStatus.add(LeaseOrderStatus.PAID_OWNER);
    }
    List<LeaseOrderDto> leaseOrderDto = leaseOrderService.getLeaseOrderByLesseeIdAndStatus(id,leaseOrderStatus);
    return ResponseEntity.ok(leaseOrderDto.stream().map(dto->{
      UserDto lessor = userService.getUserById(dto.lessorId());
      UserDto lessee = userService.getUserById(dto.lesseeId());
      ListingDto listingDto = listingService.getListingById(dto.listingId());
      CopyDto copy = copyService.getCopyById(listingDto.copyId());
      BookDto book = bookService.getBookById(copy.bookId());
      List<ReviewDto> reviews = reviewService.getReviewByOwnerId(listingDto.ownerId());
      Long bookOwned = listingService.countListingByOwner(listingDto.ownerId());
      Long bookLeasing = listingService.countListingByOwnerAndStatus(listingDto.ownerId());
      UserDto user = userService.getUserById(listingDto.ownerId());
      BigDecimal totalPenaltyFee = dto.totalPenaltyRate()
              .multiply(BigDecimal.valueOf(Duration.between(
                              dto.toDate().atStartOfDay(),
                              LocalDate.now().atStartOfDay())
                      .toDays()));
      ListingDetailDto listing = new ListingDetailDto(
              listingDto.id(),
              user,
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
      return new LeaseOrderDtoDetail(dto, listing, lessor, lessee, totalPenaltyFee);
    }).collect(Collectors.toList()));

  }
  
  
  
  @PostMapping ("/api/leaseOrder/updateReceive")
  public ResponseEntity<LeaseOrderDto> updateReceive (@RequestBody LeaseOrderUpdateRequest updateRequest) {
    return null;
  }

  @GetMapping ("/api/leaseOrder/{id}")
  public ResponseEntity<LeaseOrderDtoDetail> getLeaseOrder (@PathVariable Long id) {
    return ResponseEntity.ok(leaseOrderService.getDetailLeaseOrderById(id));
  }

  @PostMapping("/api/leaseOrder/{leaseOrderId}/pay/{paymentId}")
  public ResponseEntity<LeaseOrderDto> payTotalLeaseAndDepositFee(
    @PathVariable("leaseOrderId") Long leaseOrderId,
    @PathVariable("paymentId") Long paymentId) {
      return ResponseEntity.badRequest().build();

  }

  @GetMapping("/api/leaseOrder/admin")
  public Page<LeaseOrderAdmin> getLeaseOrderForAdmin (Pageable pageable) {

    Page<LeaseOrderDto> leaseOrderDto = leaseOrderService.getAllLeaseOrder(pageable);
    return leaseOrderDto.map(dto->{
      UserDto lessor = userService.getUserById(dto.lessorId());
      UserDto lessee = userService.getUserById(dto.lesseeId());
      ListingDto listingDto = listingService.getListingById(dto.listingId());
      CopyDto copy = copyService.getCopyById(listingDto.copyId());
      BookDto book = bookService.getBookById(copy.bookId());
      List<ReviewDto> reviews = reviewService.getReviewByOwnerId(listingDto.ownerId());
      Long bookOwned = listingService.countListingByOwner(listingDto.ownerId());
      Long bookLeasing = listingService.countListingByOwnerAndStatus(listingDto.ownerId());
      UserDto user = userService.getUserById(listingDto.ownerId());
      BigDecimal totalPenaltyFee = BigDecimal.ZERO;
      if (dto.status().equals("LATE_RETURN")) {
        totalPenaltyFee = dto.totalPenaltyRate()
                .multiply(BigDecimal.valueOf(Duration.between(
                                dto.toDate().atStartOfDay(),
                                LocalDate.now().atStartOfDay())
                        .toDays()));
      } else if (dto.returnDate() != null) {
        if (dto.toDate().isBefore(dto.returnDate())) {
        totalPenaltyFee = dto.totalPenaltyRate()
                .multiply(BigDecimal.valueOf(Duration.between(
                                dto.toDate().atStartOfDay(),
                                dto.returnDate().atStartOfDay())
                        .toDays()));
        }
      }
      BigDecimal paidOwnerFee = totalPenaltyFee.add(dto.totalLeaseFee());
      BigDecimal depositReturnFee = dto.totalDeposit().subtract(paidOwnerFee);
      if (dto.status().equals("ORDERED_PAYMENT_PENDING") || dto.status().equals("CANCELED")) {
        totalPenaltyFee = BigDecimal.ZERO;
        paidOwnerFee = BigDecimal.ZERO;
        depositReturnFee = BigDecimal.ZERO;
      }
//      if (depositReturnFee.compareTo(BigDecimal.ZERO) < 0) {depositReturnFee = BigDecimal.ZERO;}
//      BigDecimal paidOwnerFee = dto.totalDeposit().subtract(depositReturnFee);
      ListingDetailDto listing = new ListingDetailDto(
              listingDto.id(),
              user,
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
      return new LeaseOrderAdmin(dto, listing, lessor, lessee, totalPenaltyFee,depositReturnFee,paidOwnerFee);
    });


  }




}
