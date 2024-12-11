package base.api.book.dto;

import base.api.book.entity.support.LeaseOrderStatus;
import base.api.book.entity.support.SellOrderStatus;
import base.api.payment.entity.PaymentMethod;

import java.time.LocalDate;

public record SaleOrderCreateRequest(
        SellOrderStatus status,
        Long listingId,
        String buyerAddress,
        PaymentMethod paymentMethod,
        Long VoucherShopId,
        Long VoucherSessionId
) {}
