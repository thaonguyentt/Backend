package base.api.book.dto;

import base.api.book.entity.Review;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.With;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link base.api.book.entity.LeaseOrder}
 */
@Builder
@With
public record LeaseOrderDto(
  Long id,
  Long listingId,
  String status,
  @NotNull Long lessorId,
  @NotNull Long lesseeId,
  @NotNull String lessorAddress,
  @NotNull String lesseeAddress,
  @NotNull LocalDate fromDate,
  @NotNull LocalDate toDate,
  LocalDate receiveDate,
  LocalDate returnDate,
  @NotNull BigDecimal totalLeaseFee,
  @NotNull BigDecimal totalPenaltyRate,
  @NotNull BigDecimal totalDeposit,
  String paymentMethod,
  String imageLink,
  Long leaseAndDepositPaymentId,
  Long refundDepositPaymentId,
  Long payOwnerPaymentId,
  LocalDate createdDate,
  LocalDate updatedDate,
  LocalDate deletedDate,
  Set<LeaseOrderDetailDto> leaseOrderDetails,
  Set<Review> reviews
) implements Serializable {}