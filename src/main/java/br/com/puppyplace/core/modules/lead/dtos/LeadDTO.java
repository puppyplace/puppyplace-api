package br.com.puppyplace.core.modules.lead.dtos;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.com.puppyplace.core.commons.enums.Interest;
import br.com.puppyplace.core.entities.Lead;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadDTO implements Serializable {

    private static final long serialVersionUID;

    static {
        serialVersionUID = 1L;
    }

    private Long id;
    
    @NotEmpty
    private String name;
    
    @Email(message = "O e-mail deve ser válido")
    private String email;
    
    @NotEmpty
    private String cellphone;
    
    private Interest interest;

    public LeadDTO(Lead lead) {
        this.id = lead.getId();
        this.name = lead.getName();
        this.email = lead.getEmail();
        this.cellphone = lead.getCellphone();
        this.interest = lead.getInterest();
    }

}