package base.api.book.controller;

import base.api.book.dto.BooksDto;
import base.api.book.service.BooksService;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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
