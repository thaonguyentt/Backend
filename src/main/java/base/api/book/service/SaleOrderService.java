package base.api.book.service;

import base.api.book.dto.SaleOrderCreateRequest;
import base.api.book.dto.SaleOrderCreateRequestFromLease;
import base.api.book.dto.SaleOrderDetailDto;
import base.api.book.dto.SaleOrderDto;
import base.api.book.entity.*;
import base.api.book.entity.support.CopyStatus;
import base.api.book.entity.support.LeaseOrderStatus;
import base.api.book.entity.support.ListingStatus;
import base.api.book.entity.support.SellOrderStatus;
import base.api.book.exception.ListingNotAvailableException;
import base.api.book.exception.NoSuchListingException;
import base.api.book.mapper.SaleOrderDetailMapper;
import base.api.book.mapper.SaleOrderMapper;
import base.api.book.repository.*;
import base.api.payment.dto.PaymentDto;
import base.api.payment.entity.PaymentMethod;
import base.api.payment.repository.PaymentRepository;
import base.api.payment.service.PaymentService;
import base.api.system.security.Identity;
import base.api.system.security.IdentityUtil;
import base.api.system.security.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
    private final LeaseOrderService leaseOrderService;


    public SaleOrderDto getSaleOrderById (Long id) {
        return saleOrderRepository.findById(id).map(saleOrderMapper::toDto).orElse(null);
    }

    public List<SaleOrderDto> getSaleOrderBySellerId (Long id) {
        return saleOrderRepository.findSaleOrderBySellerId(id)
                .stream()
                .map(saleOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SaleOrderDto> getSaleOrderByBuyerId (Long id) {
        return saleOrderRepository.findSaleOrderByBuyerId(id)
                .stream()
                .map(saleOrderMapper::toDto)
                .collect(Collectors.toList());
    }



    public SaleOrderDto createSaleOrder (Authentication auth, SaleOrderCreateRequest requestDto) {
        SecurityUtils.requireAuthentication(auth);
        if (auth == null || !auth.isAuthenticated()) {
            auth = SecurityContextHolder.getContext().getAuthentication();
        }
        Identity identity = IdentityUtil.fromSpringAuthentication(auth);

        Long userId = Long.valueOf((String)auth.getPrincipal());
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

    public SaleOrderDto createSaleOrderFromLease (Authentication auth, SaleOrderCreateRequestFromLease requestDto) {
        SecurityUtils.requireAuthentication(auth);
        if (auth == null || !auth.isAuthenticated()) {
            auth = SecurityContextHolder.getContext().getAuthentication();
        }
        Identity identity = IdentityUtil.fromSpringAuthentication(auth);

        Long userId = Long.valueOf((String)auth.getPrincipal());
        Listing listing = listingRepository.findById(requestDto.listingId()).orElseThrow(() -> new NoSuchListingException("No such listing exists."));
        if (!ListingStatus.LEASED.equals(listing.getListingStatus()) || listing.getAllow_purchase().equals(0L)) {
            throw new ListingNotAvailableException("Listing " + requestDto.listingId() + " is not available.");
        }

        LeaseOrder leaseOrder = leaseOrderRepository.findById(requestDto.LeaseOrderId()).get();
        LeaseOrderStatus status = leaseOrder.getStatus();


        if (status.equals(LeaseOrderStatus.CANCELED) || status.equals(LeaseOrderStatus.RETURNING)
            || status.equals(LeaseOrderStatus.PAID_OWNER) || status.equals(LeaseOrderStatus.RETURNED)
            || status.equals(LeaseOrderStatus.DEPOSIT_RETURNED) || status.equals(LeaseOrderStatus.LATE_RETURN)
            || status.equals(LeaseOrderStatus.ORDERED_PAYMENT_PENDING) ) {
            throw new ListingNotAvailableException("Book" + requestDto.listingId() + " is not allow to purchase.");
        }

        leaseOrder.setStatus(LeaseOrderStatus.CANCELED);
        leaseOrderRepository.save(leaseOrder);

//        leaseOrderService.updateLeaseOrderStatus(auth, requestDto.LeaseOrderId(),LeaseOrderStatus.RETURNING);
//        leaseOrderService.updateLeaseOrderStatus(auth, requestDto.LeaseOrderId(),LeaseOrderStatus.RETURNED);
//        leaseOrderService.updateLeaseOrderStatus(auth, requestDto.LeaseOrderId(),LeaseOrderStatus.PAID_OWNER);
//        leaseOrderService.updateLeaseOrderStatus(auth, requestDto.LeaseOrderId(),LeaseOrderStatus.DEPOSIT_RETURNED);


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

        SaleOrder newSaleOrder = new SaleOrder();
        newSaleOrder.setListingId(listing.getId());
        if(totalCompensate.compareTo(BigDecimal.ZERO) > 0) {
            newSaleOrder.setStatus(SellOrderStatus.ORDERED_PAYMENT_PENDING);
        } else {
            newSaleOrder.setStatus(SellOrderStatus.PAYMENT_SUCCESS);
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
