package br.com.puppyplace.core.modules.order.dto;

import br.com.puppyplace.core.commons.enums.StateEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderAddressDTO implements Serializable {

    private static final long serialVersionUID = 2184184855092407263L;

    private UUID addressId;

    private String addressStreet;

    private String addressNumber;

    private String addressComplement;

    private String addressZipcode;

    private String addressCity;

    private StateEnum addressState;
}
