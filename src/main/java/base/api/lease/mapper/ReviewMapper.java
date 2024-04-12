package base.api.lease.mapper;

import base.api.lease.dto.ReviewDto;
import base.api.lease.entity.Review;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {

    @Mapping(source = "leaseOrderId", target = "leaseOrder.id")
    @Mapping(source = "userId", target = "user.id")
    Review toEntity(ReviewDto reviewDto);
    @Mapping(source = "leaseOrder.id", target = "leaseOrderId")
    @Mapping(source = "user.id", target = "userId")
    ReviewDto toDto(Review review);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "leaseOrderId", target = "leaseOrder.id")
    Review partialUpdate(ReviewDto reviewDto, @MappingTarget Review review);

}
