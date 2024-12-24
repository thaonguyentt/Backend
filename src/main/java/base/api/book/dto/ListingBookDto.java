package base.api.book.dto;

import base.api.user.UserDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record ListingBookDto(
        Long id,
        Integer quantity,
        String address,
        BigDecimal depositFee,
        String description,
        BigDecimal price,
        BookDto book
) implements Serializable
        {}
