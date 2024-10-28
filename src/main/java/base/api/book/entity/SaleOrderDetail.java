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
@Table(name = "sales_order_detail")
public class SaleOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_order_detail_id_gen")
    @SequenceGenerator(name = "sales_order_detail_id_gen", sequenceName = "sales_order_detail_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false, length = Integer.MAX_VALUE)
    private String title;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "sale_order_id", nullable = false)
    private SaleOrder saleOrder;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "listing_id")
    private Listing listing;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "copy_id", nullable = false)
    private Copy copy;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

}
