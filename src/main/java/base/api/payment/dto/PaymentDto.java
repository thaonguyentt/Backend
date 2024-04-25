package base.api.payment.dto;

import base.api.payment.entity.PaymentMethod;
import base.api.payment.entity.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.With;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link base.api.payment.entity.Payment}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@With
public record PaymentDto(
  Long id,
  PaymentStatus paymentStatus,
  Long payerId,
  Long payeeId,
  String currency,
  BigDecimal amount,
  PaymentMethod paymentMethod,
  String description,
  String bankTransferImgUrl
) implements Serializable { }