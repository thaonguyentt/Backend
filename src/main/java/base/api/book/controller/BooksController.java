package base.api.book.controller;

import base.api.book.dto.BooksDto;
import base.api.book.service.BooksService;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://the-flying-bookstore.vercel.app"})
@RequestMapping("/api/books")
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public List<BooksDto> getAllBooks () {
        return booksService.getALlBooks();
    }

    @GetMapping("/genre/{genre}")
    public List<BooksDto> getBookByGenre (@PathVariable String genre){
        return booksService.getBooksByGenre(genre);
    }

    @GetMapping("/key/{key}")
    public List<BooksDto> getBookByKey (@PathVariable String key) {
        return booksService.getBooksByKey(key);
    }




}
