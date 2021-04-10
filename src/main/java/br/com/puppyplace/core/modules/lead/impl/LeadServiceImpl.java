package br.com.puppyplace.core.modules.lead.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.puppyplace.core.commons.exceptions.RegraDeNegocioException;
import br.com.puppyplace.core.entities.Lead;
import br.com.puppyplace.core.modules.lead.LeadRepository;
import br.com.puppyplace.core.modules.lead.LeadService;
import br.com.puppyplace.core.modules.lead.dtos.LeadDTO;

@Service
public class LeadServiceImpl implements LeadService {
    @Autowired
    private LeadRepository leadRepository;

    @Override
    public LeadDTO createLead(LeadDTO leadDTO) {  
    	var emailAlreadyInUse = this.leadRepository.findByEmail(leadDTO.getEmail());
    	
    	if(emailAlreadyInUse.isPresent()) {
    		throw new RegraDeNegocioException("Email " + leadDTO.getEmail() + " Already In Use. Try with another.");
    	}    	
    	
        var lead = Lead.builder()
                .name(leadDTO.getName())
                .email(leadDTO.getEmail())
                .cellphone(leadDTO.getCellphone())
                .interest(leadDTO.getInterest())
                .build();
        var leadSaved = leadRepository.save(lead);
        
        return new LeadDTO(leadSaved);
    }


}
