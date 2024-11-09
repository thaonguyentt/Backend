package base.api.book.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link base.api.book.entity.VoucherSession}
 */
public record VoucherSessionDto(
        Long id,
        @NotNull String name,
        @NotNull String code,         // Add code field
        LocalDate createdDate,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        BigDecimal minValue,
        BigDecimal discountAmount,
        BigDecimal discountPercentage,
        @NotNull Integer voucherType // 0 is discountAmount value, 1 is discountPercentage
) implements Serializable {}