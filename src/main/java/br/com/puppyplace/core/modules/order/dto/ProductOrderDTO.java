package br.com.puppyplace.core.modules.order.dto;

import br.com.puppyplace.core.entities.Order;
import br.com.puppyplace.core.entities.Product;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDTO implements Serializable {

    private static final long serialVersionUID = 291678832931506131L;

    private UUID id;

    private Product product;

    private Order order;

    private BigDecimal unitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;
}
