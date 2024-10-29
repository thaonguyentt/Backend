package base.api.book.dto;

import base.api.user.UserDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record ListingDetailDto(
        Long id,
        UserDto user,
//        Long ownerId,
        Integer quantity,
        String address,
        BigDecimal leaseRate,
        BigDecimal depositFee,
        BigDecimal penaltyRate,
        String description,
        BigDecimal price,
        CopyDto copy,
        BookDto book,
        List<ReviewDto> review,
        Long bookOwned,
        Long bookLeasing

) implements Serializable
        {}
