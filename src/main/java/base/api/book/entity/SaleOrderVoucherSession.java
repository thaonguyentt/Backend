package base.api.book.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sales_order_voucher_session")
public class SaleOrderVoucherSession {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_order_voucher_session_id_gen")
  @SequenceGenerator(name = "sales_order_voucher_session_id_gen", sequenceName = "sales_order_voucher_session_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "sale_order_id", nullable = false)
  private Long saleOrderId;

  @Column(name = "voucher_id", nullable = false)
  private Long voucherId;


//  @NotNull
//  @ManyToOne(fetch = FetchType.EAGER, optional = false)
//  @JoinColumn(name = "sale_order_id", nullable = false)
//  private SaleOrder saleOrder;
//
//  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//  @JoinColumn(name = "voucher_id")
//  private VoucherSession voucherSession;

  @Column(name = "discount_amount", precision = 10, scale = 2)
  private BigDecimal discountAmount;


}