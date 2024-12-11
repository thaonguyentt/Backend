package base.api.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.With;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@With
public record SaleOrderVoucherShopDto(
        Long id,
        Long saleOrderId,
        Long voucherId,
        BigDecimal discountAmount

) implements Serializable
    {}
