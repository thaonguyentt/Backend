package base.api.book.service;

import base.api.authorization.UnauthorizedException;
import base.api.book.dto.LeaseOrderDetailDto;
import base.api.book.dto.LeaseOrderDto;
import base.api.book.entity.Copy;
import base.api.book.entity.LeaseOrder;
import base.api.book.entity.LeaseOrderDetail;
import base.api.book.entity.Listing;
import base.api.book.entity.support.CopyStatus;
import base.api.book.entity.support.LeaseOrderStatus;
import base.api.book.entity.support.ListingStatus;
import base.api.book.exception.ListingNotAvailableException;
import base.api.book.mapper.LeaseOrderDetailMapper;
import base.api.book.mapper.LeaseOrderMapper;
import base.api.book.repository.CopyRepository;
import base.api.book.repository.LeaseOrderRepository;
import base.api.book.repository.ListingRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaseOrderService {

  final LeaseOrderRepository leaseOrderRepository;
  final ListingRepository listingRepository;

  final LeaseOrderDetailMapper leaseOrderDetailMapper;
  final LeaseOrderMapper leaseOrderMapper;
  private final CopyRepository copyRepository;

  public LeaseOrderService(LeaseOrderRepository leaseOrderRepository, ListingRepository listingRepository, LeaseOrderDetailMapper leaseOrderDetailMapper, LeaseOrderMapper leaseOrderMapper,
                           CopyRepository copyRepository) {
    this.leaseOrderRepository = leaseOrderRepository;
      this.listingRepository = listingRepository;
      this.leaseOrderDetailMapper = leaseOrderDetailMapper;
      this.leaseOrderMapper = leaseOrderMapper;
      this.copyRepository = copyRepository;
  }

  public LeaseOrderDto createLeaseOrder(Authentication auth, LeaseOrderDto leaseOrderDto) {
    if (auth == null) {
      throw new UnauthorizedException("Unauthorized access");
    }
    if (leaseOrderDto.id() != null) {
      throw new IllegalArgumentException("id should be blank");
    }
    Long userId = Long.valueOf((String)auth.getPrincipal());

    // TODO verify argument
    if (leaseOrderDto.leaseOrderDetails().isEmpty()) {
      throw new IllegalArgumentException("Lease order details mst not be empty");
    }
    // Kiểm tra trạng thái của listing có AVAILABLE hay không
    // FIXME: tạo data structure cho luồng fail-slow (Exception chứa list nhiều exception khác)
    List<LeaseOrderDetailDto> orderDetailDtoList = leaseOrderDto.leaseOrderDetails().stream().toList();
    for (LeaseOrderDetailDto orderDetailDto : orderDetailDtoList) {
      Long listingId = orderDetailDto.listingId();
      Listing listing = listingRepository.getReferenceById(listingId);
      if (! ListingStatus.AVAILABLE.equals(listing.getListingStatus())) {
        throw new ListingNotAvailableException("Listing " + listingId + " is not available.");
      }
    }
    // TODO check thêm business logic khác

    // Id người cho thuê. Lấy id của listing của detail đầu tiên
    AtomicReference<Long> lessorId = new AtomicReference<>(null);

    // đổi status của listing và copy
    leaseOrderDto.leaseOrderDetails().stream().forEach(
      leaseOrderDetailDto -> {
        Listing listing = listingRepository.getReferenceById(leaseOrderDetailDto.listingId());
        listing.setListingStatus(ListingStatus.LEASED);

        if (lessorId.get() == null) { lessorId.set(listing.getOwner().getId()); }

        Copy copy = listing.getCopy();
        copy.setCopyStatus(CopyStatus.LEASED);

        listingRepository.save(listing);
        copyRepository.save(copy);
      }
    );

    // Tạo LeaseOrder mới
    Set<LeaseOrderDetail> leaseOrderDetailList = leaseOrderDto.leaseOrderDetails().stream()
      .map(leaseOrderDetailMapper::toEntity).collect(Collectors.toSet());

    LeaseOrder newLeaseOrder = leaseOrderMapper.toEntity(leaseOrderDto);
    newLeaseOrder.setStatus(LeaseOrderStatus.ORDERED_PAYMENT_PENDING);
    newLeaseOrder.setLeaseOrderDetails(leaseOrderDetailList);
    newLeaseOrder.setLesseeId(userId);
    newLeaseOrder.setLessorId(lessorId.get());

    LeaseOrder createdOrder = leaseOrderRepository.save(newLeaseOrder);

    return leaseOrderMapper.toDto(createdOrder);
  }

  public List<LeaseOrderDto> getLeaseOrderByLessorId(Long id) {
    return leaseOrderRepository.findLeaseOrderByLesseeId(id)
            .stream()
            .map(leaseOrderMapper::toDto)
            .collect(Collectors.toList());
  }

}
