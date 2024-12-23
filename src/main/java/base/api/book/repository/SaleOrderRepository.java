package base.api.book.repository;


import base.api.book.entity.LeaseOrder;
import base.api.book.entity.Review;
import base.api.book.entity.support.SellOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import base.api.book.entity.SaleOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleOrderRepository extends JpaRepository<SaleOrder, Long>, JpaSpecificationExecutor<SaleOrder> {
    List<SaleOrder> findBySellerId(Long Id);
    List<SaleOrder> findByBuyerId(Long Id);
    List<SaleOrder> findBySellerIdAndStatus(Long id, SellOrderStatus status);
    List<SaleOrder> findByBuyerIdAndStatus(Long id, SellOrderStatus status);


    @Query(
            value = """
                      select * from sales_order
                      where (date_add(now(), '1 day') < created_date
                      and status = 'ORDERED_PAYMENT_PENDING')
                    """,
            nativeQuery = true
    )
    List<SaleOrder> findLatePaymentSaleOrder();


    @Query(
            value = """
                select * from sales_order
                where (status = 'PAYMENT_SUCCESS'
                and status = 'PAYMENT_SUCCESS')
    
            """,
            nativeQuery = true
    )
    List<SaleOrder> findLateDeliverySaleOrder();
}
