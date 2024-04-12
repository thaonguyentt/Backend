package base.api.lease.repository;

import base.api.lease.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    List<Review> findByOwnerId (Long ownerId);
}
