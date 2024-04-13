package base.api.book.mapper;

import base.api.book.dto.ReviewDto;
import base.api.book.entity.Review;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {
    @Mapping(source = "leaseOrderId", target = "leaseOrder.id")
    Review toEntity(ReviewDto reviewDto);

    @Mapping(source = "leaseOrder.id", target = "leaseOrderId")
    ReviewDto toDto(Review review);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "leaseOrderId", target = "leaseOrder.id")
    Review partialUpdate(ReviewDto reviewDto, @MappingTarget Review review);
}