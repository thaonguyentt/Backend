package base.api.book.dto;

import java.io.Serializable;

public record BookDetailDto (
    ListingDto listingDto,
    BookDto bookDto,
    CopyDto copyDto

) implements Serializable
        {}
