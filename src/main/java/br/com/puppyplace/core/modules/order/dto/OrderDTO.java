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

    @Autowired
    private ModelMapper modelMapper;

    private static final long serialVersionUID = 7696933275615277004L;

    private UUID id;

    @NotEmpty
    private CustomerDTO customer;

    @NotEmpty
    private AddressDTO address;

    @NotEmpty
    private PayMethodEnum payMethod;

    @NotEmpty
    private List<ProductOrderDTO> productsOrder;

    @NotEmpty
    private BigDecimal total;

    @NotEmpty
    private String trackingCode;

}
