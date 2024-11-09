package base.api.book.repository;

import base.api.book.entity.VoucherShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherShopRepository extends JpaRepository<VoucherShop, Long> {
    List<VoucherShop> findByNameContainingOrCodeContaining(String keyword, String keyword2);
    boolean existsByCode(String code);
}