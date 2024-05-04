package base.api.book.repository;

import base.api.book.entity.LeaseOrder;
import base.api.book.entity.support.LeaseOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface LeaseOrderRepository extends JpaRepository<LeaseOrder, Long> {
    public List<LeaseOrder> findLeaseOrderByLesseeId (Long id);

    public List<LeaseOrder> findByLesseeIdAndStatusIn(Long id, List<LeaseOrderStatus> leaseOrderStatus);

    public List<LeaseOrder> findByLessorIdAndStatusIn(Long id, List<LeaseOrderStatus> leaseOrderStatus);

    public List<LeaseOrder> findLeseOrderByLessorId (Long id);

    @Query(
      value = """
        select * from lease_order
        where return_date < now()
        and (status = 'PAYMENT_SUCCESS'
            or status = 'DELIVERED'
            or status = 'RETURNING'
            )
      """,
      nativeQuery = true
    )
    List<LeaseOrder> findLateReturnLeaseOrder();

    @Query(
      value = """
        select * from lease_order
        where date_add(created_date, '1 day') > now()
        and status = 'ORDERED_PAYMENT_PENDING'
      """,
      nativeQuery = true
    )
    List<LeaseOrder> findLatePaymentLeaseOrder();
}