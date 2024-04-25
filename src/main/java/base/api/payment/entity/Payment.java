package base.api.payment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "payer_id", nullable = false)
    private Long payerId;

    @Column(name = "payee_id")
    private Long payeeId;

    // TODO create enum
    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "description")
    private String description;

    // For bank transfers only
    @Column(name = "bank_transfer_img_url")
    private String bankTransferImgUrl;

}