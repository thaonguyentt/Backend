package base.api.payment.dto;

import base.api.payment.entity.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.With;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@With
public record PaymentCreateRequest(
    Long payeeId,
    BigDecimal amount,
    String currency,
    PaymentMethod paymentMethod,
    String description
) {}
