package base.api.book.dto;

import base.api.book.entity.Copy;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link Copy}
 */
public record CopyDto(Long id, Long bookBookId, @NotNull Long ownerId, Integer quantity,String imageLink,
                      BigDecimal damagePercent, LocalDate createdDate,
                      LocalDate updatedDate, LocalDate deletedDate) implements Serializable {
}