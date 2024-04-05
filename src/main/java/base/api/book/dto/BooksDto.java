package base.api.book.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record BooksDto (
    Long id,
    Long copyId,
    BigDecimal leaseRate,
    String link_img,
    String title,
    String publisher,
    String language,
    List<String> genre
) implements Serializable
{}
