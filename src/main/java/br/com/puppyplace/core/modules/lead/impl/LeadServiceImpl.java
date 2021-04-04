package br.com.puppyplace.core.modules.lead.impl;

import br.com.puppyplace.core.entities.Lead;
import br.com.puppyplace.core.modules.lead.LeadRepository;
import br.com.puppyplace.core.modules.lead.LeadService;
import br.com.puppyplace.core.modules.lead.dtos.LeadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeadServiceImpl implements LeadService {
    @Autowired
    private LeadRepository leadRepository;

    @Override
    public LeadDTO createLead(LeadDTO leadDTO) {
        var lead = Lead.builder()
                .name(leadDTO.getName())
                .email(leadDTO.getEmail())
                .cellphone(leadDTO.getCellphone())
                .interest(leadDTO.getInterest())
                .build();
        leadRepository.save(lead);
        return new LeadDTO(lead);
    }


}
