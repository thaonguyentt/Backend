package base.api.book.controller;

import base.api.book.dto.*;
import base.api.book.dto.search.ListingSearchByOwnerAndNameDto;
import base.api.book.dto.search.ListingSearchDto;
import base.api.book.service.*;
import base.api.user.UserDto;
import base.api.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8082", "https://the-flying-bookstore.vercel.app","https://the-flying-bookstore-dashboard-fe.vercel.app"})
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
        List<ReviewDto> reviews = reviewService.getReviewByListingId(listingDto.id());
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
                listingDto.price(),
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
      @RequestParam(name = "genre", required = false) String genre,
      @RequestParam(name = "allowRent") Number allowRent,
      @RequestParam(name = "allowPurchase") Number allowPurchase
    ) {
        List<GenreDto> genreDto = genreService.getGenreByNameVn(genre);
        ListingSearchDto listingSearchDto = new ListingSearchDto();
        listingSearchDto.setTitle(title);
        listingSearchDto.setAllowPurchase(allowPurchase);
        listingSearchDto.setAllowRent(allowRent);
        if (!genreDto.isEmpty()) {
            listingSearchDto.setGenre(genreDto.getFirst().name());
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
                dto.price(),
                dto.description(),
                dto.allow_rent(),
                dto.allow_purchase(),
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
                            dto.price(),
                            dto.description(),
                            dto.allow_rent(),
                            dto.allow_purchase(),
                            copyDto,
                            bookDto
                    );
                });
    }


    @GetMapping("/search/byOwnerIdAndName")
    public Page<ListingExtendedDto> getListingByOwnerIdAndName (
            Pageable pageable,
            @RequestParam (name = "ownerId") Long ownerId,
            @RequestParam (name = "title") String title
        ) {
        ListingSearchByOwnerAndNameDto listingSearch = new ListingSearchByOwnerAndNameDto();
        listingSearch.setTitle(title);
        listingSearch.setOwnerId(ownerId);
        Page<ListingDto> listingDto = listingService.getListingByOwnerIdAndTitleContainer(pageable,listingSearch);
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
                    dto.price(),
                    dto.description(),
                    dto.allow_rent(),
                    dto.allow_purchase(),
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
//        Long copyId = listingService.getListingById(id).copyId();
        listingService.deleteListing(id);
//        copyService.deleteCopy(copyId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/editStatus/{id}")
    public ResponseEntity<ListingDto> updateRentStatusAndPurchaseStatusListing (@PathVariable Long id, @RequestBody EditStatusListingRequest statusRequest) {
        return ResponseEntity.ok(null);
    }


}
