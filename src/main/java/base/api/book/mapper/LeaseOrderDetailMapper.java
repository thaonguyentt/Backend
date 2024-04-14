package base.api.book.mapper;

import base.api.book.dto.LeaseOrderDetailDto;
import base.api.book.entity.LeaseOrderDetail;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LeaseOrderDetailMapper {
    @Mapping(source = "listingId", target = "listing.id")
    LeaseOrderDetail toEntity(LeaseOrderDetailDto leaseOrderDetailDto);

    @Mapping(source = "listing.id", target = "listingId")
    LeaseOrderDetailDto toDto(LeaseOrderDetail leaseOrderDetail);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "listingId", target = "listing.id")
    LeaseOrderDetail partialUpdate(LeaseOrderDetailDto leaseOrderDetailDto, @MappingTarget LeaseOrderDetail leaseOrderDetail);
}