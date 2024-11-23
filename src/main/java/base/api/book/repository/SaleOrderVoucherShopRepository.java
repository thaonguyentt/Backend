package base.api.book.repository;

import base.api.book.entity.SaleOrderDetail;
import base.api.book.entity.SaleOrderVoucherShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SaleOrderVoucherShopRepository extends JpaRepository<SaleOrderVoucherShop, Long>, JpaSpecificationExecutor<ReviewRepository> {
    Optional<SaleOrderVoucherShop> findBySaleOrderId(Long id);
}
