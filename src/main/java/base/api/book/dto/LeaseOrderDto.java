package base.api.book.dto;

import base.api.book.entity.LeaseOrderDetail;
import base.api.book.entity.Review;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link base.api.book.entity.LeaseOrder}
 */
public record LeaseOrderDto(
  Long id,
  Long listingId,
  Long statusId,
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
  Integer paymentMethod,
  String imageLink,
  Integer depositPaymentId,
  Integer refundPaymentId,
  Integer payOwnerPaymentId,
  LocalDate createdDate,
  LocalDate updatedDate,
  LocalDate deletedDate,
  Set<LeaseOrderDetail> leaseOrderDetails,
  Set<Review> reviews
) implements Serializable {}