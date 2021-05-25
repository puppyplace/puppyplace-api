package br.com.puppyplace.core.entities;

import br.com.puppyplace.core.modules.order.dto.ProductOrderDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_order", schema = "public")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductOrder extends AbstractEntity {

    public ProductOrder(ProductOrderDTO productOrderDTO){
        this.quantity = productOrderDTO.getQuantity();
        this.unitPrice = productOrderDTO.getUnitPrice();
        this.totalPrice = productOrderDTO.getTotalPrice();
    }

    @OneToOne(fetch =FetchType.EAGER)
    @JoinColumn(name= "id_product", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "id_order", nullable = false)
    private Order order;

    @Column
    private Float unitPrice;

    @Column
    private Integer quantity;

    @Column
    private Float totalPrice;

}
