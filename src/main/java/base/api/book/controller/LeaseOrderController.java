package base.api.book.controller;

import base.api.book.dto.LeaseOrderDto;
import base.api.book.repository.LeaseOrderRepository;
import base.api.book.service.LeaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeaseOrderController {
  @Autowired
  LeaseOrderRepository leaseOrderRepository;

  @Autowired
  LeaseOrderService leaseOrderService;

  @PostMapping("/api/leaseOrder")
  public LeaseOrderDto createLeaseOrder(@RequestBody LeaseOrderDto leaseOrderDto) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return leaseOrderService.createLeaseOrder(auth, leaseOrderDto);
  }

  @GetMapping("/api/leaseOrder")
  public ResponseEntity<Object> getLeaseOrder() {
    var obj = leaseOrderRepository.getReferenceById(1L);
//    obj.getDepositPaymentId();
    return new ResponseEntity<>(obj.toString(), HttpStatusCode.valueOf(200));
  }
}
