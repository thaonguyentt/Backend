package base.api.book.controller;


import base.api.book.dto.VoucherShopDto;
import base.api.book.service.VoucherShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8082", "https://the-flying-bookstore.vercel.app", "https://the-flying-bookstore-dashboard-fe.vercel.app"})
@RequestMapping("/api/voucher-shop")
public class VoucherShopController {

    private final VoucherShopService voucherShopService;

    public VoucherShopController(VoucherShopService voucherShopService) {
        this.voucherShopService = voucherShopService;
    }


    @GetMapping
    public ResponseEntity<List<VoucherShopDto>> getAllVouchers() {
        return ResponseEntity.ok(voucherShopService.getAllVouchers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherShopDto> getVoucherById(@PathVariable Long id) {
        VoucherShopDto voucherShopDto = voucherShopService.getVoucherById(id);
        if (voucherShopDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(voucherShopDto);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<VoucherShopDto>> getVoucherByName(@PathVariable String name) {
        List<VoucherShopDto> voucherShopDtos = voucherShopService.getVouchersByName(name);
        if (voucherShopDtos == null || voucherShopDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(voucherShopDtos);
    }

    @PostMapping
    public ResponseEntity<VoucherShopDto> createVoucher(@RequestBody VoucherShopDto voucherShopDto) {
        return ResponseEntity.ok(voucherShopService.createVoucher(voucherShopDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoucherShopDto> updateVoucher(@PathVariable Long id, @RequestBody VoucherShopDto voucherShopDto) {
        return ResponseEntity.ok(voucherShopService.updateVoucher(id, voucherShopDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Long id) {
        voucherShopService.deleteVoucherById(id);
        return ResponseEntity.noContent().build();
    }


}
