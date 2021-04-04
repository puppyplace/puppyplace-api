package br.com.puppyplace.core.modules.lead.dtos;

import br.com.puppyplace.core.commons.enums.Interest;
import br.com.puppyplace.core.entities.Lead;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
    private String name;
    private String email;
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