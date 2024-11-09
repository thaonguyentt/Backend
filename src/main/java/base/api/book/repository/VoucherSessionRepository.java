package base.api.book.repository;

import base.api.book.entity.VoucherSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherSessionRepository extends JpaRepository<VoucherSession,Long> {
    List<VoucherSession> findByNameContainingOrCodeContaining(String keyword, String keyword2);


    boolean existsByCode(String code);
}
