package base.api.book.controller;


import base.api.book.dto.SaleOrderCreateRequest;
import base.api.book.dto.SaleOrderCreateRequestFromLease;
import base.api.book.dto.SaleOrderDetailDto;
import base.api.book.dto.SaleOrderDto;
import base.api.book.entity.SaleOrder;
import base.api.book.service.SaleOrderDetailService;
import base.api.book.service.SaleOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8082", "https://the-flying-bookstore.vercel.app","https://the-flying-bookstore-dashboard-fe.vercel.app"})
@RequestMapping("/api/SaleOrder")
public class SaleOrderController {
    private final SaleOrderService saleOrderService;

    private final SaleOrderDetailService saleOrderDetailService;


    public SaleOrderController(SaleOrderService saleOrderService, SaleOrderDetailService saleOrderDetailService) {
        this.saleOrderService = saleOrderService;
        this.saleOrderDetailService = saleOrderDetailService;
    }

    @GetMapping ("")
    public ResponseEntity<List<SaleOrderDto>> getAllSaleOrder () {
        List<SaleOrderDto> saleOrderDto = saleOrderService.getAllSaleOrder();
        if (saleOrderDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderDto);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<SaleOrderDto> getSaleOrderById (@PathVariable Long id) {
        SaleOrderDto saleOrderDto = saleOrderService.getSaleOrderById(id);
        if (saleOrderDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderDto);
    }

    @GetMapping ("/seller/{id}")
    public ResponseEntity<List<SaleOrderDto>> getSaleOrderBySellerId (@PathVariable Long id) {
        List<SaleOrderDto> saleOrderDto = saleOrderService.getSaleOrderBySellerId(id);
        if (saleOrderDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderDto);
    }

    @GetMapping ("/buyer/{id}")
    public ResponseEntity<List<SaleOrderDto>> getSaleOrderByBuyerId (@PathVariable Long id) {
        List<SaleOrderDto> saleOrderDto = saleOrderService.getSaleOrderByBuyerId(id);
        if (saleOrderDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderDto);
    }

    @GetMapping("/saleOrderDT/{id}")
    public ResponseEntity<SaleOrderDetailDto> getSaleOrderDetailById (@PathVariable Long id) {
        SaleOrderDetailDto saleOrderDetailDto = saleOrderDetailService.getSaleOrderDetail(id);
        if (saleOrderDetailDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderDetailDto);
    }



    @PostMapping ("/createSaleOrder")
    public SaleOrderDto createLeaseOrder (@RequestBody SaleOrderCreateRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return saleOrderService.createSaleOrder(auth,request);
    }

    @PostMapping ("/createSaleOrderFromLease")
    public SaleOrderDto createLeaseOrderFromLease (@RequestBody SaleOrderCreateRequestFromLease request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return saleOrderService.createSaleOrderFromLease(auth,request);
    }









}
