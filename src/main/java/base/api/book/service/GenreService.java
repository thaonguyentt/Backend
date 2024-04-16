package base.api.book.service;

import base.api.book.dto.GenreDto;
import base.api.book.entity.Genre;
import base.api.book.mapper.GenreMapper;
import base.api.book.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GenreService {

  private final GenreRepository genreRepository;
  private final GenreMapper genreMapper;

  public GenreService(GenreRepository genreRepository, GenreMapper genreMapper) {
    this.genreRepository = genreRepository;
    this.genreMapper = genreMapper;
  }

  public GenreDto createGenre(GenreDto genreDto) {
    Genre genre = genreMapper.toEntity(genreDto);
    Genre createdGenre = genreRepository.save(genre);
    return genreMapper.toDto(createdGenre);
  }

  public GenreDto getGenreById(Long genreId) {
    Optional<Genre> optionalGenre = genreRepository.findById(genreId);
    return optionalGenre.map(genreMapper::toDto).orElse(null);
  }

  public GenreDto getGenreByNameVn (String nameVn) {
    Optional<Genre> optionalGenre = genreRepository.findGenreByNameVn(nameVn);
    return optionalGenre.map(genreMapper::toDto).orElse(null);
  }
  public List<GenreDto> getAllGenres() {
    return genreRepository.findAll().stream().map(genreMapper::toDto).collect(Collectors.toList());
  }

  public GenreDto updateGenre(GenreDto newGenreDto) {
    Long id = newGenreDto.id();
//    Genre oldGenre = genreRepository.getReferenceById(id);
    Genre newGenre = genreMapper.toEntity(newGenreDto);
    newGenre.setId(id);
    Genre updatedGenre = genreRepository.save(newGenre);
    return genreMapper.toDto(updatedGenre);
  }

  public void deleteGenreById(Long id) {
    genreRepository.deleteById(id);
  }


}
