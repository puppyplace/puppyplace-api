package br.com.puppyplace.core.modules.lead.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadDTO implements Serializable {

	private static final long serialVersionUID = -451193093349710026L;
	
	private UUID id;
    
    @NotEmpty(message = "Name could'nt be empty")
    private String name;
    
    @Email(message = "It should be a valid email")
    private String email;
    
}