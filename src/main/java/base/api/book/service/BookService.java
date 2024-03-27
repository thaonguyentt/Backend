package base.api.book.service;


import base.api.book.dto.BookDto;
import base.api.book.entity.Book;
import base.api.book.mapper.BookMapper;
import base.api.book.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public BookDto createBook (BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        Book createdBook = bookRepository.save(book);
        return bookMapper.toDto(createdBook);
    }

    public BookDto getBookById (Long id) {
        Optional<Book> optionalBook= bookRepository.findById(id);
        return optionalBook.map(bookMapper::toDto).orElse(null);
    }

    public List<BookDto> getAllBooks () {
        return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    public List<BookDto> getAllBookByTitle(String title) {
        return bookRepository.findByTitleContaining(title)
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDto updateBook (BookDto newBookDTO) {
        Long id = newBookDTO.id();
        Book newBook = bookMapper.toEntity(newBookDTO);
        newBook.setId(id);
        Book updatedBook = bookRepository.save(newBook);
        return bookMapper.toDto(updatedBook);
    }

    public void deleteBookById (Long id) {
        bookRepository.deleteById(id);
    }
 }
