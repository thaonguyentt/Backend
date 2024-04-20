package base.api.book.repository;

import base.api.book.entity.LeaseOrder;
import base.api.book.entity.support.LeaseOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface LeaseOrderRepository extends JpaRepository<LeaseOrder, Long> {
    public List<LeaseOrder> findLeaseOrderByLesseeId (Long id);
    public List<LeaseOrder>findByLesseeIdAndStatusIn(Long id, List<LeaseOrderStatus> leaseOrderStatus);
    public List<LeaseOrder>findByLessorIdAndStatusIn(Long id, List<LeaseOrderStatus> leaseOrderStatus);

    public List<LeaseOrder> findLeseOrderByLessorId (Long id);

}