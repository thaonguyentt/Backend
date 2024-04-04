package base.api.book.dto;

import base.api.book.entity.Copy;
import base.api.book.entity.support.CopyStatus;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link Copy}
 */
public record CopyDto(Long id, Long bookId, @NotNull Long ownerId, Integer quantity, String imageLink,
                      BigDecimal damagePercent, LocalDate createdDate,
                      LocalDate updatedDate, LocalDate deletedDate, CopyStatus copyStatus) implements Serializable {
}