package base.api.book.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link base.api.book.entity.Genre}
 */
public record GenreDto(Long id, @NotNull String name, @NotNull String nameVn, LocalDate createdDate,
                       LocalDate updatedDate, LocalDate deletedDate) implements Serializable {
}