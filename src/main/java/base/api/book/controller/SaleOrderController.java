package base.api.book.controller;


import base.api.book.dto.*;
import base.api.book.entity.SaleOrderVoucherShop;
import base.api.book.entity.support.SellOrderStatus;
import base.api.book.mapper.SaleOrderVoucherShopMapper;
import base.api.book.service.*;
import base.api.system.security.Identity;
import base.api.system.security.IdentityUtil;
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
    @Autowired
    SaleOrderVoucherShopService saleOrderVoucherShopService;

    @Autowired
    SaleOrderVoucherSessionService saleOrderVoucherSessionService;
    @Autowired
    SaleOrderVoucherShopMapper saleOrderVoucherShopMapper;
//    public SaleOrderController(SaleOrderService saleOrderService, SaleOrderDetailService saleOrderDetailService) {
//        this.saleOrderService = saleOrderService;
//        this.saleOrderDetailService = saleOrderDetailService;
//    }

    @GetMapping ("")
    public ResponseEntity<List<SaleOrderDetailManagementDto>> getAllSaleOrder () {
        List<SaleOrderDto> saleOrderDto = saleOrderService.getAllSaleOrder();
        if (saleOrderDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderDto.stream().map(dto->{
            UserDto buyer = userService.getUserById(dto.buyerId());
            UserDto seller = userService.getUserById(dto.sellerId());
            ListingDto listing = listingService.getListingById(dto.listingId());
            SaleOrderVoucherShopDto voucherShop = saleOrderVoucherShopService.getSaleOrderVoucherShop(dto.id());
            SaleOrderVoucherSessionDto voucherSession = saleOrderVoucherSessionService.getSaleOrderVoucherSessionBySaleOrder(dto.id());
            BigDecimal finalPrice = dto.totalPrice();
            return new SaleOrderDetailManagementDto(dto, listing, seller, buyer, voucherShop, voucherSession, finalPrice);
        }).collect(Collectors.toList()));
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
        if (saleOrderDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderDto.stream().map(dto -> {
            UserDto buyer = userService.getUserById(dto.buyerId());
            UserDto seller = userService.getUserById(dto.sellerId());
            ListingDto listing = listingService.getListingById(dto.listingId());
            SaleOrderVoucherShopDto voucherShop = saleOrderVoucherShopService.getSaleOrderVoucherShop(dto.id());
            SaleOrderVoucherSessionDto voucherSession = saleOrderVoucherSessionService.getSaleOrderVoucherSessionBySaleOrder(dto.id());
            BigDecimal finalPrice = dto.totalPrice();
            return new SaleOrderDetailManagementDto (dto,listing,seller, buyer,voucherShop, voucherSession,finalPrice);
        }).collect(Collectors.toList()));
//        return ResponseEntity.ok(saleOrderDto);
    }

    @GetMapping ("/buyer/{id}")
    public ResponseEntity<List<SaleOrderDetailManagementDto>> getSaleOrderByBuyerId (@PathVariable Long id) {
        List<SaleOrderDto> saleOrderDto = saleOrderService.getSaleOrderByBuyerId(id);
        if (saleOrderDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderDto.stream().map(dto -> {
            UserDto buyer = userService.getUserById(dto.buyerId());
            UserDto seller = userService.getUserById(dto.sellerId());
            ListingDto listing = listingService.getListingById(dto.listingId());
            SaleOrderVoucherShopDto voucherShop = saleOrderVoucherShopService.getSaleOrderVoucherShop(dto.id());
            SaleOrderVoucherSessionDto voucherSession = saleOrderVoucherSessionService.getSaleOrderVoucherSessionBySaleOrder(dto.id());
            BigDecimal finalPrice = dto.totalPrice();
            return new SaleOrderDetailManagementDto (dto,listing,seller, buyer,voucherShop, voucherSession,finalPrice);
        }).collect(Collectors.toList()));
//        return ResponseEntity.ok(saleOrderDto);
    }

    @GetMapping("/search/BuyerAndStatus")
    public ResponseEntity<List<SaleOrderDetailManagementDto>> getSaleOrderBySBuyerAndStatus (@RequestParam(name="id") Long id, @RequestParam(name="status") SellOrderStatus status) {
        List<SaleOrderDto> saleOrderDto = saleOrderService.getSaleOrderByBuyerAndStatus(id, status);
        if (saleOrderDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderDto.stream().map(dto -> {
            UserDto buyer = userService.getUserById(dto.buyerId());
            UserDto seller = userService.getUserById(dto.sellerId());
            ListingDto listing = listingService.getListingById(dto.listingId());
            SaleOrderVoucherShopDto voucherShop = saleOrderVoucherShopService.getSaleOrderVoucherShop(dto.id());
            SaleOrderVoucherSessionDto voucherSession = saleOrderVoucherSessionService.getSaleOrderVoucherSessionBySaleOrder(dto.id());
            BigDecimal finalPrice = dto.totalPrice();
            return new SaleOrderDetailManagementDto (dto,listing,seller, buyer,voucherShop, voucherSession,finalPrice);
        }).collect(Collectors.toList()));
//        return ResponseEntity.ok(saleOrderDto);
    }

    @GetMapping("/search/SellerAndStatus")
    public ResponseEntity<List<SaleOrderDetailManagementDto>> getSaleOrderBySellerAndStatus (@RequestParam(name="id") Long id, @RequestParam(name="status") SellOrderStatus status) {
        List<SaleOrderDto> saleOrderDto = saleOrderService.getSaleOrderBySellerAndStatus(id, status);
        if (saleOrderDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderDto.stream().map(dto -> {
            UserDto buyer = userService.getUserById(dto.buyerId());
            UserDto seller = userService.getUserById(dto.sellerId());
            ListingDto listing = listingService.getListingById(dto.listingId());
            SaleOrderVoucherShopDto voucherShop = saleOrderVoucherShopService.getSaleOrderVoucherShop(dto.id());
            SaleOrderVoucherSessionDto voucherSession = saleOrderVoucherSessionService.getSaleOrderVoucherSessionBySaleOrder(dto.id());
            BigDecimal finalPrice = dto.totalPrice();
            return new SaleOrderDetailManagementDto (dto,listing,seller, buyer,voucherShop, voucherSession,finalPrice);
        }).collect(Collectors.toList()));
//        return ResponseEntity.ok(saleOrderDto);
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

    @GetMapping("/saleOrderVCShop/{id}")
    public ResponseEntity<SaleOrderVoucherShopDto> getSaleOrderVoucherShopById (@PathVariable Long id) {
        SaleOrderVoucherShopDto saleOrderVoucherShopDto = saleOrderVoucherShopService.getSaleOrderVoucherShop(id);
        if (saleOrderVoucherShopDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderVoucherShopDto);
    }

    @GetMapping("/saleOrderVCSession/{id}")
    public ResponseEntity<SaleOrderVoucherSessionDto> getSaleOrderVoucherSessionById (@PathVariable Long id) {
        SaleOrderVoucherSessionDto saleOrderVoucherSessionDto = saleOrderVoucherSessionService.getSaleOrderVoucherShop(id);
        if (saleOrderVoucherSessionDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(saleOrderVoucherSessionDto);
    }





    @PostMapping ("/createSaleOrder")
    public ResponseEntity<SaleOrderDetailManagementDto> createLeaseOrder (@RequestBody SaleOrderCreateRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SaleOrderDto saleOrderDto = saleOrderService.createSaleOrder(auth,request);
        UserDto buyer = userService.getUserById(saleOrderDto.buyerId());
        UserDto seller = userService.getUserById(saleOrderDto.sellerId());
        ListingDto listing = listingService.getListingById(saleOrderDto.listingId());
        SaleOrderVoucherShopDto voucherShop = saleOrderVoucherShopService.getSaleOrderVoucherShop(saleOrderDto.id());
        SaleOrderVoucherSessionDto voucherSession = saleOrderVoucherSessionService.getSaleOrderVoucherSessionBySaleOrder(saleOrderDto.id());
        BigDecimal finalPrice = saleOrderDto.totalPrice();

        return ResponseEntity.ok(new SaleOrderDetailManagementDto (saleOrderDto,listing,seller, buyer,voucherShop, voucherSession,finalPrice));
    }

    @PostMapping ("/createSaleOrderFromLease")
    public SaleOrderDto createLeaseOrderFromLease (@RequestBody SaleOrderCreateRequestFromLease request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return saleOrderService.createSaleOrderFromLease(auth,request);
    }

    @PostMapping ("/createVoucherShopMap")
    public SaleOrderVoucherShop create (@RequestBody SaleOrderVoucherShopDto request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Identity identity = IdentityUtil.fromSpringAuthentication(auth);
        SaleOrderVoucherShopDto t = saleOrderVoucherShopService.create(identity,request);
        return saleOrderVoucherShopMapper.toEntity(t);
    }









}
