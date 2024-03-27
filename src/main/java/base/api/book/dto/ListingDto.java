package base.api.book.dto;

import jakarta.validation.constraints.NotNull;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link base.api.book.entity.Listing}
 */
public record ListingDto(Long id, Long copyCopyId, @NotNull Long ownerId, Integer quantity, String address, LocalDate expiryDate, BigDecimal price,
                         BigDecimal deposit, BigDecimal penaltyFee, String description, LocalDate createdDate,
                         LocalDate updatedDate, LocalDate deletedDate) implements Serializable {
}
