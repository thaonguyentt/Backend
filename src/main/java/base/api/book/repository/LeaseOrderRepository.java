package base.api.book.repository;

import base.api.book.entity.LeaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaseOrderRepository extends JpaRepository<LeaseOrder, Long> {
}