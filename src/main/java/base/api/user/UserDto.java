package base.api.user;

import java.io.Serializable;
import java.time.LocalDate;

public record UserDto(
  Long id,
  String username,
  String email,
  String phoneNumber,
  String firstName,
  String lastName,
  LocalDate birthDate,
  String avatarUrl,
  String address,
  String password

) implements Serializable {}
