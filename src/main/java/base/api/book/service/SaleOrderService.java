package base.api.book.service;

import base.api.book.dto.*;
import base.api.book.entity.*;
import base.api.book.entity.support.CopyStatus;
import base.api.book.entity.support.LeaseOrderStatus;
import base.api.book.entity.support.ListingStatus;
import base.api.book.entity.support.SellOrderStatus;
import base.api.book.exception.ListingNotAvailableException;
import base.api.book.exception.NoSuchListingException;
import base.api.book.exception.SaleOrderCanNotUpdateStatus;
import base.api.book.exception.VoucherCanNotApply;
import base.api.book.mapper.SaleOrderDetailMapper;
import base.api.book.mapper.SaleOrderMapper;
import base.api.book.repository.*;
import base.api.payment.dto.PaymentDto;
import base.api.payment.entity.Payment;
import base.api.payment.entity.PaymentMethod;
import base.api.payment.entity.PaymentStatus;
import base.api.payment.repository.PaymentRepository;
import base.api.payment.service.PaymentService;
import base.api.system.security.Identity;
import base.api.system.security.IdentityUtil;
import base.api.system.security.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;
import base.api.book.entity.support.SellOrderStatus;

@Service
@Transactional
@AllArgsConstructor
public class SaleOrderService {

    private final SaleOrderRepository saleOrderRepository;

    private final SaleOrderMapper saleOrderMapper;

    private final ListingRepository listingRepository;

    private final CopyRepository copyRepository;
    private final PaymentService paymentService;
    private PaymentRepository paymentRepository;

    private final LeaseOrderRepository leaseOrderRepository;
    @Autowired
    VoucherShopService voucherShopService;
    @Autowired
    VoucherSessionService voucherSessionService;

    @Autowired
    SaleOrderVoucherShopService saleOrdervoucherShopService;
    @Autowired
    SaleOrderVoucherSessionService saleOrderVoucherSessionService;


    public List<SaleOrderDto> getAllSaleOrder() {
        return saleOrderRepository.findAll().stream().map(saleOrderMapper::toDto).collect(Collectors.toList());
    }

    public SaleOrderDto getSaleOrderById(Long id) {
        return saleOrderRepository.findById(id).map(saleOrderMapper::toDto).orElse(null);
    }

    public List<SaleOrderDto> getSaleOrderBySellerId(Long id) {
        return saleOrderRepository.findBySellerId(id)
                .stream()
                .map(saleOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SaleOrderDto> getSaleOrderByBuyerId(Long id) {
        return saleOrderRepository.findByBuyerId(id)
                .stream()
                .map(saleOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SaleOrderDto> getSaleOrderOfSellerByStatus(Long id, SellOrderStatus status) {
        return saleOrderRepository.findBySellerIdAndStatus(id, status)
                .stream()
                .map(saleOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SaleOrderDto> getSaleOrderByBuyerAndStatus(Long id, SellOrderStatus status) {
        return saleOrderRepository.findByBuyerIdAndStatus(id, status)
                .stream()
                .map(saleOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SaleOrderDto> getSaleOrderBySellerAndStatus(Long id, SellOrderStatus status) {
        return saleOrderRepository.findBySellerIdAndStatus(id, status)
                .stream()
                .map(saleOrderMapper::toDto)
                .collect(Collectors.toList());
    }



    public SaleOrderDto createSaleOrder(Authentication auth, SaleOrderCreateRequest requestDto) {
        SecurityUtils.requireAuthentication(auth);
        if (auth == null || !auth.isAuthenticated()) {
            auth = SecurityContextHolder.getContext().getAuthentication();
        }
        Identity identity = IdentityUtil.fromSpringAuthentication(auth);

        Long userId = Long.valueOf((String) auth.getPrincipal());
        Listing listing = listingRepository.findById(requestDto.listingId()).orElseThrow(() -> new NoSuchListingException("No such listing exists."));
        if (!ListingStatus.AVAILABLE.equals(listing.getListingStatus()) || listing.getAllow_purchase().equals(0L)) {
            throw new ListingNotAvailableException("Listing " + requestDto.listingId() + " is not available.");
        }

        Long SellerId = listing.getOwner().getId();
        listing.setListingStatus(ListingStatus.SOLD);
        Copy copy = listing.getCopy();
        copy.setCopyStatus(CopyStatus.SOLD);


        Listing updatedListing = listingRepository.save(listing);
        Copy updatedCopy = copyRepository.save(copy);
        Book book = copy.getBook();







        SaleOrder newSaleOrder = new SaleOrder();
        newSaleOrder.setListingId(listing.getId());
        newSaleOrder.setStatus(SellOrderStatus.ORDERED_PAYMENT_PENDING);
        newSaleOrder.setSellerId(listing.getOwner().getId());
        newSaleOrder.setBuyerId(userId);
        newSaleOrder.setSellerAddress(listing.getOwner().getAddress());
        newSaleOrder.setBuyerAddress(requestDto.buyerAddress());
        newSaleOrder.setTotalPrice(listing.getPrice());
        newSaleOrder.setTotalChange(BigDecimal.ZERO);
        newSaleOrder.setTotalCompensate(BigDecimal.ZERO);
        newSaleOrder.setPaymentMethod(requestDto.paymentMethod());
        newSaleOrder.setCreatedDate(LocalDate.now());
        newSaleOrder.setSaleOrderDetails(
                Set.of(SaleOrderDetail.builder()
                        .title(copy.getBook().getTitle())
                        .saleOrder(newSaleOrder)
                        .listing(listing)
                        .copy(listing.getCopy())
                        .price(listing.getPrice()
                        ).build()
                )
        );

        // Create Payment
        PaymentDto newPayment = paymentService.create(
                identity,
                PaymentDto.builder()
                        .amount(listing.getPrice())
                        .currency("VND")
                        .amount(listing.getPrice())
                        .payerId(userId) // Pay từ userId cho hệ thống
                        .payeeId(0L) // Pay cho hệ thống tạm cho payeeId = 0
                        .description("Lease fee and deposit")
                        .paymentMethod(PaymentMethod.COD)
                        .build()
        );
        newSaleOrder.setSellPaymentId(newPayment.id());

        SaleOrder createdLO = saleOrderRepository.save(newSaleOrder);

        var newSaleOrderDto = saleOrderMapper.toDto(createdLO);
        // Add more info

        // Kiểm tra tính hợp lệ của voucher shop và insert vào bảng SaleOrderVoucherShop
        if (requestDto.VoucherShopId() != null) {
            BigDecimal amount;
            VoucherShopDto voucherShopDto = voucherShopService.getVoucherById(requestDto.VoucherShopId());
            if (SaleOrderService.this.checkVoucherShopValid(voucherShopDto, listing.getPrice())) {
                if (voucherShopDto.discountPercentage() != null) {
                    amount = listing.getPrice().multiply(voucherShopDto.discountPercentage()).divide(BigDecimal.valueOf(100));
                } else {
                    amount = voucherShopDto.discountAmount();
                }
                SaleOrderVoucherShopDto newSaleOrderVoucherShopDto = saleOrdervoucherShopService.create(
                        identity,
                        SaleOrderVoucherShopDto.builder()
                                .saleOrderId(createdLO.getId())
                                .voucherId(requestDto.VoucherShopId())
                                .discountAmount(amount)
                                .build()
                );
            }
        }

        // Kiểm tra tính hợp lệ của voucher session và insert vào bảng SaleOrderVoucherSession
        if (requestDto.VoucherSessionId() != null) {
            BigDecimal amount;
            VoucherSessionDto voucherSessionDto = voucherSessionService.getVoucherById(requestDto.VoucherSessionId());
            if (SaleOrderService.this.checkVoucherSessionValid(voucherSessionDto, listing.getPrice())) {
                if (voucherSessionDto.discountPercentage() != null) {
                    amount = listing.getPrice().multiply(voucherSessionDto.discountPercentage()).divide(BigDecimal.valueOf(100));
                } else {
                    amount = voucherSessionDto.discountAmount();

                }
                SaleOrderVoucherSessionDto newSaleOrderVoucherSessionDto = saleOrderVoucherSessionService.create(
                        identity,
                        SaleOrderVoucherSessionDto.builder()
                                .saleOrderId(newSaleOrderDto.id())
                                .voucherId(requestDto.VoucherSessionId())
                                .discountAmount(amount)
                                .build()
                );
            }
        }

        return newSaleOrderDto;


    }

    public Boolean checkVoucherShopValid (VoucherShopDto voucherShopDto, BigDecimal price) {
        if (voucherShopDto.endDate().isBefore(LocalDate.now())) {
            throw new VoucherCanNotApply("voucher Shop đã quá hạn");
        } else if (voucherShopDto.startDate().isAfter(LocalDate.now())) {
            throw new VoucherCanNotApply("voucher Shop chưa được áp dụng");
        } else if (price.compareTo(voucherShopDto.minValue()) < 0) {
            throw new VoucherCanNotApply("Chưa đạt giá trị đơn tối thiểu của voucher shop");
        } else {
            return true;
        }
    }

    public Boolean checkVoucherSessionValid (VoucherSessionDto voucherSessionDto, BigDecimal price) {
        if (voucherSessionDto.endDate().isBefore(LocalDate.now())) {
            throw new VoucherCanNotApply("voucher Session đã quá hạn");
        } else if (voucherSessionDto.startDate().isAfter(LocalDate.now())) {
            throw new VoucherCanNotApply("voucher Session chưa được áp dụng");
        } else if (price.compareTo(voucherSessionDto.minValue()) < 0) {
            throw new VoucherCanNotApply("Chưa đạt giá trị đơn tối thiểu của voucher session");
        } else {
            return true;
        }
    }

    public SaleOrderDto createSaleOrderFromLease(Authentication auth, SaleOrderCreateRequestFromLease requestDto) {
        SecurityUtils.requireAuthentication(auth);
        if (auth == null || !auth.isAuthenticated()) {
            auth = SecurityContextHolder.getContext().getAuthentication();
        }
        Identity identity = IdentityUtil.fromSpringAuthentication(auth);

        Long userId = Long.valueOf((String) auth.getPrincipal());
        Listing listing = listingRepository.findById(requestDto.listingId()).orElseThrow(() -> new NoSuchListingException("No such listing exists."));
        if (!ListingStatus.LEASED.equals(listing.getListingStatus()) || listing.getAllow_purchase().equals(0L)) {
            throw new ListingNotAvailableException("Listing " + requestDto.listingId() + " is not available.");
        }

        LeaseOrder leaseOrder = leaseOrderRepository.findById(requestDto.LeaseOrderId()).get();
        LeaseOrderStatus status = leaseOrder.getStatus();

        // kiểm tra status của lease order, chỉ cho phép lease order có status là DELIVERED với ORDERED_PAYMENT_PENDING
        // chuyển từ đơn thuê sang đơn mua
        if (status.equals(LeaseOrderStatus.CANCELED) || status.equals(LeaseOrderStatus.RETURNING)
                || status.equals(LeaseOrderStatus.PAID_OWNER) || status.equals(LeaseOrderStatus.RETURNED)
                || status.equals(LeaseOrderStatus.DEPOSIT_RETURNED) || status.equals(LeaseOrderStatus.LATE_RETURN)
//                || status.equals(LeaseOrderStatus.ORDERED_PAYMENT_PENDING)
        ) {
            throw new ListingNotAvailableException("Book" + requestDto.listingId() + " is not allow to purchase.");
        } else if (status.equals(LeaseOrderStatus.ORDERED_PAYMENT_PENDING)) {

            leaseOrder.setStatus(LeaseOrderStatus.CANCELED);
            leaseOrderRepository.save(leaseOrder);

            Long SellerId = listing.getOwner().getId();
            listing.setListingStatus(ListingStatus.SOLD);
            Copy copy = listing.getCopy();
            copy.setCopyStatus(CopyStatus.SOLD);


            Listing updatedListing = listingRepository.save(listing);
            Copy updatedCopy = copyRepository.save(copy);
            Book book = copy.getBook();


            SaleOrder newSaleOrder = new SaleOrder();
            newSaleOrder.setListingId(listing.getId());
            newSaleOrder.setStatus(SellOrderStatus.ORDERED_PAYMENT_PENDING);
            newSaleOrder.setSellerId(listing.getOwner().getId());
            newSaleOrder.setBuyerId(userId);
            newSaleOrder.setSellerAddress(listing.getOwner().getAddress());
            newSaleOrder.setBuyerAddress(requestDto.buyerAddress());
            newSaleOrder.setTotalPrice(listing.getPrice());
            newSaleOrder.setTotalChange(BigDecimal.ZERO);
            newSaleOrder.setTotalCompensate(BigDecimal.ZERO);
            newSaleOrder.setPaymentMethod(requestDto.paymentMethod());
            newSaleOrder.setCreatedDate(LocalDate.now());
            newSaleOrder.setSaleOrderDetails(
                    Set.of(SaleOrderDetail.builder()
                            .title(copy.getBook().getTitle())
                            .saleOrder(newSaleOrder)
                            .listing(listing)
                            .copy(listing.getCopy())
                            .price(listing.getPrice()
                            ).build()
                    )
            );

            // Create Payment
            PaymentDto newPayment = paymentService.create(
                    identity,
                    PaymentDto.builder()
                            .amount(listing.getPrice())
                            .currency("VND")
                            .amount(listing.getPrice())
                            .payerId(userId) // Pay từ userId cho hệ thống
                            .payeeId(0L) // Pay cho hệ thống tạm cho payeeId = 0
                            .description("Lease fee and deposit")
                            .paymentMethod(PaymentMethod.COD)
                            .build()
            );
            newSaleOrder.setSellPaymentId(newPayment.id());

            SaleOrder createdLO = saleOrderRepository.save(newSaleOrder);

            var newSaleOrderDto = saleOrderMapper.toDto(createdLO);
            return newSaleOrderDto;
        } else {

            leaseOrder.setStatus(LeaseOrderStatus.CANCELED);
            leaseOrderRepository.save(leaseOrder);

            // tính toán chi phí
            BigDecimal RealTotalLeaseFee = leaseOrder.getTotalPenaltyRate()
                    .multiply(BigDecimal.valueOf(Duration.between(
                                    leaseOrder.getFromDate().atStartOfDay(),
                                    LocalDate.now().atStartOfDay())
                            .toDays()));
            BigDecimal totalChange = leaseOrder.getTotalDeposit().subtract(RealTotalLeaseFee)
                    .subtract(listing.getPrice());
            BigDecimal totalCompensate = BigDecimal.ZERO;

            if (totalChange.compareTo(BigDecimal.ZERO) < 0) {
                totalChange = BigDecimal.ZERO;
                totalCompensate = BigDecimal.ZERO.subtract(totalChange);
            }

            Long SellerId = listing.getOwner().getId();
            listing.setListingStatus(ListingStatus.SOLD);
            Copy copy = listing.getCopy();
            copy.setCopyStatus(CopyStatus.SOLD);


            Listing updatedListing = listingRepository.save(listing);
            Copy updatedCopy = copyRepository.save(copy);
            Book book = copy.getBook();

            PaymentDto newPayment = paymentService.create(
                    identity,
                    PaymentDto.builder()
                            .amount(listing.getPrice())
                            .currency("VND")
                            .paymentStatus(PaymentStatus.PAYMENT_PENDING)
                            .payerId(userId) // Pay từ userId cho hệ thống
                            .payeeId(0L) // Pay cho hệ thống tạm cho payeeId = 0
                            .description("Lease fee and deposit")
                            .paymentMethod(PaymentMethod.COD)
                            .build()
            );

            SaleOrder newSaleOrder = new SaleOrder();
            newSaleOrder.setListingId(listing.getId());
            if (totalCompensate.compareTo(BigDecimal.ZERO) > 0) {
                newSaleOrder.setStatus(SellOrderStatus.ORDERED_PAYMENT_PENDING);
                newSaleOrder.setChangePaymentId(newPayment.id());
            } else {
                newSaleOrder.setStatus(SellOrderStatus.DELIVERED);
                newSaleOrder.setReceiveDate(LocalDate.now());
                newSaleOrder.setChangePaymentId(newPayment.id());
                Long paymentId = newPayment.id();
                Payment payment = paymentRepository.findById(paymentId).get();
                payment.setPaymentStatus(PaymentStatus.SUCCEEDED);
                paymentRepository.save(payment);
            }
//        newSaleOrder.setStatus(SellOrderStatus.ORDERED_PAYMENT_PENDING);
            newSaleOrder.setSellerId(listing.getOwner().getId());
            newSaleOrder.setBuyerId(userId);
            newSaleOrder.setSellerAddress(listing.getOwner().getAddress());
            newSaleOrder.setBuyerAddress(requestDto.buyerAddress());
            newSaleOrder.setTotalPrice(listing.getPrice());
            newSaleOrder.setTotalChange(totalChange);
            newSaleOrder.setTotalCompensate(totalCompensate);
            newSaleOrder.setPaymentMethod(requestDto.paymentMethod());
            newSaleOrder.setCreatedDate(LocalDate.now());
            newSaleOrder.setSaleOrderDetails(
                    Set.of(SaleOrderDetail.builder()
                            .title(copy.getBook().getTitle())
                            .saleOrder(newSaleOrder)
                            .listing(listing)
                            .copy(listing.getCopy())
                            .price(listing.getPrice()
                            ).build()
                    )
            );


            SaleOrder createdLO = saleOrderRepository.save(newSaleOrder);

            var newSaleOrderDto = saleOrderMapper.toDto(createdLO);
            // Add more info

            return newSaleOrderDto;
        }


    }

    public SaleOrderDto updateSaleOrderStatus (Authentication auth, Long id, SellOrderStatus newStatus) {
        Identity identity = IdentityUtil.fromSpringAuthentication(auth);
        if( saleOrderRepository.findById(id).isEmpty()) return null;
        SaleOrder saleOrder = saleOrderRepository.findById(id).get();
        SaleOrder updateOrder = switch (newStatus)  {
            case ORDERED_PAYMENT_PENDING -> saleOrder;
            case CANCELED -> changeOrderStatusCancel(identity, saleOrder);
            case PAYMENT_SUCCESS -> changeOderStatusPaymentsuccess(identity, saleOrder);
            case DELIVERED -> changeOderStatusDelivered(identity, saleOrder);
            case PAID_BUYER -> changeOderStatusPaidBuyer(identity, saleOrder);
            case PAID_SELLER -> changeOderStatusPaidSeller(identity,saleOrder);
        };

        return saleOrderMapper.toDto(updateOrder);
    }

    public SaleOrder changeOrderStatusCancel (Identity identity, SaleOrder saleOrder) {
        IdentityUtil.requireHasAnyRole(identity, "SYSTEM", "ADMIN", "USER");
        if (saleOrder.getSellPaymentId().describeConstable().isEmpty()) {
            throw new SaleOrderCanNotUpdateStatus("Đơn hàng không thể cancel");
        } else {
            Listing listing = listingRepository.findById(saleOrder.getListingId()).get();
            listing.setListingStatus(ListingStatus.AVAILABLE);
            Copy copy = listing.getCopy();
            copy.setCopyStatus(CopyStatus.LISTED);
            saleOrder.setStatus(SellOrderStatus.CANCELED);
            return saleOrderRepository.save(saleOrder);
        }
    }

    public SaleOrder changeOderStatusPaymentsuccess (Identity identity, SaleOrder saleOrder) {
        IdentityUtil.requireHasAnyRole(identity, "SYSTEM", "ADMIN", "USER");
        if (saleOrder.getSellPaymentId().describeConstable().isPresent()) {
            saleOrder.setStatus(SellOrderStatus.PAYMENT_SUCCESS);
            Long paymentId = saleOrder.getSellPaymentId();
            Payment payment = paymentRepository.findById(paymentId).get();
            payment.setPaymentStatus(PaymentStatus.SUCCEEDED);
            paymentRepository.save(payment);
            return saleOrderRepository.save(saleOrder);
        } else {
            throw new SaleOrderCanNotUpdateStatus("Nếu user đã trả phần tiền còn thiếu thì đơn hàng cần chuyển sang trạng thái DELIVERED");
        }
    }

    public SaleOrder changeOderStatusDelivered (Identity identity, SaleOrder saleOrder) {
        IdentityUtil.requireHasAnyRole(identity, "SYSTEM", "ADMIN", "USER");
            saleOrder.setStatus(SellOrderStatus.DELIVERED);
            saleOrder.setReceiveDate(LocalDate.now());
            if (saleOrder.getSellPaymentId().describeConstable().isPresent()) {
                Long paymentId = saleOrder.getSellPaymentId();
                Payment payment = paymentRepository.findById(paymentId).get();
                payment.setPaymentStatus(PaymentStatus.SUCCEEDED);
                paymentRepository.save(payment);
            } else {
                Long paymentId = saleOrder.getCompensatePaymentId();
                Payment payment = paymentRepository.findById(paymentId).get();
                payment.setPaymentStatus(PaymentStatus.SUCCEEDED);
                paymentRepository.save(payment);
            }
            return saleOrderRepository.save(saleOrder);
    }

    public SaleOrder changeOderStatusPaidBuyer (Identity identity, SaleOrder saleOrder) {
        IdentityUtil.requireHasAnyRole(identity, "SYSTEM", "ADMIN", "USER");
       if (saleOrder.getChangePaymentId().describeConstable().isPresent()){
            saleOrder.setStatus(SellOrderStatus.PAID_BUYER);
            Payment payment = paymentRepository.findById(saleOrder.getId()).get();
            payment.setPaymentStatus(PaymentStatus.SUCCEEDED);
            paymentRepository.save(payment);
            return saleOrderRepository.save(saleOrder);
        } else {
            throw new SaleOrderCanNotUpdateStatus("Nếu admin đã trả tiền cho người bán thì cần chuyển sang trạng thái PAID_SELLER");
        }
    }

    public SaleOrder changeOderStatusPaidSeller (Identity identity, SaleOrder saleOrder) {
        saleOrder.setStatus(SellOrderStatus.PAID_SELLER);
        saleOrder.setStatus(SellOrderStatus.PAID_BUYER);
        Payment payment = paymentRepository.findById(saleOrder.getId()).get();
        payment.setPaymentStatus(PaymentStatus.SUCCEEDED);
        return saleOrderRepository.save(saleOrder);
    }


    public void cancelOrderOnLatePayment(Identity identity) {
        IdentityUtil.requireAuthenticated(identity);
        IdentityUtil.requireHasAnyRole(identity, "ADMIN", "SYSTEM");

        List<SaleOrder> latePaymentOrders = saleOrderRepository.findLatePaymentSaleOrder();
        latePaymentOrders.forEach(order -> changeOrderStatusCancel(identity, order));
        saleOrderRepository.saveAll(latePaymentOrders);
    }

    public void cancelOrderOnLateDelivery(Identity identity) {
        IdentityUtil.requireAuthenticated(identity);
        IdentityUtil.requireHasAnyRole(identity, "ADMIN", "SYSTEM");

        List<SaleOrder> latePaymentOrders = saleOrderRepository.findLatePaymentSaleOrder();
        latePaymentOrders.forEach(order -> changeOrderStatusCancel(identity, order));
        saleOrderRepository.saveAll(latePaymentOrders);
    }
}
