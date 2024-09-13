package base.api.book.mapper;

import base.api.book.dto.SaleOrderDto;
import base.api.book.entity.Genre;
import base.api.book.entity.SaleOrder;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SaleOrderMapper {
    SaleOrder toEntity(SaleOrderDto saleOrderDto);

    SaleOrderDto toDto(SaleOrder saleOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SaleOrder partialUpdate(SaleOrderDto saleOrderDto, @MappingTarget SaleOrder saleOrder);

}
