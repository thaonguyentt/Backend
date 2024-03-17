package base.api.book.repository;

import base.api.book.entity.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CopyRepository extends JpaRepository<Copy, Long>, JpaSpecificationExecutor<Copy> {
}