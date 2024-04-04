package base.api.book.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "listing")
public class Listing {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listing_id_gen")
  @SequenceGenerator(name = "listing_id_gen", sequenceName = "listing_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "copy_id", nullable = false)
  private Copy copy;

  @NotNull
  @Column(name = "owner_id", nullable = false)
  private Long ownerId;

  @NotNull
  @Column(name = "quantity", nullable = false)
  private Long quantity;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "expiry_date")
  private LocalDate expiryDate;

  @Column(name = "lease_rate", precision = 10, scale = 2)
  private BigDecimal leaseRate;

  @Column(name = "deposit_fee", precision = 10, scale = 2)
  private BigDecimal depositFee;

  @Column(name = "penalty_rate", precision = 10, scale = 2)
  private BigDecimal penaltyRate;

  @Column(name = "description", length = Integer.MAX_VALUE)
  private String description;

//  @Column(name = "created_date")
//  private LocalDate createdDate;
//
//  @Column(name = "updated_date")
//  private LocalDate updatedDate;
//
//  @Column(name = "deleted_date")
//  private LocalDate deletedDate;

}