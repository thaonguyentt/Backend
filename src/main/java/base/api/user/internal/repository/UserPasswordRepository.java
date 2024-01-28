package base.api.user.internal.repository;

import base.api.user.internal.entity.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPasswordRepository extends JpaRepository<UserPassword, Long> {
  public Optional<UserPassword> getByUserId(Long userId);
}
