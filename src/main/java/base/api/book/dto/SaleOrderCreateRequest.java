package base.api.book.dto;

import base.api.book.entity.support.LeaseOrderStatus;
import base.api.payment.entity.PaymentMethod;

import java.time.LocalDate;

public record SaleOrderCreateRequest(
    LeaseOrderStatus status,
    Long listingId,
    Long sellerId,
    String SellerAddress,
    PaymentMethod paymentMethod
) {}
