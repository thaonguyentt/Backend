package base.api.book.dto;

import base.api.book.entity.Listing;
import base.api.user.UserDto;

import java.io.Serializable;
import java.math.BigDecimal;

public record SaleOrderDetailDto(
        Long id,
        String title,
        Long listingId,
        Long copyId,
        BigDecimal price

) implements Serializable
    {}
