package base.api.book.service;


import base.api.book.dto.ListingDto;
import base.api.book.entity.Listing;
import base.api.book.mapper.ListingMapper;
import base.api.book.repository.ListingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ListingService {
    private final ListingMapper listingMapper;
    private final ListingRepository listingRepository;

    public ListingService(ListingMapper listingMapper, ListingRepository listingRepository) {
        this.listingMapper = listingMapper;
        this.listingRepository = listingRepository;
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

    public List<ListingDto> getListingByOwnerId (Long id) {
        List<Listing> listing = listingRepository.findByOwnerId(id);
        return listing
                .stream()
                .map(listingMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ListingDto> getAllListing () {
        return listingRepository
                .findAll()
                .stream()
                .map(listingMapper::toDto)
                .collect(Collectors.toList());
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
