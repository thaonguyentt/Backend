package base.api.book.service;

import base.api.book.dto.SaleOrderDto;
import base.api.book.entity.SaleOrder;
import base.api.book.mapper.SaleOrderMapper;
import base.api.book.repository.SaleOrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class SaleOrderService {

    private final SaleOrderRepository saleOrderRepository;

    private final SaleOrderMapper saleOrderMapper;


    public SaleOrderDto getSaleOrderById (Long id) {
        return saleOrderRepository.findById(id).map(saleOrderMapper::toDto).orElse(null);
    }
}
