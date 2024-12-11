package base.api.book.service;

import base.api.book.dto.SaleOrderVoucherSessionDto;
import base.api.book.dto.SaleOrderVoucherShopDto;
import base.api.book.entity.SaleOrderVoucherSession;
import base.api.book.mapper.SaleOrderVoucherSessionMapper;
import base.api.book.mapper.SaleOrderVoucherShopMapper;
import base.api.book.repository.SaleOrderVoucherSessionRepository;
import base.api.book.repository.SaleOrderVoucherShopRepository;
import base.api.payment.dto.PaymentDto;
import base.api.payment.entity.Payment;
import base.api.payment.entity.PaymentStatus;
import base.api.system.security.Identity;
import base.api.system.security.IdentityUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class SaleOrderVoucherSessionService {

    private final SaleOrderVoucherSessionRepository saleOrderVoucherSessionRepository;

    private final SaleOrderVoucherSessionMapper saleOrderVoucherSessionMapper;
    public SaleOrderVoucherSessionDto getSaleOrderVoucherShop(Long id) {
        return saleOrderVoucherSessionRepository.findById(id).map(saleOrderVoucherSessionMapper::toDto).orElse(null);
    }

    public SaleOrderVoucherSessionDto create(Identity identity, SaleOrderVoucherSessionDto saleOrderVoucherSessionDto) {
        IdentityUtil.requireAuthenticated(identity);
        IdentityUtil.requireHasAnyRole(identity, "ADMIN", "USER", "SYSTEM");

        SaleOrderVoucherSession newSaleOrderVoucherSession = saleOrderVoucherSessionMapper.toEntity(saleOrderVoucherSessionDto);

        SaleOrderVoucherSession createdSaleOrderVoucherSession = saleOrderVoucherSessionRepository.save(newSaleOrderVoucherSession);

        return saleOrderVoucherSessionMapper.toDto(createdSaleOrderVoucherSession);

    }

    public SaleOrderVoucherSessionDto getSaleOrderVoucherSessionBySaleOrder (Long id) {
        Optional<SaleOrderVoucherSession> saleOrderVoucherSession = saleOrderVoucherSessionRepository.findBySaleOrderId(id);
        return saleOrderVoucherSession.map(saleOrderVoucherSessionMapper::toDto).orElse(null);
    }



}
