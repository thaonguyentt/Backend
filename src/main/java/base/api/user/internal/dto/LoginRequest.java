package base.api.user.internal.dto;

public record LoginRequest (
  String loginName,
  String password
) {}
