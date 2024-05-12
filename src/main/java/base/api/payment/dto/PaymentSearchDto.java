package base.api.payment.dto;

import base.api.payment.entity.PaymentMethod;
import base.api.payment.entity.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.With;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@With
public record PaymentSearchDto (
    List<PaymentStatus> statusList,
    Long payeeId,
    Long payerId,
    List<PaymentMethod> paymentMethodList
) {}
