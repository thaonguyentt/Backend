package base.api.common.internal.mapper;

import base.api.common.AreaDTO;
import base.api.common.internal.entity.Area;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AreaMapper {
  Area toEntity(AreaDTO areaDTO);
  List<Area> toEntity(List<AreaDTO> list);
  AreaDTO toDto(Area area);
  List<AreaDTO> toDto(List<Area> list);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Area partialUpdate(AreaDTO areaDTO, @MappingTarget Area area);
}