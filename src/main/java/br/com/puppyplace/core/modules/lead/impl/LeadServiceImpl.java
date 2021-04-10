package br.com.puppyplace.core.modules.lead.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.puppyplace.core.commons.exceptions.EmailUnavailableException;
import br.com.puppyplace.core.entities.Lead;
import br.com.puppyplace.core.modules.lead.LeadRepository;
import br.com.puppyplace.core.modules.lead.LeadService;
import br.com.puppyplace.core.modules.lead.dtos.LeadDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LeadServiceImpl implements LeadService {
    @Autowired
    private LeadRepository leadRepository;

    @Override
    public LeadDTO createLead(LeadDTO leadDTO) {      	
    	log.info(">>> Validating if email is available");
    	
    	var emailAlreadyInUse = this.leadRepository.findByEmail(leadDTO.getEmail());
    	
    	if(emailAlreadyInUse.isPresent()) {    		
    		log.info(">>> Email unavailable. Returning error to client.");    		
    		throw new EmailUnavailableException("Email is already in use.");
    	}    	
    	
        var lead = Lead.builder()
                .name(leadDTO.getName())
                .email(leadDTO.getEmail())
                .build();
        
        leadDTO = new LeadDTO(leadRepository.save(lead));
        
        log.info(">>> Lead {} persisted.", leadDTO);
        
        return leadDTO;
    }
}
