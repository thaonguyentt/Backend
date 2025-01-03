package base.api.book.dto.search;

import base.api.book.entity.Copy;
import base.api.book.entity.support.CopyStatus;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link Copy}
 */
public record CopyDtoUpload(
        Long id,
        Long bookId,
        @NotNull
        Long ownerId,
        Integer quantity,
        String imageLink,
        BigDecimal allow_rent,
        BigDecimal allow_purchase,
        BigDecimal damagePercent,
        LocalDate createdDate,
        LocalDate updatedDate,
        LocalDate deletedDate,
        CopyStatus copyStatus
) implements Serializable {
}