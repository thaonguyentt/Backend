package base.api.book.entity;

import base.api.book.entity.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "copy")
public class Copy {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "copy_id_gen")
  @SequenceGenerator(name = "copy_id_gen", sequenceName = "copy_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "book_id", nullable = false)
  private Book book;

  @NotNull
  @Column(name = "owner_id", nullable = false)
  private Long ownerId;

  @NotNull
  @Column(name = "quantity", nullable = false)
  private Long quantity;

  @Column(name = "image_link", length = Integer.MAX_VALUE)
  private String imageLink;

//  @Column(name = "price", precision = 10, scale = 2)
//  private BigDecimal price;

  @Column(name = "damage_percent")
  private BigDecimal damagePercent;



//  @Column(name = "created_date")
//  private LocalDate createdDate;
//
//  @Column(name = "updated_date")
//  private LocalDate updatedDate;
//
//  @Column(name = "deleted_date")
//  private LocalDate deletedDate;

}