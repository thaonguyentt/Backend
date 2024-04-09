package base.api.book.controller;

import base.api.book.dto.BookDto;
import base.api.book.dto.CopyDto;
import base.api.book.dto.ListingDto;
import base.api.book.dto.ListingExtendedDto;
import base.api.book.dto.search.ListingSearchDto;
import base.api.book.service.BookService;
import base.api.book.service.CopyService;
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
    private final CopyService copyService;
    private final BookService bookService;

    public ListingController(ListingService listingService, CopyService copyService, BookService bookService) {
        this.listingService = listingService;
        this.copyService = copyService;
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingDto> getListingById(@PathVariable Long id) {
        ListingDto listingDto = listingService.getListingById(id);
        if (listingDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listingDto);
    }

    @GetMapping
    public ResponseEntity<List<ListingDto>> getAllListing() {
        return ResponseEntity.ok(listingService.getAllListing());
    }

    @GetMapping("/search")
    public Page<ListingExtendedDto> test(
      Pageable pageable,
      @RequestParam(name = "title", required = false) String title,
      @RequestParam(name = "genre", required = false) String genre) {
        ListingSearchDto listingSearchDto = new ListingSearchDto();
        listingSearchDto.setTitle(title);
        listingSearchDto.setGenre(genre);
        return listingService.findListings(pageable, listingSearchDto)
          .map(dto -> {
              CopyDto copyDto = copyService.getCopyById(dto.copyId());
              BookDto bookDto = bookService.getBookById(copyDto.bookId());
              return new ListingExtendedDto(
                dto.id(),
                dto.ownerId(),
                dto.quantity(),
                dto.address(),
                dto.expiryDate(),
                dto.leaseRate(),
                dto.depositFee(),
                dto.penaltyRate(),
                dto.description(),
                copyDto,
                bookDto
                );
          });
    }

    @GetMapping("/search/{ownerId}")
    public ResponseEntity<List<ListingExtendedDto>> getListingByOwnerId(@PathVariable Long ownerId) {
        List<ListingExtendedDto> listListing = listingService.getListingByOwnerId(ownerId);
        if (listListing.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listListing);
    }

    @PostMapping
    public ResponseEntity<ListingDto> createListing(@RequestBody ListingDto listingDto) {
        return ResponseEntity.ok(listingService.createListing(listingDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListingDto> updateListing(@PathVariable Long id, @RequestBody ListingDto listingDto) {
        return ResponseEntity.ok(listingService.updateListing(listingDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        listingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }


}
