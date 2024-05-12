package base.api.book.dto;

import base.api.book.entity.Review;
import base.api.user.UserDto;
import base.api.user.internal.entity.User;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record LeaseOrderDtoDetail (
    LeaseOrderDto leaseOrder,
    ListingDetailDto listing,
    UserDto lessor,
    UserDto lessee,
    BigDecimal totalPenaltyFee
) implements Serializable
        {}
