package base.api.book.dto;

import base.api.user.UserDto;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ListingDetailDto(
        Long id,
//        UserDto user,
//        Long ownerId,
        Integer quantity,
        String address,
        BigDecimal leaseRate,
        BigDecimal depositFee,
        BigDecimal penaltyRate,
        String description,
        CopyDto copy,
        BookDto book,
        List<ReviewDto> review,
        Long bookOwned,
        Long bookLeasing

) implements Serializable
        {}
