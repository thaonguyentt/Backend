package base.api.book.service;

import base.api.book.entity.LeaseOrderDetail;
import base.api.book.mapper.LeaseOrderDetailMapper;
import base.api.book.repository.LeaseOrderDetailRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class LeaseOrderDetailService {
    private final LeaseOrderDetailRepository leaseOrderDetailRepository;
    private final LeaseOrderDetailMapper leaseOrderDetailMapper;

    public LeaseOrderDetailService(LeaseOrderDetailRepository leaseOrderDetailRepository, LeaseOrderDetailMapper leaseOrderDetailMapper) {
        this.leaseOrderDetailRepository = leaseOrderDetailRepository;
        this.leaseOrderDetailMapper = leaseOrderDetailMapper;
    }

    public Set<LeaseOrderDetail> getLeaseOrderDetailByLeaseOrderId (Long leaseOrderId) {
        return leaseOrderDetailRepository.findLeaseOrderDetailByLeaseOrderId(leaseOrderId);
    }

}
