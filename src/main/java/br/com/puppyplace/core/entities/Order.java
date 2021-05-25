package br.com.puppyplace.core.entities;

import br.com.puppyplace.core.commons.enums.PayMethodEnum;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "order")
@Table(name = "order", schema = "public")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Order extends AbstractEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_customer", nullable = false)
    private Customer customer;

    @OneToOne(fetch =FetchType.EAGER)
    @JoinColumn(name= "id_address", nullable = false)
    private Address address;

    @Column
    @Enumerated(EnumType.STRING)
    private PayMethodEnum payMethod;

    @OneToMany(mappedBy = "order",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductOrder> productOrders = new ArrayList<>();

    @Column
    private Float total;

    @Column
    private String trackingCode;

}
