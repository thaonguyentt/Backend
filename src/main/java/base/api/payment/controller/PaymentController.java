package base.api.payment.controller;

import base.api.payment.dto.PaymentDto;
import base.api.payment.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    
    @GetMapping("/{id}")
    PaymentDto getPaymentById(@PathVariable(name = "id") Long id) {
        return paymentService.getPaymentById(id);
    }
}
