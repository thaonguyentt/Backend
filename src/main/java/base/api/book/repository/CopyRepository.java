package base.api.book.repository;


import base.api.book.entity.Copy;
import base.api.book.entity.support.CopyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CopyRepository extends JpaRepository<Copy, Long>, JpaSpecificationExecutor<Copy> {
    public List<Copy> findByOwnerIdAndCopyStatus (Long id, CopyStatus copyStatus);
}