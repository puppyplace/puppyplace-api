package br.com.puppyplace.core.entities;

import br.com.puppyplace.core.commons.enums.PayMethodEnum;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity(name="order")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Order extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @OneToOne
    @JoinColumn(name= "id_address", nullable = false)
    private Address address;

    @Column
    @Enumerated(EnumType.STRING)
    private PayMethodEnum payMethod;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<ProductOrder> productsOrder;

    @Column
    private BigDecimal total;

    @Column
    private String trackingCode;

}
