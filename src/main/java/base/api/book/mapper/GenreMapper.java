package base.api.book.mapper;

import base.api.book.dto.GenreDto;
import base.api.book.entity.Genre;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreMapper {
  Genre toEntity(GenreDto genreDto);

  GenreDto toDto(Genre genre);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Genre partialUpdate(GenreDto genreDto, @MappingTarget Genre genre);
}