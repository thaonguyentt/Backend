package base.api.book.mapper;

import base.api.book.entity.Copy;
import base.api.book.dto.CopyDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CopyMapper {
  @Mapping(source = "bookId", target = "book.id")
  Copy toEntity(CopyDto copyDto);

  @Mapping(source = "book.id", target = "bookId")
  CopyDto toDto(Copy copy);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(source = "bookId", target = "book.id")
  Copy partialUpdate(CopyDto copyDto, @MappingTarget Copy copy);
}