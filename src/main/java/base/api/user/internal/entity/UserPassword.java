package base.api.user.internal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_PASSWORD")
public class UserPassword {
  @Id
  @GeneratedValue(generator = "user_password_generator")
  @SequenceGenerator(
    name = "user_password_generator",
    sequenceName = "user_password_id_seq",
    allocationSize = 1
  )
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "password")
  private String password;
}
