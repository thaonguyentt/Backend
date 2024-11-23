package base.api.book.repository;

import base.api.book.entity.SaleOrderDetail;
import base.api.book.entity.SaleOrderVoucherSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SaleOrderVoucherSessionRepository extends JpaRepository<SaleOrderVoucherSession, Long>, JpaSpecificationExecutor<ReviewRepository> {
    Optional<SaleOrderVoucherSession> findBySaleOrderId(Long id);
}
