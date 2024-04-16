package base.api.book.repository;

import base.api.book.entity.LeaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaseOrderRepository extends JpaRepository<LeaseOrder, Long> {
    public List<LeaseOrder> findLeaseOrderByLesseeId (Long id);

}