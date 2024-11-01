package base.api.book.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="VOUCHER_SESSION")
public class VoucherSession {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voucher_session_id_gen")
    @SequenceGenerator(name = "voucher_session_id_gen", sequenceName = "voucher_session_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false, length = 50)  // Add code column
    private String code;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate = LocalDate.now();

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate = LocalDate.now();

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "min_value", precision = 15, scale = 2)
    private BigDecimal minValue = BigDecimal.ZERO;

    @Column(name = "discount_amount", precision = 15, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(name = "voucher_type", nullable = false)
    private Integer voucherType;
}
