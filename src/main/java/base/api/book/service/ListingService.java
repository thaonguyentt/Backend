package base.api.book.service;


import base.api.book.dto.ListingDto;
import base.api.book.dto.ListingExtendedDto;
import base.api.book.dto.search.ListingSearchDto;
import base.api.book.entity.Book;
import base.api.book.entity.Copy;
import base.api.book.entity.Listing;
import base.api.book.mapper.BookMapper;
import base.api.book.mapper.CopyMapper;
import base.api.book.mapper.ListingMapper;
import base.api.book.repository.BookRepository;
import base.api.book.repository.CopyRepository;
import base.api.book.repository.ListingRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ListingService {
    private final ListingMapper listingMapper;
    private final ListingRepository listingRepository;

    private final BookMapper bookMapper;

    private final CopyMapper copyMapper;


    public ListingService(ListingMapper listingMapper, ListingRepository listingRepository, BookMapper bookMapper, CopyMapper copyMapper) {
        this.listingMapper = listingMapper;
        this.listingRepository = listingRepository;
        this.bookMapper = bookMapper;
        this.copyMapper = copyMapper;
    }

    public ListingDto createListing (ListingDto listingDto) {
        Listing listing = listingMapper.toEntity(listingDto);
        Listing createdListing = listingRepository.save(listing);
        return listingMapper.toDto(createdListing);
    }

    public ListingDto getListingById (Long id) {
        Optional<Listing> optionalListing = listingRepository.findById(id);
        return optionalListing.map(listingMapper::toDto).orElse(null);
    }

    public List<ListingExtendedDto> getListingByOwnerId (Long id) {
        List<ListingExtendedDto> listingExtendedDtoList = new ArrayList<>();
        List<Listing> listListing = listingRepository.findByOwnerId(id);
        for (Listing listing : listListing) {
            ListingExtendedDto listingExtendedDto = new ListingExtendedDto(listing.getId(),listing.getOwner().getId(),listing.getQuantity().intValue(),
                    listing.getAddress(),listing.getExpiryDate(),listing.getLeaseRate(),listing.getDepositFee(),listing.getPenaltyRate(),
                    listing.getDescription(),copyMapper.toDto(listing.getCopy()),bookMapper.toDto(listing.getCopy().getBook()));
            listingExtendedDtoList.add(listingExtendedDto);
        }
        return listingExtendedDtoList;
    }

    public List<ListingDto> getAllListing () {
        return listingRepository
                .findAll()
                .stream()
                .map(listingMapper::toDto)
                .toList();
    }

    public Page<ListingDto> findListings(Pageable pageable, ListingSearchDto searchDto) {
        Page<Listing> result;
        if (StringUtils.isNoneBlank(searchDto.getTitle(), searchDto.getGenre())) {
            result = listingRepository.findByTitleLikeAndGenre(pageable, searchDto.getTitle(), searchDto.getGenre());
        } else if (StringUtils.isNoneBlank(searchDto.getTitle()) && StringUtils.isBlank(searchDto.getGenre())) {
            result = listingRepository.findByTitle(pageable, searchDto.getTitle());
        } else if (StringUtils.isBlank(searchDto.getTitle()) && StringUtils.isNotBlank(searchDto.getGenre())) {
            result = listingRepository.findByGenre(pageable, searchDto.getGenre());
        } else {
            result = listingRepository.findAll(pageable);
        }
        return result.map(listingMapper::toDto);
    }

    public ListingDto updateListing (ListingDto listingDto) {
        Long id = listingDto.id();
        Listing newListing = listingMapper.toEntity(listingDto);
        newListing.setId(id);
        Listing createdListing = listingRepository.save(newListing);
        return listingMapper.toDto(createdListing);
    }

    public void deleteListing (Long id) {
        listingRepository.deleteById(id);
    }
}
