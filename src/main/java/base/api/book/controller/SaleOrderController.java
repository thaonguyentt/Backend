package base.api.book.controller;

import base.api.book.dto.BookDto;
import base.api.book.dto.SaleOrderDto;
import base.api.book.entity.SaleOrder;
import base.api.book.service.SaleOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
@RequestMapping("/api/SaleOrder")
public class SaleOrderController {
    private final SaleOrderService saleOrderService;

    public SaleOrderController(SaleOrderService saleOrderService) {
        this.saleOrderService = saleOrderService;
    }

    @GetMapping ("/{id}")
    public ResponseEntity<SaleOrderDto> getSaleOrderById (@PathVariable Long id) {
        SaleOrderDto saleOrderDto = saleOrderService.getSaleOrderById(id);
        if (saleOrderDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderDto);
    }

}
