package base.api.book.mapper;

import base.api.book.dto.LeaseOrderDto;
import base.api.book.entity.LeaseOrder;
import org.mapstruct.*;

@Mapper(
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  componentModel = MappingConstants.ComponentModel.SPRING,
  uses = LeaseOrderDetailMapper.class)
public interface LeaseOrderMapper {
    LeaseOrder toEntity(LeaseOrderDto leaseOrderDto);

    @AfterMapping
    default void linkLeaseOrderDetails(@MappingTarget LeaseOrder leaseOrder) {
        leaseOrder.getLeaseOrderDetails().forEach(leaseOrderDetail -> leaseOrderDetail.setLeaseOrder(leaseOrder));
    }

    @AfterMapping
    default void linkReviews(@MappingTarget LeaseOrder leaseOrder) {
        leaseOrder.getReviews().forEach(review -> review.setLeaseOrder(leaseOrder));
    }

    LeaseOrderDto toDto(LeaseOrder leaseOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    LeaseOrder partialUpdate(LeaseOrderDto leaseOrderDto, @MappingTarget LeaseOrder leaseOrder);
}