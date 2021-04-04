package br.com.puppyplace.core.modules.lead.impl;

import br.com.puppyplace.core.entities.Lead;
import br.com.puppyplace.core.modules.lead.LeadRepository;
import br.com.puppyplace.core.modules.lead.dtos.LeadDTO;
import br.com.puppyplace.core.modules.lead.impl.LeadServiceImpl;

import org.jeasy.random.EasyRandom;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
public class LeadServiceImplTest {
    @InjectMocks
    LeadServiceImpl leadService;

    EasyRandom easyRandom = new EasyRandom();

    @Mock
    LeadRepository leadRepository;

    @Test
    void testCreateLeadWithSuccess(){

        var leadDTO = easyRandom.nextObject(LeadDTO.class);
        when(leadRepository.save(any())).thenReturn(getLeadSaved(leadDTO));

        var leadCreated = leadService.createLead(leadDTO);

        verify(leadRepository, times(1)).save(any());
        assertNotNull(leadCreated.getId());
        assertNotNull(leadCreated.getName());
        assertNotNull(leadCreated.getEmail());
        assertNotNull(leadCreated.getCellphone());
        assertNotNull(leadCreated.getInterest());
    }

    private Lead getLeadSaved(LeadDTO leadDTO){
        return Lead.builder()
            .id(leadDTO.getId())
            .name(leadDTO.getName())
            .email(leadDTO.getEmail())
            .cellphone(leadDTO.getCellphone())
            .interest(leadDTO.getInterest())
            .build();
    }
}
