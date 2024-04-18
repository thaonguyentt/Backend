package base.api.book.dto;

import base.api.book.entity.support.LeaseOrderStatus;
import base.api.payment.entity.PaymentMethod;

import java.time.LocalDate;

public record LeaseOrderCreateRequest(
    LeaseOrderStatus status,
    Long listingId,
    Long lesseeId,
    String lesseeAddress,
    LocalDate fromDate,
    LocalDate toDate,
    PaymentMethod paymentMethod
) {}
