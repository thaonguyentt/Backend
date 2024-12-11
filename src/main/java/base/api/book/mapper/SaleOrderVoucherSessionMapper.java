package base.api.book.mapper;

import base.api.book.dto.SaleOrderVoucherSessionDto;
import base.api.book.dto.SaleOrderVoucherShopDto;
import base.api.book.entity.SaleOrderVoucherSession;
import base.api.book.entity.SaleOrderVoucherShop;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SaleOrderVoucherSessionMapper {
//    @Mapping(source = "saleOrderId", target = "saleOrder.id")
//    @Mapping(source = "voucherSessionId", target = "voucherSession.id")
    SaleOrderVoucherSession toEntity(SaleOrderVoucherSessionDto saleOrderVoucherSessionDto);

//    @Mapping(source = "saleOrder.id", target = "saleOrderId")
//    @Mapping(source = "voucherSession.id", target = "voucherSessionId")
    SaleOrderVoucherSessionDto toDto(SaleOrderVoucherSession saleOrderVoucherSession);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(source = "listingId", target = "listing.id")
    SaleOrderVoucherSession partialUpdate(SaleOrderVoucherShopDto saleOrderVoucherShopDto, @MappingTarget SaleOrderVoucherSession saleOrderVoucherSession);
}