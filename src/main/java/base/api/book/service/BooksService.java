package base.api.book.service;

import base.api.book.dto.BooksDto;
import base.api.book.entity.Listing;
import base.api.book.repository.BookRepository;
import base.api.book.repository.CopyRepository;
import base.api.book.repository.ListingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<BooksDto> listbooks = new ArrayList<>();

        for (Listing listing : listings) {

        }
    }
}
