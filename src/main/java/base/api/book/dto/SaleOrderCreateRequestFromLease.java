package base.api.book.dto;

import base.api.book.entity.support.SellOrderStatus;
import base.api.payment.entity.PaymentMethod;

public record SaleOrderCreateRequestFromLease(
        Long LeaseOrderId,
        Long listingId,
        String buyerAddress,
        PaymentMethod paymentMethod
) {}
