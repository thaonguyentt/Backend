package base.api.book.controller;

import base.api.book.dto.*;
import base.api.book.dto.search.ListingSearchByOwnerAndNameDto;
import base.api.book.dto.search.ListingSearchDto;
import base.api.book.service.*;
import base.api.user.UserDto;
import base.api.user.UserService;
import org.apache.commons.lang3.StringUtils;
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

    private final ReviewService reviewService;
    private final GenreService genreService;

    public ListingController(ListingService listingService,
                             CopyService copyService,
                             BookService bookService,
                             UserService userService,
                             ReviewService reviewService,
                             GenreService genreService
                             ) {
        this.listingService = listingService;
        this.copyService = copyService;
        this.bookService = bookService;
        this.userService = userService;
        this.reviewService = reviewService;
        this.genreService = genreService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingDto> getListingById(@PathVariable Long id) {
        ListingDto listingDto = listingService.getListingById(id);
        if (listingDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listingDto);
    }

    @GetMapping("/detailListing/{id}")
    public ResponseEntity<ListingDetailDto> getListingDetailById(@PathVariable Long id) {
        ListingDto listingDto = listingService.getListingById(id);
        if (listingDto == null) {
            return ResponseEntity.noContent().build();
        }
        CopyDto copy = copyService.getCopyById(listingDto.copyId());
        BookDto book = bookService.getBookById(copy.bookId());
        List<ReviewDto> reviews = reviewService.getReviewByOwnerId(listingDto.ownerId());
        Long bookOwned = listingService.countListingByOwner(listingDto.ownerId());
        Long bookLeasing = listingService.countListingByOwnerAndStatus(listingDto.ownerId());
        UserDto user = userService.getUserById(listingDto.ownerId());
        ListingDetailDto listingDetailDto = new ListingDetailDto(
                listingDto.id(),
                user,
//                userService.getUserById(listingDto.ownerId()).id(),
                listingDto.quantity(),
                listingDto.address(),
                listingDto.leaseRate(),
                listingDto.depositFee(),
                listingDto.penaltyRate(),
                listingDto.description(),
                copy,
                book,
                reviews,
                bookOwned,
                bookLeasing
        );
        return ResponseEntity.ok(listingDetailDto);
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
        List<GenreDto> genreDto = genreService.getGenreByNameVn(genre);
        ListingSearchDto listingSearchDto = new ListingSearchDto();
        listingSearchDto.setTitle(title);
        if (!genreDto.isEmpty()) {
            listingSearchDto.setGenre(genreDto.get(0).name());
        }
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

    /////////đang làm
    @GetMapping("/search/byOwnerIdAndName")
    public Page<ListingExtendedDto> getListingByOwnerIdAndName (
            Pageable pageable,
            @RequestParam (name = "ownerId") Long ownerId,
            @RequestParam (name = "title") String title
        ) {
        ListingSearchByOwnerAndNameDto listingSearch = new ListingSearchByOwnerAndNameDto();
        listingSearch.setTitle(title);
        listingSearch.setOwnerId(ownerId);
        Page<ListingDto> listingDto = listingService.getListingByOwnerIdAndTitle(pageable,listingSearch);
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
