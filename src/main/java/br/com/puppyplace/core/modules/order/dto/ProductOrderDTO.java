package br.com.puppyplace.core.modules.order.dto;

import br.com.puppyplace.core.entities.Order;
import br.com.puppyplace.core.entities.Product;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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

    @NotEmpty(message = "Field Product is mandatory")
    private Product product;

    @NotEmpty(message = "Field Order is mandatory")
    private Order order;

    @Positive
    @NotEmpty(message = "Field unitPrice is mandatory")
    private BigDecimal unitPrice;

    @Min(value = 1)
    @NotEmpty(message = "Field quantity must be minimum 1")
    private Integer quantity;

    @Positive
    @NotEmpty(message = "Field totalPrice is mandatory")
    private BigDecimal totalPrice;
}
