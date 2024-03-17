package base.api.book.mapper;

import base.api.book.entity.Copy;
import base.api.book.dto.CopyDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CopyMapper {
  @Mapping(source = "bookBookId", target = "book.id")
  Copy toEntity(CopyDto copyDto);

  @Mapping(source = "book.id", target = "bookBookId")
  CopyDto toDto(Copy copy);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(source = "bookBookId", target = "book.id")
  Copy partialUpdate(CopyDto copyDto, @MappingTarget Copy copy);
}