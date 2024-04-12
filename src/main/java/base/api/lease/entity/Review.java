package base.api.lease.entity;

import base.api.book.entity.LeaseOrder;
import base.api.user.internal.entity.User;
import io.hypersistence.utils.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "review")
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_gen")
  @SequenceGenerator(name = "review_id_gen", sequenceName = "review_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "score")
  private Float score;

  @Column(name = "description", length = Integer.MAX_VALUE)
  private String description;

  @Column(name = "image_link")
  @Type(StringArrayType.class)
  private String[] imageLink;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "lease_order_id", nullable = false)
  private LeaseOrder leaseOrder;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @Column(name = "user_id", nullable = false)
  private User user;

  @NotNull
  @Column(name = "listing_id", nullable = false)
  private Long listingId;

  @Column(name = "created_date")
  private LocalDate createdDate;

  @Column(name = "updated_date")
  private LocalDate updatedDate;

  @Column(name = "deleted_date")
  private LocalDate deletedDate;

}