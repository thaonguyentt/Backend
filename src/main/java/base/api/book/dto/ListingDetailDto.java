package base.api.book.dto;

import base.api.lease.dto.ReviewDto;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ListingDetailDto(
        Long id,
        @NotNull Long ownerId,
        Integer quantity,
        String address,
        LocalDate expiryDate,
        BigDecimal leaseRate,
        BigDecimal depositFee,
        BigDecimal penaltyRate,
        String description,
        CopyDto copy,
        BookDto book,
        List<ReviewDto> review,
        String FirstName,
        String LastName,
        String LinkImg

) implements Serializable
        {}
