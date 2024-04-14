package base.api.book.controller;

import base.api.book.dto.*;
import base.api.book.dto.search.ListingSearchDto;
import base.api.book.service.BookService;
import base.api.book.service.CopyService;
import base.api.book.service.ListingService;
import base.api.user.UserService;
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

    private final UserService userService;

    public ListingController(ListingService listingService, CopyService copyService, BookService bookService, UserService userService) {
        this.listingService = listingService;
        this.copyService = copyService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingDto> getListingById(@PathVariable Long id) {
        ListingDto listingDto = listingService.getListingById(id);
        if (listingDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listingDto);
    }

//    @GetMapping("/detailListing/{id}")
//    public ResponseEntity<ListingDetailDto> getListingById(@PathVariable Long id) {
//        ListingDto listingDto = listingService.getListingById(id);
//        if (listingDto == null) {
//            return ResponseEntity.noContent().build();
//        }
//        ListingDetailDto listingDetailDto = new ListingDetailDto(
//                listingDto.id(),
//                userService.getUserById(listingDto.ownerId()),
////                listing.getQuantity(),
////                listing.getAddress(),
////                listing.getLeaseRate(),
////                listing.getDepositFee(),
////                listing.getPenaltyRate(),
////                listing.getDescription(),
////                listing.getCopy(),
////                listing.getCopy().getBook(),
////                reviewService.getReviewByOwnerId(listing.getOwner().getId())
//        );
//        return ResponseEntity.ok(listingDto);
//    }

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

    @GetMapping ("/search/byOwnerId/{ownerId}")
    public Page<ListingExtendedDto> getListingByOwnerId (Pageable pageable, @PathVariable Long ownerId) {
        Page<ListingDto> listingDto = listingService.getListingByOwnerId(pageable,ownerId);
        return listingDto.map(dto->{
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
