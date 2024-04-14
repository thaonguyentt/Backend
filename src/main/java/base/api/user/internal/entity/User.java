package base.api.user.internal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_USER")
public class User {

  @Id
  @GeneratedValue(generator = "user_id_generator")
  @SequenceGenerator(
      name = "user_id_generator",
      sequenceName = "user_user_id_seq",
      allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "email")
  private String email;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(name = "avatar_url")
  private String avatarUrl;

  @Column(name = "address")
  private String address;

//  @Column(name = "created_date")
//  private LocalDate createdDate;
//
//  @Column(name = "updated_date")
//  private LocalDate updatedDate;
}
