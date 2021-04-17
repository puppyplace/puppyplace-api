package br.com.puppyplace.core.modules.lead.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.puppyplace.core.commons.exceptions.EmailUnavailableException;
import br.com.puppyplace.core.entities.Lead;
import br.com.puppyplace.core.modules.lead.LeadRepository;
import br.com.puppyplace.core.modules.lead.dto.LeadDTO;


@ExtendWith(SpringExtension.class)
public class LeadServiceImplTest {
    @InjectMocks
    LeadServiceImpl leadService;    

    @Mock
    LeadRepository leadRepository;
    
    EasyRandom easyRandom = new EasyRandom();
    
    @BeforeEach
    void init() {
    	ReflectionTestUtils.setField(leadService, "mapper", new ModelMapper());
    }

    @Test
    void shouldSucceeded_WhenSendANewLeadDTO(){
    	//given
        var leadDTO = easyRandom.nextObject(LeadDTO.class);  
        when(leadRepository.findByEmail(leadDTO.getEmail())).thenReturn(Optional.ofNullable(null));
        when(leadRepository.save(any(Lead.class))).thenReturn(getLeadSaved(leadDTO));
        
        //when
        var leadCreated = leadService.create(leadDTO);

        //then
        verify(leadRepository, times(1)).save(any(Lead.class));
        assertNotNull(leadCreated.getId());
        assertNotNull(leadCreated.getName());
        assertNotNull(leadCreated.getEmail());
    }
    
    @Test
    void shouldThrowsError_WhenSendALeadDTOWithAlreadyUsedEmail(){
    	//given
        var leadDTO = easyRandom.nextObject(LeadDTO.class);  
        when(leadRepository.findByEmail(leadDTO.getEmail())).thenReturn(Optional.ofNullable(new Lead()));
        
        //then
        assertThrows(EmailUnavailableException.class, () -> {
        	leadService.create(leadDTO);
        });

        verify(leadRepository, times(0)).save(any(Lead.class));
    }

    private Lead getLeadSaved(LeadDTO leadDTO){
        return Lead.builder()
            .id(leadDTO.getId())
            .name(leadDTO.getName())
            .email(leadDTO.getEmail())
            .build();
    }
}
