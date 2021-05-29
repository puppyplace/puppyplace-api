package br.com.puppyplace.core.modules.order.dto;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.UUID;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class ProductOrderDTO implements Serializable {

    private static final long serialVersionUID = 291678832931506131L;

    private UUID id;

    @NotNull(message = "Field ID Product is mandatory")
    private UUID productId;

    @Positive
    @DecimalMin(value = "0.0", inclusive = false, message = "Field unitPrice is mandatory")
    private Float unitPrice;

    @Min(value = 1, message = "Field quantity must be minimum 1")
    private Integer quantity;

    @Positive
    @DecimalMin(value = "0.0", inclusive = false, message = "Field totalPrice is mandatory")
    private Float totalPrice;
}
