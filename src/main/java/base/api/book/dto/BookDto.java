package base.api.book.dto;

import base.api.book.entity.Book;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link Book}
 */
public record BookDto(Long id, @NotNull String isbn, @NotNull String title, List<String> authors,
                      String languageCode, List<String> genre, String publisher, LocalDate publishedDate,
                      Integer pageCount, String size) implements Serializable {
}