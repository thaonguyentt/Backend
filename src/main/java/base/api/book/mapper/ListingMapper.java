package base.api.book.mapper;

import base.api.book.dto.ListingDto;
import base.api.book.entity.Listing;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ListingMapper {
//  @Mapping(source = "copyId", target = "copy.id")
  Listing toEntity(ListingDto listingDto);

//  @Mapping(source = "copy.id", target = "copyId")
  ListingDto toDto(Listing listing);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(source = "copyId", target = "copy.id")
  Listing partialUpdate(ListingDto listingDto, @MappingTarget Listing listing);
}