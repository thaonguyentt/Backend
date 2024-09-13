package base.api.book.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.With;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link base.api.book.entity.SaleOrder}
 */
@Builder
@With
public record SaleOrderDto (
        Long id,
        Long listingId,
        String status,
        @NotNull Long sellerId,
        @NotNull Long buyerId,
        @NotNull String sellerAddress,
        @NotNull String buyerAddress,
        LocalDate receiveDate,
        BigDecimal totalPrice,
        BigDecimal totalChange,
        @NotNull BigDecimal totalCompensate,
        @NotNull BigDecimal totalPenaltyRate,
        String paymentMethod,
        Long sellPaymentId,
        Long changePaymentId,
        Long compensatePaymentId,
        LocalDate createdDate

//        Set<LeaseOrderDetailDto> leaseOrderDetails

) implements Serializable {}


