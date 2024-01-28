package base.api.user.internal.dto;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record LoginResponse(
  String token
) {}
