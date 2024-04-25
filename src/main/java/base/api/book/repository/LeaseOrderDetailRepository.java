package base.api.book.repository;

import base.api.book.entity.LeaseOrder;
import base.api.book.entity.LeaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface LeaseOrderDetailRepository extends JpaRepository<LeaseOrderDetail, Long> {
    public Set<LeaseOrderDetail> findLeaseOrderDetailByLeaseOrderId (Long leaseOrderId);

    public void deleteLeaseOrderDetailByLeaseOrderId (Long leaseOrderId);
}
