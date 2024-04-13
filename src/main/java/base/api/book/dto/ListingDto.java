package base.api.book.dto;

import base.api.book.entity.support.ListingStatus;
import jakarta.validation.constraints.NotNull;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link base.api.book.entity.Listing}
 */
public record ListingDto(
  Long id,
  Long copyId,
  @NotNull Long ownerId,
  Integer quantity,
  String address,
  LocalDate expiryDate,
  BigDecimal leaseRate,
  BigDecimal depositFee,
  BigDecimal penaltyRate,
  String description,
  ListingStatus listingStatus
) implements Serializable {}
