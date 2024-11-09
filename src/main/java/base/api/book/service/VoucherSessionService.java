package base.api.book.service;

import base.api.book.dto.VoucherSessionDto;
import base.api.book.entity.VoucherSession;
import base.api.book.mapper.VoucherSessionMapper;
import base.api.book.repository.VoucherSessionRepository;
import base.api.common.exception.DuplicateVoucherCodeException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VoucherSessionService {
    private final VoucherSessionRepository voucherSessionRepository;
    private final VoucherSessionMapper voucherSessionMapper;

    public VoucherSessionService (VoucherSessionRepository voucherSessionRepository, VoucherSessionMapper voucherSessionMapper){
        this.voucherSessionRepository = voucherSessionRepository;
        this.voucherSessionMapper = voucherSessionMapper;
    }


    public VoucherSessionDto getVoucherById(Long id) {
        Optional<VoucherSession> optionalVoucher = voucherSessionRepository.findById(id);
        return optionalVoucher.map(voucherSessionMapper::toDto).orElse(null);
    }

    public List<VoucherSessionDto> getAllVouchers() {
        return voucherSessionRepository.findAll()
                .stream()
                .map(voucherSessionMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<VoucherSessionDto> getVouchersByKeyword(String keyword) {
        List<VoucherSession> vouchers = voucherSessionRepository.findByNameContainingOrCodeContaining(keyword, keyword);
        return vouchers
                .stream()
                .map(voucherSessionMapper::toDto)
                .collect(Collectors.toList());
    }

    public VoucherSessionDto updateVoucher(Long id, VoucherSessionDto newVoucherDto) {
        VoucherSession updatedVoucher = voucherSessionMapper.toEntity(newVoucherDto);
        updatedVoucher.setId(id);
        VoucherSession savedVoucher = voucherSessionRepository.save(updatedVoucher);
        return voucherSessionMapper.toDto(savedVoucher);
    }

    public void deleteVoucherById(Long id) {
        voucherSessionRepository.deleteById(id);
    }

    public VoucherSessionDto createVoucher(VoucherSessionDto voucherSessionDto) {
        if(voucherSessionRepository.existsByCode(voucherSessionDto.code())){
            throw new DuplicateVoucherCodeException("Voucher code "+ voucherSessionDto.code() + " already exists.");
        }

        VoucherSession voucher = voucherSessionMapper.toEntity(voucherSessionDto);
        VoucherSession createVoucher = voucherSessionRepository.save(voucher);
        return voucherSessionMapper.toDto(createVoucher);
    }
}
