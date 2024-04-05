package base.api.book.service;

import base.api.book.dto.BooksDto;
import base.api.book.entity.Book;
import base.api.book.entity.Copy;
import base.api.book.entity.Listing;
import base.api.book.repository.BookRepository;
import base.api.book.repository.CopyRepository;
import base.api.book.repository.ListingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BooksService {
    private final ListingRepository listingRepository;

    private final CopyRepository copyRepository;

    private final BookRepository bookRepository;


    public BooksService(ListingRepository listingRepository, CopyRepository copyRepository, BookRepository bookRepository) {
        this.listingRepository = listingRepository;
        this.copyRepository = copyRepository;
        this.bookRepository = bookRepository;
    }


    public List<BooksDto> getALlBooks () {

        List<Listing> listings = listingRepository.findAll();
        List<BooksDto> booksDto = new ArrayList<>();

        for (Listing listing : listings) {
            Long listingId = listing.getId();
            Long copyId = listing.getCopy().getId();
            Optional<Copy> copy = copyRepository.findById(copyId);
            Book book = copy.get().getBook();
            String title = book.getTitle();
            BigDecimal leaseRate = listing.getLeaseRate();
            String link_img = copy.get().getImageLink();
            String publisher = book.getPublisher();
            String language = book.getLanguage().getName();
            List<String> genre = book.getGenre();
            BooksDto bookDto = new BooksDto(listingId,copyId,leaseRate,link_img,title,publisher,language,genre);
            booksDto.add(bookDto);
        }
        return booksDto;
    }

    public List<BooksDto> getBooksByGenre (String genre) {
        List<Listing> listings = listingRepository.findAll();
        List<BooksDto> booksDto = new ArrayList<>();

        for (Listing listing : listings) {
            Long listingId = listing.getId();
            Long copyId = listing.getCopy().getId();
            Optional<Copy> copy = copyRepository.findById(copyId);
            String title = copy.get().getBook().getTitle();
            BigDecimal leaseRate = listing.getLeaseRate();
            String link_img = copy.get().getImageLink();
            Book book = copy.get().getBook();
            List<String> bookGenre= book.getGenre();
            String publisher = book.getPublisher();
            String language = book.getLanguage().getName();
            List<String> genrelist = book.getGenre();
            for (String gen : bookGenre) {
                if (gen.equals(genre)) {
                    BooksDto bookDto = new BooksDto(listingId,copyId,leaseRate,link_img,title,publisher,language,genrelist);
                    booksDto.add(bookDto);
                }

            }
        }
        return booksDto;
    }

    public List<BooksDto> getBooksByKey (String key) {
        List<Listing> listings = listingRepository.findAll();
        List<BooksDto> booksDto = new ArrayList<>();

        for (Listing listing : listings) {
            Long listingId = listing.getId();
            Long copyId = listing.getCopy().getId();
            Optional<Copy> copy = copyRepository.findById(copyId);
            String title = copy.get().getBook().getTitle();
            BigDecimal leaseRate = listing.getLeaseRate();
            String link_img = copy.get().getImageLink();
            Book book = copy.get().getBook();
            List<String> bookGenre= book.getGenre();
            String publisher = book.getPublisher();
            String language = book.getLanguage().getName();
            List<String> genrelist = book.getGenre();
            Boolean flag = false;
            for (String gen : bookGenre) {
                if (gen.equals(key)) {
                    flag = true;
                    break;
                } else if (key.trim().toLowerCase().equals(title.trim().toLowerCase())){
                    flag = true;
                };
            }
            if (flag.equals(true)) {
                BooksDto bookDto = new BooksDto(listingId,copyId,leaseRate,link_img,title,publisher,language,genrelist);
                booksDto.add(bookDto);
            }
        }
        return booksDto;

    }
}
