package base.api.lease.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "lease")
public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_gen")
    @SequenceGenerator(name = "book_id_gen", sequenceName = "book_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "lessor_id",nullable = false)
    private Long lessor_id;

    @Column(name = "lessor_id",nullable = false)
    private Long lessee_id;

    @Column(name = "listing_id")
    private Long listing_id;

    @Column(name = "status_id")
    private Long status_id;

    @Column(name = "created_date")
    private LocalDate created_date;

    @Column(name = "from_date")
    private LocalDate from_date;

    @Column(name = "to_date")
    private LocalDate to_date;

    @Column(name = "receive_date")
    private LocalDate receive_date;

    @Column(name = "return_date")
    private LocalDate return_date;

    @Column(name = "book_name")
    private String book_name;

    @Column(name = "lessor_address")
    private String lessor_address;

    @Column(name = "lessee_address ")
    private String lessee_address;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "penalty_fee", precision = 10, scale = 2)
    private BigDecimal penalty_fee;

    @Column(name = "deposit", precision = 10, scale = 2)
    private BigDecimal deposit;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "payment_method", nullable = false)
    private Long payment_method;

    @Column(name = "link_img")
    private String link_img;

    @Column(name = "payment_deposit_id")
    private Long payment_deposit_id;

    @Column(name = "payment_refund_id")
    private Long payment_refund_id;

    @Column(name = "payment_pay_owner_id")
    private Long payment_pay_owner_id;
}
