package base.api.book.service;

import base.api.book.dto.SaleOrderDetailDto;
import base.api.book.mapper.SaleOrderDetailMapper;
import base.api.book.repository.SaleOrderDetailRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class SaleOrderDetailService {

    private final SaleOrderDetailRepository saleOrderDetailRepository;

    private final SaleOrderDetailMapper saleOrderDetailMapper;

    public SaleOrderDetailDto getSaleOrderDetail(Long id) {
        return saleOrderDetailRepository.findById(id).map(saleOrderDetailMapper::toDto).orElse(null);
    }


}
