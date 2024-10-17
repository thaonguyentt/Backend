package base.api.book.controller;

import base.api.book.dto.GenreDto;
import base.api.book.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8082", "https://the-flying-bookstore.vercel.app"})
@RequestMapping("/api/genre")
public class GenreController {

  private final GenreService genreService;

  public GenreController(GenreService genreService) {
    this.genreService = genreService;
  }

  @GetMapping
  public ResponseEntity<List<GenreDto>> getAllGenre() {
    return ResponseEntity.ok(genreService.getAllGenres());
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenreDto> getGenreById(@PathVariable Long id) {
    GenreDto genreDto = genreService.getGenreById(id);
    if (genreDto == null) { return ResponseEntity.notFound().build(); }
    return ResponseEntity.ok(genreDto);
  }

  @PostMapping
  public ResponseEntity<GenreDto> createGenre(@RequestBody GenreDto genreDto) {
    return ResponseEntity.ok(genreService.createGenre(genreDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenreDto> updateGenre(@PathVariable Long id, @RequestBody GenreDto genreDto) {
    return ResponseEntity.ok(genreService.updateGenre(genreDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
    genreService.deleteGenreById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/nameVn")
  public ResponseEntity<String> getGenreByNameVn(@RequestParam(name = "genre") String nameVn) {
    return ResponseEntity.ok(genreService.getGenreByNameVn(nameVn).get(0).name());
  }
}
