package base.api.book.mapper;

import base.api.book.dto.BookDto;
import base.api.book.entity.Book;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {
  @Mapping(source = "languageCode", target = "language.code")
  Book toEntity(BookDto bookDto);

  @Mapping(source = "language.code", target = "languageCode")
  BookDto toDto(Book book);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(source = "languageCode", target = "language.code")
  Book partialUpdate(BookDto bookDto, @MappingTarget Book book);
}