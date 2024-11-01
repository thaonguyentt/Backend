package base.api.book.mapper;

import base.api.book.dto.VoucherShopDto;
import base.api.book.entity.VoucherShop;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VoucherShopMapper {

    // Mapping from DTO to entity
    VoucherShop toEntity(VoucherShopDto voucherShopDto);

    // Mapping from entity to DTO
    VoucherShopDto toDto(VoucherShop voucherShop);

    // update from DTO to entity, ignore null value
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VoucherShop partialUpdate(VoucherShopDto voucherShopDto, @MappingTarget VoucherShop voucherShop);

}
