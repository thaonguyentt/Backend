package base.api.payment.controller;

import base.api.payment.dto.PaymentCreateRequest;
import base.api.payment.dto.PaymentDto;
import base.api.payment.entity.PaymentMethod;
import base.api.payment.service.PaymentService;
import base.api.system.security.Identity;
import base.api.system.security.IdentityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8082", "https://the-flying-bookstore.vercel.app"})
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/search")
    ResponseEntity<Page<PaymentDto>> searchPayment(
      @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
      @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
      @RequestParam(name = "sort") List<String> sortBy){
        Sort sort = Sort.unsorted();
        for (String sortField : sortBy) {
            sort = sort.and(Sort.by(sortField).ascending());
        }
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return ResponseEntity.ok(paymentService.searchPayment(pageRequest));
    }
    
    @GetMapping("/{id}")
    PaymentDto getPaymentById(@PathVariable(name = "id") Long id) {
        return paymentService.getPaymentById(id);
    }

    @PostMapping("/{id}")
    PaymentDto updatePaymentById(@PathVariable("id") Long paymentId, @RequestBody PaymentDto paymentDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Identity identity = IdentityUtil.fromSpringAuthentication(auth);

        return paymentService.updatePayment(identity, paymentId, paymentDto);
    }

    @GetMapping("/testcreate")
    PaymentDto testCreatePayment() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Identity identity = IdentityUtil.fromSpringAuthentication(auth);
        Long userId = Long.parseLong((String)auth.getPrincipal());

        return paymentService.create(
          identity,
          PaymentDto.builder()
            .payerId(userId)
            .payeeId(0L)
            .amount(BigDecimal.valueOf(80000L))
            .currency("VND")
            .paymentMethod(PaymentMethod.BANK_TRANSFER)
            .description("Test")
            .build()
        );
    }
}
