package br.com.puppyplace.core.modules.address.dto;

import br.com.puppyplace.core.commons.enums.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO implements Serializable {
    private static final long serialVersionUID = 4048477763840042960L;

    private UUID id;

    @NotEmpty
    private String street;

    @NotEmpty
    private String number;

    @NotEmpty
    private String complement;

    @NotEmpty
    private String zipcode;

    @NotEmpty
    private String city;

    private StateEnum state;

    private boolean principal;
}
