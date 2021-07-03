package br.com.puppyplace.core.modules.customer.dto;

import br.com.puppyplace.core.modules.address.dto.AddressDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO implements Serializable {

    private static final long serialVersionUID = -3788501987274403060L;

    private UUID id;

    private String firstName;

    private String lastName;

    @CPF
    private String document;

    @Email(message = "It should be a valid email")
    private String email;

    private List<AddressDTO> addresses;

    private String cellphone;

    @Past
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthdate;

    private boolean active;
}
