package br.com.puppyplace.core.modules.lead.dtos;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.com.puppyplace.core.entities.Lead;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LeadDTO implements Serializable {

	private static final long serialVersionUID;
	
	static {
		serialVersionUID = -451193093349710026L;
	}
	
	private UUID id;
    
    @NotEmpty
    private String name;
    
    @Email(message = "O e-mail deve ser válido")
    private String email;

    public LeadDTO(Lead lead) {
        this.id = lead.getId();
        this.name = lead.getName();
        this.email = lead.getEmail();
    }

	@Override
	public String toString() {
		return "{id:" + id + ", name:" + name + ", email:" + email + "}";
	}   
    

}