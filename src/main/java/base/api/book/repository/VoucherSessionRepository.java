package base.api.book.repository;

import base.api.book.entity.VoucherSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherSessionRepository extends JpaRepository<VoucherSession,Long> {
    List<VoucherSession> findByNameContaining(String name);

    boolean existsByCode(String code);
}
