package base.api.book.entity;

import base.api.book.entity.support.LeaseOrderStatus;
import base.api.payment.entity.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "lease_order")
@ToString // TODO delete this
public class LeaseOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lease_order_id_gen")
  @SequenceGenerator(name = "lease_order_id_gen", sequenceName = "lease_order_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "listing_id")
  private Long listingId;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private LeaseOrderStatus status;

  @NotNull
  @Column(name = "lessor_id", nullable = false)
  private Long lessorId;

  @NotNull
  @Column(name = "lessee_id", nullable = false)
  private Long lesseeId;

  @Column(name = "lessor_address", length = Integer.MAX_VALUE)
  private String lessorAddress;

  @Column(name = "lessee_address", length = Integer.MAX_VALUE)
  private String lesseeAddress;

  @NotNull
  @Column(name = "from_date", nullable = false)
  private LocalDate fromDate;

  @NotNull
  @Column(name = "to_date", nullable = false)
  private LocalDate toDate;

  @Column(name = "receive_date")
  private LocalDate receiveDate;

  @Column(name = "return_date")
  private LocalDate returnDate;

  @Column(name = "total_lease_fee", precision = 10, scale = 2)
  private BigDecimal totalLeaseFee;

  @Column(name = "total_penalty_rate", precision = 10, scale = 2)
  private BigDecimal totalPenaltyRate;

  @Column(name = "total_deposit", precision = 10, scale = 2)
  private BigDecimal totalDeposit;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "payment_method")
  private PaymentMethod paymentMethod;

  @Column(name = "image_link", length = Integer.MAX_VALUE)
  private String imageLink;

  @Column(name = "lease_and_deposit_payment_id")
  private Long leaseAndDepositPaymentId;

  @Column(name = "refund_deposit_payment_id")
  private Long refundDepositPaymentId;

  @Column(name = "pay_owner_payment_id")
  private Long payOwnerPaymentId;

  @Column(name = "created_date")
  private LocalDate createdDate;

  @Column(name = "updated_date")
  private LocalDate updatedDate;

  @Column(name = "deleted_date")
  private LocalDate deletedDate;

  @OneToMany(mappedBy = "leaseOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<LeaseOrderDetail> leaseOrderDetails = new LinkedHashSet<>();

  @OneToMany(mappedBy = "leaseOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Review> reviews = new LinkedHashSet<>();

}