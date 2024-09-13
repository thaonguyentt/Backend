package base.api.book.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import base.api.book.entity.SaleOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SaleOrderRepository extends JpaRepository<SaleOrder, Long>, JpaSpecificationExecutor<SaleOrder> {

}
