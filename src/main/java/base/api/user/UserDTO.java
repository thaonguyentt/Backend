package base.api.user;

import java.time.LocalDate;

public record UserDTO (
  Long id,
  String username,
  String firstName,
  String lastName,
  String email,
  String phoneNumber,
  String password,
  LocalDate birthDate
) {}
