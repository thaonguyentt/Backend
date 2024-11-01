package base.api.book.repository;

import base.api.book.entity.VoucherShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherShopRepository extends JpaRepository<VoucherShop, Long> {
    List<VoucherShop> findByNameContaining(String name);
    boolean existsByCode(String code);
}