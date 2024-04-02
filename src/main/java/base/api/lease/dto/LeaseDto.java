package base.api.lease.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link base.api.lease.entity.Lease}
 */
public record LeaseDto (Long id,Long lessor_id,Long lessee_id,Long listing_id,Long status_id,
                        LocalDate created_date,LocalDate from_date,LocalDate to_date,LocalDate receive_date,
                        LocalDate return_date, String book_name, String lessor_address, String lessee_address,
                        BigDecimal price, BigDecimal penalty_fee, BigDecimal deposit, Long quantity, Long payment_method,
                        String link_img, Long payment_deposit_id, Long payment_refund_id, Long payment_pay_owner_id) implements Serializable {

}

