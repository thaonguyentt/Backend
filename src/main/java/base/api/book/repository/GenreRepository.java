package base.api.book.repository;

import base.api.book.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.w3c.dom.Text;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long>, JpaSpecificationExecutor<Genre> {
    public Optional<Genre> findByNameVnContaining  (String nameVn);
}