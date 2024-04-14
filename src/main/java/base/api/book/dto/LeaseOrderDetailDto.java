package base.api.book.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link base.api.book.entity.LeaseOrderDetail}
 */
public record LeaseOrderDetailDto(Long id, Long listingId, BigDecimal leaseRate, BigDecimal depositFee,
                                  BigDecimal penaltyRate) implements Serializable {
}