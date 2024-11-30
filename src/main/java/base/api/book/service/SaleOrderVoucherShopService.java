package base.api.book.service;

import base.api.book.dto.SaleOrderDetailDto;
import base.api.book.dto.SaleOrderVoucherSessionDto;
import base.api.book.dto.SaleOrderVoucherShopDto;
import base.api.book.entity.SaleOrderVoucherSession;
import base.api.book.entity.SaleOrderVoucherShop;
import base.api.book.mapper.SaleOrderDetailMapper;
import base.api.book.mapper.SaleOrderVoucherShopMapper;
import base.api.book.repository.SaleOrderDetailRepository;
import base.api.book.repository.SaleOrderVoucherShopRepository;
import base.api.system.security.Identity;
import base.api.system.security.IdentityUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class SaleOrderVoucherShopService {

    private final SaleOrderVoucherShopRepository saleOrderVoucherShopRepository;

    private final SaleOrderVoucherShopMapper saleOrderVoucherShopMapper;
    public SaleOrderVoucherShopDto getSaleOrderVoucherShop(Long id) {
        return saleOrderVoucherShopRepository.findById(id).map(saleOrderVoucherShopMapper::toDto).orElse(null);
    }

    public SaleOrderVoucherShopDto create(Identity identity, SaleOrderVoucherShopDto saleOrderVoucherShopDto) {
        IdentityUtil.requireAuthenticated(identity);
        IdentityUtil.requireHasAnyRole(identity, "ADMIN", "USER", "SYSTEM");

        SaleOrderVoucherShop newSaleOrderVoucherShop = saleOrderVoucherShopMapper.toEntity(saleOrderVoucherShopDto);

        SaleOrderVoucherShop createdSaleOrderVoucherShop = saleOrderVoucherShopRepository.save(newSaleOrderVoucherShop);

        return saleOrderVoucherShopMapper.toDto(createdSaleOrderVoucherShop);

    }

    public SaleOrderVoucherShopDto getSaleOrderVoucherBySaleOrderId (Long id) {
        Optional<SaleOrderVoucherShop> saleOrderVoucherShop= saleOrderVoucherShopRepository.findBySaleOrderId(id);
        return saleOrderVoucherShop.map(saleOrderVoucherShopMapper::toDto).orElse(null);
    }


}
