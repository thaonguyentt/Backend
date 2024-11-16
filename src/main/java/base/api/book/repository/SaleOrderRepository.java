package base.api.book.repository;


import base.api.book.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import base.api.book.entity.SaleOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SaleOrderRepository extends JpaRepository<SaleOrder, Long>, JpaSpecificationExecutor<SaleOrder> {
    List<SaleOrder> findSaleOrderBySellerId(Long Id);
    List<SaleOrder> findSaleOrderByBuyerId(Long Id);
}
