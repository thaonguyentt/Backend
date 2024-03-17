package base.api.user;

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
) {}
