package base.api.payment.service;

import base.api.authorization.UnauthorizedException;
import base.api.payment.dto.PaymentDto;
import base.api.payment.entity.Payment;
import base.api.payment.entity.PaymentStatus;
import base.api.payment.mapper.PaymentMapper;
import base.api.payment.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;


    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    public PaymentDto getPaymentById(Long paymentId) {
        return paymentMapper.toDto(paymentRepository.findById(paymentId).orElseThrow(()-> new IllegalArgumentException("No such payment: " + paymentId)));
    }

    public PaymentDto create(Authentication auth, PaymentDto paymentDto) {
        if (auth == null || auth.getPrincipal() == null) {
            throw new UnauthorizedException();
        }
        // WHO can create payment ?

        Payment newPayment = paymentMapper.toEntity(paymentDto);

        // Payment mới luôn có trạng thái PENDING
        newPayment.setPaymentStatus(PaymentStatus.PAYMENT_PENDING);

        Payment createdPayment = paymentRepository.save(newPayment);

        return paymentMapper.toDto(createdPayment);

    }

    public PaymentDto updatePaymentStatus(Authentication auth, Long paymentId, PaymentStatus newPaymentStatus) {
        if (auth == null || auth.getPrincipal() == null) {
            throw new UnauthorizedException();
        }
        // TODO WHO can update payment status ?

        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()-> new IllegalArgumentException("No such payment: " + paymentId));

        if (newPaymentStatus.canTransitionFrom(payment.getPaymentStatus())) {
            payment.setPaymentStatus(newPaymentStatus);
        } else { throw new IllegalArgumentException(); }

        return paymentMapper.toDto(paymentRepository.save(payment));

    }


}
