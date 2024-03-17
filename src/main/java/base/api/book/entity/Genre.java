package base.api.book.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "genre")
public class Genre {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_id_gen")
  @SequenceGenerator(name = "genre_id_gen", sequenceName = "genre_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
  private String name;

  @NotNull
  @Column(name = "name_vn", nullable = false, length = Integer.MAX_VALUE)
  private String nameVn;

  @Column(name = "created_date")
  private LocalDate createdDate;

  @Column(name = "updated_date")
  private LocalDate updatedDate;

  @Column(name = "deleted_date")
  private LocalDate deletedDate;

}