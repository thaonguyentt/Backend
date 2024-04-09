package base.api.book.controller;

import base.api.book.dto.ListingDto;
import base.api.book.dto.search.ListingSearchDto;
import base.api.book.service.ListingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
@RequestMapping("/api/listing")
public class ListingController {
    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingDto> getListingById (@PathVariable Long id) {
        ListingDto listingDto = listingService.getListingById(id);
        if (listingDto == null) {return ResponseEntity.noContent().build();}
        return ResponseEntity.ok(listingDto);
    }

    @GetMapping
    public ResponseEntity<List<ListingDto>> getAllListing () {
        return ResponseEntity.ok(listingService.getAllListing());
    }

    @GetMapping("/search")
    public Page<ListingDto> test(Pageable pageable, ListingSearchDto searchDto) {
        return listingService.findListings(pageable, searchDto);
    }

    @GetMapping ("/search/{ownerId}")
    public ResponseEntity<List<ListingDto>> getListingByOwnerId (@PathVariable Long ownerId) {
        List<ListingDto> listListing = listingService.getListingByOwnerId(ownerId);
        if (listListing.isEmpty()) {return ResponseEntity.noContent().build();}
        return ResponseEntity.ok(listListing);
    }

    @PostMapping
    public ResponseEntity<ListingDto> createListing (@RequestBody ListingDto listingDto) {
        return ResponseEntity.ok(listingService.createListing(listingDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListingDto> updateListing (@PathVariable Long id, @RequestBody ListingDto listingDto) {
        return ResponseEntity.ok(listingService.updateListing(listingDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing (@PathVariable Long id) {
        listingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }



}
