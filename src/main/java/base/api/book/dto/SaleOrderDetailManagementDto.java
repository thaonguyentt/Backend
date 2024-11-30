package base.api.book.dto;

import base.api.book.entity.Listing;
import base.api.user.UserDto;

import java.io.Serializable;
import java.math.BigDecimal;

public record SaleOrderDetailManagementDto(
    SaleOrderDto saleOrder,

    ListingDto listing,
    UserDto Seller,
    UserDto Buyer,
    SaleOrderVoucherShopDto voucherShop,
    SaleOrderVoucherSessionDto voucherSession,
    BigDecimal totalPrice
) implements Serializable
        {}
