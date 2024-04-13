package base.api.book.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link base.api.book.entity.Review}
 */
public record ReviewDto(Long id, Integer score, String description, String[] imageLink, Long leaseOrderId,
                        @NotNull Long userId, @NotNull Long listingId, Instant createdDate, LocalDate updatedDate,
                        LocalDate deletedDate) implements Serializable {
}