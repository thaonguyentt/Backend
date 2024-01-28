package base.api.user.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import base.api.user.internal.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  public Optional<User> getByUsername(String username);
  public Optional<User> getByEmail(String email);
  public Optional<User> getByPhoneNumber(String phoneNumber);
  // public List<User> findByName(String name); // TODO find by combine firstname + lastname
}
