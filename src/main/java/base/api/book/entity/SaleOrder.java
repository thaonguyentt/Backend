package base.api.book.entity;

import base.api.book.entity.support.LeaseOrderStatus;
import base.api.book.entity.support.SellOrderStatus;
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
@Table(name = "sales_order")
@ToString // TODO delete this
public class SaleOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sale_order_id_gen")
    @SequenceGenerator(name = "sale_order_id_gen", sequenceName = "sale_order_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "listing_id")
    private Long listingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SellOrderStatus status;

    @NotNull
    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @NotNull
    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @Column(name = "seller_address", length = Integer.MAX_VALUE)
    private String sellerAddress;

    @Column(name = "buyer_address", length = Integer.MAX_VALUE)
    private String buyerAddress;

    @NotNull
    @Column(name = "receive_date", nullable = false)
    private LocalDate receiveDate;

    @Column(name = "total_price ", precision = 10, scale = 2)
    private BigDecimal totalPrice ;

    @Column(name = "total_change", precision = 10, scale = 2)
    private BigDecimal totalChange;

    @Column(name = "total_compensate ", precision = 10, scale = 2)
    private BigDecimal totalCompensate ;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "sell_payment_id")
    private Long sellPaymentId;

    @Column(name = "change_payment_id")
    private Long changePaymentId;

    @Column(name = "compensate_payment_id")
    private Long compensatePaymentId;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "deleted_date")
    private LocalDate deletedDate;

//    @OneToMany(mappedBy = "SaleOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Set<LeaseOrderDetail> leaseOrderDetails = new LinkedHashSet<>();


}