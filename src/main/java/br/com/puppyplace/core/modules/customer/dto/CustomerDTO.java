package br.com.puppyplace.core.modules.customer.dto;

import br.com.puppyplace.core.entities.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO implements Serializable {

    private static final long serialVersionUID = -3788501987274403060L;

    private UUID id;

    @NotEmpty(message = "Name could not be empty")
    private String name;

    @NotEmpty(message = "Document could not be empty")
    private String document;

    @Email(message = "It should be a valid email")
    private String email;

    private List<Address> addresses;

    private String cellphone;

    @Past
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private boolean active;
}
