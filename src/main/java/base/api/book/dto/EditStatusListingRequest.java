package base.api.book.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * DTO for {@link base.api.book.entity.Listing}
 */
public record EditStatusListingRequest (
        Long id ,
        @NotNull BigDecimal AllowPurchase,
        @NotNull BigDecimal AllowRent
        )
{
}
