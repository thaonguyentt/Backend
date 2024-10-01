package base.api.book.controller;

import base.api.book.dto.BookDto;
import base.api.book.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://the-flying-bookstore.vercel.app"})
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookservice;

    public BookController(BookService bookservice) {
        this.bookservice = bookservice;
    }
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks () {
        return ResponseEntity.ok(bookservice.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById (@PathVariable Long id) {
        BookDto bookDto = bookservice.getBookById(id);
        if (bookDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(bookDto);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<BookDto>> getBookByName (@PathVariable String title) {
        List<BookDto> bookDto = bookservice.getAllBookByTitle(title);
        if (bookDto == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(bookDto);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook (@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookservice.createBook(bookDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook (@PathVariable Long id, @RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookservice.updateBook(bookDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook (@PathVariable Long id) {
        bookservice.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }
}
