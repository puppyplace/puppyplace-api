package br.com.puppyplace.core.modules.order.dto;

import br.com.puppyplace.core.commons.enums.PayMethodEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 7696933275615277004L;

    private UUID id;

    @NotNull(message = "Field ID Customer is mandatory")
    private UUID customerId;

    @NotNull(message = "Field ID Address is mandaroty")
    private UUID addressId;

    @NotNull(message = "Field payMethod is mandatory")
    private PayMethodEnum payMethod;

    @NotEmpty(message = "List of Products must be minimum 1")
    @JsonProperty(value = "productOrders")
    private List<ProductOrderDTO> productOrderDTOS ;

    @DecimalMin(value = "0.0", inclusive = false, message = "Field Total is mandatory")
    private Float total;

    private String trackingCode;

}
