package base.api.book.service;

import base.api.book.dto.LeaseOrderDto;
import base.api.book.repository.LeaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaseOrderService {

  @Autowired
  LeaseOrderRepository leaseOrderRepository;

  public LeaseOrderDto createLeaseOrder(LeaseOrderDto leaseOrderDto) {
    if (leaseOrderDto.id() != null) {
      throw new IllegalArgumentException("id should be blank");
    }
    // TODO verify argument


    // TODO do stuff here



    return null;
  }
}
