package base.api.book.entity;

import base.api.common.internal.entity.Language;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_gen")
  @SequenceGenerator(name = "book_id_gen", sequenceName = "book_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "isbn", nullable = false, length = Integer.MAX_VALUE)
  private String isbn;

  @NotNull
  @Column(name = "title", nullable = false, length = Integer.MAX_VALUE)
  private String title;

  @Type(ListArrayType.class)
  @Column(name = "authors", columnDefinition = "text[]")
  private List<String> authors;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "language")
  private Language language;

  @Type(ListArrayType.class)
  @Column(name = "genre", columnDefinition = "text[]")
  private List<String> genre;

  @Column(name = "publisher", length = Integer.MAX_VALUE)
  private String publisher;

  @Column(name = "published_date")
  private LocalDate publishedDate;

  @Column(name = "page_count")
  private Integer pageCount;

  @Column(name = "size", length = Integer.MAX_VALUE)
  private String size;

  @Column(name = "created_date")
  private LocalDate createdDate;

  @Column(name = "updated_date")
  private LocalDate updatedDate;

  @Column(name = "deleted_date")
  private LocalDate deletedDate;

}