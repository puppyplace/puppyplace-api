package br.com.puppyplace.core.modules.order.dto;

import br.com.puppyplace.core.commons.enums.PayMethodEnum;
import br.com.puppyplace.core.entities.Address;
import br.com.puppyplace.core.entities.Customer;
import br.com.puppyplace.core.entities.Order;
import br.com.puppyplace.core.entities.ProductOrder;
import br.com.puppyplace.core.modules.customer.dto.AddressDTO;
import br.com.puppyplace.core.modules.customer.dto.CustomerDTO;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
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

    @NotEmpty(message = "Field Customer is mandadory")
    private CustomerDTO customer;

    @NotEmpty(message = "Field Address is mandaroty")
    private AddressDTO address;

    @NotEmpty(message = "Field payMethod is mandatory")
    private PayMethodEnum payMethod;

    @NotEmpty(message = "List of Products must be minimum 1")
    private List<ProductOrderDTO> productsOrder;

    @Positive
    @NotEmpty(message = "Field Total is mandaory")
    private BigDecimal total;

    private String trackingCode;

}
