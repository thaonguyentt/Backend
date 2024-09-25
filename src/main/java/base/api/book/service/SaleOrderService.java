package base.api.book.service;

import base.api.book.dto.SaleOrderCreateRequest;
import base.api.book.dto.SaleOrderDetailDto;
import base.api.book.dto.SaleOrderDto;
import base.api.book.entity.Listing;
import base.api.book.entity.SaleOrder;
import base.api.book.entity.support.ListingStatus;
import base.api.book.exception.ListingNotAvailableException;
import base.api.book.exception.NoSuchListingException;
import base.api.book.mapper.SaleOrderDetailMapper;
import base.api.book.mapper.SaleOrderMapper;
import base.api.book.repository.ListingRepository;
import base.api.book.repository.SaleOrderDetailRepository;
import base.api.book.repository.SaleOrderRepository;
import base.api.system.security.Identity;
import base.api.system.security.IdentityUtil;
import base.api.system.security.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class SaleOrderService {

    private final SaleOrderRepository saleOrderRepository;

    private final SaleOrderMapper saleOrderMapper;

    private final ListingRepository listingRepository;


    public SaleOrderDto getSaleOrderById (Long id) {
        return saleOrderRepository.findById(id).map(saleOrderMapper::toDto).orElse(null);
    }

    public SaleOrderDto createSaleOrder (Authentication auth, SaleOrderCreateRequest requestDto) {
        SecurityUtils.requireAuthentication(auth);
        if (auth == null || !auth.isAuthenticated()) {
            auth = SecurityContextHolder.getContext().getAuthentication();
        }
        Identity identity = IdentityUtil.fromSpringAuthentication(auth);

        Long userId = Long.valueOf((String)auth.getPrincipal());
        Listing listing = listingRepository.findById(requestDto.listingId()).orElseThrow(() -> new NoSuchListingException("No such listing exists."));
        if (!ListingStatus.AVAILABLE.equals(listing.getListingStatus()) || listing.getAllow_purchase().equals(0L)) {
            throw new ListingNotAvailableException("Listing " + requestDto.listingId() + " is not available.");
        }


        return null;

    }
}
