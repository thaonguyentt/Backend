package base.api.book.service;

import base.api.book.dto.VoucherShopDto;
import base.api.book.entity.VoucherShop;
import base.api.book.mapper.VoucherShopMapper;
import base.api.book.repository.VoucherShopRepository;
import base.api.common.exception.DuplicateVoucherCodeException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VoucherShopService {
    private final VoucherShopRepository voucherShopRepository;
    private final VoucherShopMapper voucherShopMapper;

    public VoucherShopService(VoucherShopRepository voucherShopRepository, VoucherShopMapper voucherShopMapper) {
        this.voucherShopRepository = voucherShopRepository;
        this.voucherShopMapper = voucherShopMapper;
    }

    public VoucherShopDto createVoucher(VoucherShopDto voucherShopDto) {
        // Kiểm tra nếu mã code đã tồn tại trong cơ sở dữ liệu
        if (voucherShopRepository.existsByCode(voucherShopDto.code())) {
            throw new DuplicateVoucherCodeException("Voucher code " + voucherShopDto.code() + " already exists.");
        }

        VoucherShop voucher = voucherShopMapper.toEntity(voucherShopDto);
        VoucherShop createdVoucher = voucherShopRepository.save(voucher);
        return voucherShopMapper.toDto(createdVoucher);
    }


    public VoucherShopDto getVoucherById(Long id) {
        Optional<VoucherShop> optionalVoucher = voucherShopRepository.findById(id);
        return optionalVoucher.map(voucherShopMapper::toDto).orElse(null);
    }

    public List<VoucherShopDto> getAllVouchers() {
        return voucherShopRepository.findAll()
                .stream()
                .map(voucherShopMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<VoucherShopDto> getVouchersByName(String name) {
        List<VoucherShop> vouchers = voucherShopRepository.findByNameContaining(name);
        return vouchers
                .stream()
                .map(voucherShopMapper::toDto)
                .collect(Collectors.toList());
    }

    public VoucherShopDto updateVoucher(Long id, VoucherShopDto newVoucherDto) {
        VoucherShop updatedVoucher = voucherShopMapper.toEntity(newVoucherDto);
        updatedVoucher.setId(id);
        VoucherShop savedVoucher = voucherShopRepository.save(updatedVoucher);
        return voucherShopMapper.toDto(savedVoucher);
    }

    public void deleteVoucherById(Long id) {
        voucherShopRepository.deleteById(id);
    }
}
