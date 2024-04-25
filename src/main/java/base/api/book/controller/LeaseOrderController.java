package base.api.book.controller;

import base.api.book.dto.LeaseOrderCreateRequest;
import base.api.book.dto.LeaseOrderDto;
import base.api.book.dto.search.LeaseOrderUpdateRequest;
import base.api.book.entity.support.LeaseOrderStatus;
import base.api.book.repository.LeaseOrderRepository;
import base.api.book.service.LeaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
public class LeaseOrderController {
  @Autowired
  LeaseOrderRepository leaseOrderRepository;

  @Autowired
  LeaseOrderService leaseOrderService;


//  @PostMapping("/api/leaseOrder")
//  public LeaseOrderDto createLeaseOrder(@RequestBody LeaseOrderDto leaseOrderDto) {
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    return leaseOrderService.createLeaseOrder(auth, leaseOrderDto);
//  }

  @PostMapping("/api/leaseOrder")
  public LeaseOrderDto createLeaseOrder2(@RequestBody LeaseOrderCreateRequest request) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return leaseOrderService.createLeaseOrder(auth, request);
  }

  @GetMapping("/api/leaseOrder")
  public ResponseEntity<Object> getLeaseOrder() {
    var obj = leaseOrderRepository.getReferenceById(1L);
//    obj.getDepositPaymentId();
    return new ResponseEntity<>(obj.toString(), HttpStatusCode.valueOf(200));
  }
  @GetMapping("/api/leaseOrder/search/lessor/{id}")
  public ResponseEntity<List<LeaseOrderDto>> getLeaseOrderByLessorId (@PathVariable Long id) {
    return ResponseEntity.ok(leaseOrderService.getLeaseOrderByLessorId(id));
  }

  @GetMapping("/api/leaseOrder/search/lessee/{id}")
  public ResponseEntity<List<LeaseOrderDto>> getLeaseOrderByLesseeId (@PathVariable Long id) {
    return ResponseEntity.ok(leaseOrderService.getLeaseOrderByLesseeId(id));
  }

  @GetMapping ("/api/leaseOrder/search/lessor/status/{id}")
  public ResponseEntity<List<LeaseOrderDto>> getLeaseOrderByLessorIdAndStatus (@PathVariable Long id, @RequestParam(name="status") Long status) {
    List<LeaseOrderStatus> leaseOrderStatus = new ArrayList<>();
    if (status == 1) {
      leaseOrderStatus.add(LeaseOrderStatus.ORDERED_PAYMENT_PENDING);
      leaseOrderStatus.add(LeaseOrderStatus.PAYMENT_SUCCESS);
    } else if (status == 2) {
      leaseOrderStatus.add(LeaseOrderStatus.DELIVERED);
      leaseOrderStatus.add(LeaseOrderStatus.RETURNING);
    } else if (status == 3) {
      leaseOrderStatus.add(LeaseOrderStatus.LATE_RETURN);
    } else {
      leaseOrderStatus.add(LeaseOrderStatus.RETURNED);
      leaseOrderStatus.add(LeaseOrderStatus.DEPOSIT_RETURNED);
      leaseOrderStatus.add(LeaseOrderStatus.PAID_OWNER);
    }
    return ResponseEntity.ok(leaseOrderService.getLeaseOrderByLessorIdAndStatus(id,leaseOrderStatus));

  }

  @GetMapping ("/api/leaseOrder/edit/status")
  public ResponseEntity<LeaseOrderDto> updateStatus (@RequestParam(name="id") Long id, @RequestParam(name="status") LeaseOrderStatus status) {
    return ResponseEntity.ok(leaseOrderService.updateLeaseOrderStatus(id, status));
  }

  @GetMapping ("/api/leaseOrder/search/lessee/status/{id}")
  public ResponseEntity<List<LeaseOrderDto>> getLeaseOrderByLesseeIdAndStatus (@PathVariable Long id, @RequestParam(name="status") Long status) {
    List<LeaseOrderStatus> leaseOrderStatus = new ArrayList<>();
    if (status == 1) {
      leaseOrderStatus.add(LeaseOrderStatus.ORDERED_PAYMENT_PENDING);
      leaseOrderStatus.add(LeaseOrderStatus.PAYMENT_SUCCESS);
    } else if (status == 2) {
      leaseOrderStatus.add(LeaseOrderStatus.DELIVERED);
      leaseOrderStatus.add(LeaseOrderStatus.RETURNING);
    } else if (status == 3) {
      leaseOrderStatus.add(LeaseOrderStatus.LATE_RETURN);
    } else {
      leaseOrderStatus.add(LeaseOrderStatus.RETURNED);
      leaseOrderStatus.add(LeaseOrderStatus.DEPOSIT_RETURNED);
      leaseOrderStatus.add(LeaseOrderStatus.PAID_OWNER);
    }
    return ResponseEntity.ok(leaseOrderService.getLeaseOrderByLesseeIdAndStatus(id,leaseOrderStatus));

  }
  
  
  
  @PostMapping ("/api/leaseOrder/updateReceive")
  public ResponseEntity<LeaseOrderDto> updateReceive (@RequestBody LeaseOrderUpdateRequest updateRequest) {
    return null;
  }

  @GetMapping ("/api/leaseOrder/{id}")
  public ResponseEntity<LeaseOrderDto> getLeaseOrder (@PathVariable Long id) {
    return ResponseEntity.ok(leaseOrderService.getLeaseOrderById(id));
  }


}
