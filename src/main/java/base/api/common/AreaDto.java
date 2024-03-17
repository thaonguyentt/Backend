package base.api.common;

import base.api.common.internal.entity.Area;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link Area} entity
 */
public record AreaDto(
  Integer id,
  @NotNull String code,
  String name,
  Integer type,
  List<AreaDto> ancestor
) implements Serializable {}