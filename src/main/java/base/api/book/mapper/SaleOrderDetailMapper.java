package base.api.book.mapper;

import base.api.book.dto.LeaseOrderDetailDto;
import base.api.book.dto.SaleOrderDetailDto;
import base.api.book.entity.LeaseOrderDetail;
import base.api.book.entity.SaleOrderDetail;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SaleOrderDetailMapper {
//    @Mapping(source = "listingId", target = "listing.id")
    SaleOrderDetail toEntity(SaleOrderDetailDto saleOrderDetailDto);

    @Mapping(source = "listing.id", target = "listingId")
    @Mapping(source = "copy.id", target = "copyId")
    SaleOrderDetailDto toDto(SaleOrderDetail saleOrderDetail);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "listingId", target = "listing.id")
    SaleOrderDetail partialUpdate(SaleOrderDetailDto saleOrderDetailDto, @MappingTarget SaleOrderDetail saleOrderDetail);
}