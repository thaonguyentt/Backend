package base.api.book.controller;


import base.api.book.dto.*;
import base.api.book.entity.SaleOrder;
import base.api.book.entity.support.LeaseOrderStatus;
import base.api.book.entity.support.SellOrderStatus;
import base.api.book.service.ListingService;
import base.api.book.service.SaleOrderDetailService;
import base.api.book.service.SaleOrderService;
import base.api.system.security.SecurityUtils;
import base.api.user.UserDto;
import base.api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8082", "https://the-flying-bookstore.vercel.app","https://the-flying-bookstore-dashboard-fe.vercel.app"})
@RequestMapping("/api/SaleOrder")
public class SaleOrderController {
    @Autowired
    SaleOrderService saleOrderService;
    @Autowired
    SaleOrderDetailService saleOrderDetailService;
    @Autowired
    UserService userService;
    @Autowired
    ListingService listingService;


//    public SaleOrderController(SaleOrderService saleOrderService, SaleOrderDetailService saleOrderDetailService) {
//        this.saleOrderService = saleOrderService;
//        this.saleOrderDetailService = saleOrderDetailService;
//    }

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
    public ResponseEntity<List<SaleOrderDetailManagementDto>> getSaleOrderBySellerId (@PathVariable Long id) {
        List<SaleOrderDto> saleOrderDto = saleOrderService.getSaleOrderBySellerId(id);
        return ResponseEntity.ok(saleOrderDto.stream().map(dto -> {
            UserDto buyer = userService.getUserById(dto.buyerId());
            UserDto seller = userService.getUserById(dto.sellerId());
            ListingDto listing = listingService.getListingById(dto.listingId());
            BigDecimal finalPrice = dto.totalPrice();
            return new SaleOrderDetailManagementDto (dto,listing,seller, buyer,finalPrice);
        }).collect(Collectors.toList()));
    }

    @GetMapping ("/buyer/{id}")
    public ResponseEntity<List<SaleOrderDto>> getSaleOrderByBuyerId (@PathVariable Long id) {
        List<SaleOrderDto> saleOrderDto = saleOrderService.getSaleOrderByBuyerId(id);
        if (saleOrderDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderDto);
    }

    @GetMapping("/search/BuyerAndStatus")
    public ResponseEntity<List<SaleOrderDto>> getSaleOrderBySBuyerAndStatus (@RequestParam(name="id") Long id, @RequestParam(name="status") SellOrderStatus status) {
        List<SaleOrderDto> saleOrderDto = saleOrderService.getSaleOrderByBuyerAndStatus(id, status);
        if (saleOrderDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderDto);
    }

    @GetMapping("/search/SellerAndStatus")
    public ResponseEntity<List<SaleOrderDto>> getSaleOrderBySellerAndStatus (@RequestParam(name="id") Long id, @RequestParam(name="status") SellOrderStatus status) {
        List<SaleOrderDto> saleOrderDto = saleOrderService.getSaleOrderBySellerAndStatus(id, status);
        if (saleOrderDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderDto);
    }

    @GetMapping ("/status")
    public ResponseEntity<SaleOrderDto> updateStatus (@RequestParam(name="id") Long id, @RequestParam(name="status") SellOrderStatus status) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityUtils.requireAuthentication(auth);
        return ResponseEntity.ok(saleOrderService.updateSaleOrderStatus(auth, id, status));
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
