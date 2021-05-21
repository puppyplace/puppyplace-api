package br.com.puppyplace.core.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductOrder extends AbstractEntity {

    @OneToOne
    @JoinColumn(name= "id_product", nullable = false)
    private Product product;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name= "id_order", nullable = false)
    private Order order;

    @Column
    private BigDecimal unitPrice;

    @Column
    private Integer quantity;

    @Column
    private BigDecimal totalPrice;

}
