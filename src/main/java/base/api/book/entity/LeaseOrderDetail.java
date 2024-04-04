package base.api.book.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "lease_order_detail")
public class LeaseOrderDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lease_order_detail_id_gen")
  @SequenceGenerator(name = "lease_order_detail_id_gen", sequenceName = "lease_order_detail_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "lease_order_id", nullable = false)
  private LeaseOrder leaseOrder;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "listing_id")
  private Listing listing;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "copy_id", nullable = false)
  private Copy copy;

  @Column(name = "lease_rate", precision = 10, scale = 2)
  private BigDecimal leaseRate;

  @Column(name = "deposit_fee", precision = 10, scale = 2)
  private BigDecimal depositFee;

  @Column(name = "penalty_rate", precision = 10, scale = 2)
  private BigDecimal penaltyRate;

}