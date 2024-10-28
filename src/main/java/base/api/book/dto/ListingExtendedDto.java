package base.api.book.dto;

import base.api.book.entity.Book;
import base.api.book.entity.Copy;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ListingExtendedDto (
        Long id,
        @NotNull Long ownerId,
        Integer quantity,
        String address,
        LocalDate expiryDate,
        BigDecimal leaseRate,
        BigDecimal depositFee,
        BigDecimal penaltyRate,
        BigDecimal price,
        String description,
        BigDecimal allowRent,
        BigDecimal allowPurchase,
        CopyDto copy,
        BookDto book
        ) implements Serializable {}
