package base.api.common.internal.mapper;

import base.api.common.AreaDto;
import base.api.common.internal.entity.Area;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AreaMapper {
  Area toEntity(AreaDto areaDTO);
  List<Area> toEntity(List<AreaDto> list);
  AreaDto toDto(Area area);
  List<AreaDto> toDto(List<Area> list);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Area partialUpdate(AreaDto areaDTO, @MappingTarget Area area);
}