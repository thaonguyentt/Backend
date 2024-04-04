package base.api.book.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BooksDto (
    Long id,
    Long copyId,
    BigDecimal price,
    String link_img,
    String title
)
{}
