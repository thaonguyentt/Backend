package base.api.payment.service;

import base.api.payment.dto.PaymentDto;
import base.api.payment.entity.Payment;
import base.api.payment.entity.PaymentStatus;
import base.api.payment.mapper.PaymentMapper;
import base.api.payment.repository.PaymentRepository;
import base.api.system.security.SecurityUtils;
import jakarta.transaction.Transactional;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<PaymentDto> searchPayment(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(paymentMapper::toDto);
    }

    public PaymentDto create(Authentication auth, PaymentDto paymentDto) {
        SecurityUtils.requireAuthentication(auth);
        // WHO can create payment ?

        Payment newPayment = paymentMapper.toEntity(paymentDto);

        // Payment mới luôn có trạng thái PENDING
        newPayment.setPaymentStatus(PaymentStatus.PAYMENT_PENDING);

        Payment createdPayment = paymentRepository.save(newPayment);

        return paymentMapper.toDto(createdPayment);

    }

    /**
     * Thay đổi status của payment
     * Chỉ admin và hệ thống được đổi
     * @param auth
     * @param paymentId
     * @param newPaymentStatus
     * @return
     */
    PaymentDto updatePaymentStatus(Authentication auth, Long paymentId, PaymentStatus newPaymentStatus) {
        SecurityUtils.requireAuthentication(auth);
        // only ADMIN role can change payment status
        SecurityUtils.requireHasRole(auth,"ADMIN");

        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()-> new IllegalArgumentException("No such payment: " + paymentId));

        if (newPaymentStatus.canTransitionFrom(payment.getPaymentStatus())) {
            payment.setPaymentStatus(newPaymentStatus);
        } else { throw new IllegalArgumentException(); }

        return paymentMapper.toDto(paymentRepository.save(payment));

    }

    public PaymentDto updatePayment(Authentication auth, Long paymentId, PaymentDto paymentDto) {
        SecurityUtils.requireAuthentication(auth);
        // only ADMIN role can update payment
        SecurityUtils.requireHasRole(auth,"ADMIN");

        if (paymentDto.id() != null && !Objects.equals(paymentDto.id(), paymentId)) {
            throw new IllegalArgumentException("Payment id mismatch");
        }

        Payment payment = paymentMapper.toEntity(paymentDto);
        Payment updatedPayment = paymentRepository.save(payment);

        return paymentMapper.toDto(updatedPayment);
    }


}
