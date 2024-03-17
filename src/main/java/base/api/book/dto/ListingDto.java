package base.api.book.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link base.api.book.entity.Listing}
 */
public record ListingDto(Long id, Long copyCopyId, LocalDate expiryDate, String description, LocalDate createdDate,
                         LocalDate updatedDate, LocalDate deletedDate) implements Serializable {
}