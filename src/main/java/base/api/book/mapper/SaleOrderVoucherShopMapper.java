package base.api.book.mapper;

import base.api.book.dto.SaleOrderVoucherShopDto;
import base.api.book.entity.SaleOrderVoucherShop;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SaleOrderVoucherShopMapper {
//    @Mapping(source = "saleOrderId", target = "saleOrder.id")
//    @Mapping(source = "voucherId", target = "voucherShop.id")
    SaleOrderVoucherShop toEntity(SaleOrderVoucherShopDto saleOrderVoucherShopDto);

//    @Mapping(source = "saleOrder.id", target = "saleOrderId")
//    @Mapping(source = "voucherShop.id", target = "voucherId")
    SaleOrderVoucherShopDto toDto(SaleOrderVoucherShop saleOrderVoucherShop);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(source = "listingId", target = "listing.id")
    SaleOrderVoucherShop partialUpdate(SaleOrderVoucherShopDto saleOrderVoucherShopDto, @MappingTarget SaleOrderVoucherShop saleOrderVoucherShop);
}