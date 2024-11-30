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
@Table(name = "sales_order_voucher_shop")
public class SaleOrderVoucherShop {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_order_voucher_shop_id_gen")
  @SequenceGenerator(name = "sales_order_voucher_shop_id_gen", sequenceName = "sales_order_voucher_shop_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "sale_order_id", nullable = false)
  private Long saleOrderId;

  @Column(name = "voucher_id", nullable = false)
  private Long voucherId;
//  @NotNull
//  @OneToOne(fetch = FetchType.EAGER, optional = false)
//  @JoinColumn(name = "sale_order_id", nullable = false)
//  private SaleOrder saleOrder;
//
//  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//  @JoinColumn(name = "voucher_id")
//  private VoucherShop voucherShop;

  @Column(name = "discount_amount", precision = 10, scale = 2)
  private BigDecimal discountAmount;


}