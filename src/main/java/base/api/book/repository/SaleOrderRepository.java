package base.api.book.repository;


import base.api.book.entity.Review;
import base.api.book.entity.support.SellOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import base.api.book.entity.SaleOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SaleOrderRepository extends JpaRepository<SaleOrder, Long>, JpaSpecificationExecutor<SaleOrder> {
    List<SaleOrder> findBySellerId(Long Id);
    List<SaleOrder> findByBuyerId(Long Id);
    List<SaleOrder> findBySellerIdAndStatus(Long id, SellOrderStatus status);
    List<SaleOrder> findByBuyerIdAndStatus(Long id, SellOrderStatus status);
}
