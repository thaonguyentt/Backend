package base.api.user;

import java.io.Serializable;
import java.time.LocalDate;

public record UserDto(
  Long id,
  String username,
  String firstName,
  String lastName,
  String email,
  String phoneNumber,
  String password,
  LocalDate birthDate
) implements Serializable {}
