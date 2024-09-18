package base.api.book.repository;

import base.api.book.entity.SaleOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SaleOrderDetailRepository extends JpaRepository<SaleOrderDetail, Long>, JpaSpecificationExecutor<ReviewRepository> {
}
