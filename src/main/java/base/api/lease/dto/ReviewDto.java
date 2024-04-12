package base.api.lease.dto;


import base.api.lease.entity.Review;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
/**
 * DTO for {@link base.api.lease.entity.Review}
 */
public record ReviewDto (
        Long id,
        BigDecimal score,
        String description,
        String[] imageLink,
        Long leaseOrderId,
        Long  userId,
        Long listingId,
        LocalDate createdDate,
        LocalDate updatedDate,
        LocalDate deletedDate

)implements Serializable {}
