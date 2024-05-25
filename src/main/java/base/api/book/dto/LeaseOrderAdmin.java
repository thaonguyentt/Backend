package base.api.book.dto;

import base.api.user.UserDto;

import java.io.Serializable;
import java.math.BigDecimal;

public record LeaseOrderAdmin(
        LeaseOrderDto leaseOrder,
        ListingDetailDto listing,
        UserDto lessor,
        UserDto lessee,
        BigDecimal totalPenaltyFee,
        BigDecimal depositReturnFee,
        BigDecimal paidOwnerFee

) implements Serializable
        {}
