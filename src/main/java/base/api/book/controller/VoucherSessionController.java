package base.api.book.controller;

import base.api.book.dto.ErrorResponse;
import base.api.book.dto.VoucherSessionDto;
import base.api.book.service.VoucherSessionService;
import base.api.common.exception.DuplicateVoucherCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8082", "https://the-flying-bookstore.vercel.app", "https://the-flying-bookstore-dashboard-fe.vercel.app"})
@RequestMapping("/api/voucher-session")
public class VoucherSessionController {
    private final VoucherSessionService voucherSessionService;

    public VoucherSessionController(VoucherSessionService voucherSessionService){
        this.voucherSessionService = voucherSessionService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherSessionDto>> getAllVoucher(){
        return ResponseEntity.ok(voucherSessionService.getAllVouchers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherSessionDto> getVoucherById(@PathVariable Long id){
        VoucherSessionDto voucherSessionDto = voucherSessionService.getVoucherById(id);
        if(voucherSessionDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(voucherSessionDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<VoucherSessionDto>> getVoucherByName(@RequestParam String name) {
        List<VoucherSessionDto> voucherSessionDto = voucherSessionService.getVouchersByName(name);
        if (voucherSessionDto == null || voucherSessionDto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(voucherSessionDto);
    }


    @PostMapping
    public ResponseEntity<?> createVoucher(@RequestBody VoucherSessionDto voucherSessionDto) {
        try {
            VoucherSessionDto createdVoucher = voucherSessionService.createVoucher(voucherSessionDto);
            return ResponseEntity.ok(createdVoucher);
        } catch (DuplicateVoucherCodeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("Voucher code " + voucherSessionDto.code() + " already exists."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred while creating the voucher."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoucherSessionDto> updateVoucher(@PathVariable Long id, @RequestBody VoucherSessionDto voucherSessionDto) {
        return ResponseEntity.ok(voucherSessionService.updateVoucher(id, voucherSessionDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Long id) {
        voucherSessionService.deleteVoucherById(id);
        return ResponseEntity.noContent().build();
    }

}
