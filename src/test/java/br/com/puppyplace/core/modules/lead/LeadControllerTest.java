package br.com.puppyplace.core.modules.lead;

import static org.jeasy.random.FieldPredicates.named;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.EmailRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.puppyplace.core.modules.lead.dto.LeadDTO;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LeadController.class)
public class LeadControllerTest {
	@Autowired
	private MockMvc httpRequest;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private LeadService leadService;	

	private EasyRandom easyRandom;

	@BeforeEach
	void init() {
		var emailRandomizer = new EmailRandomizer();
		var parameters = new EasyRandomParameters().randomize(named("email"), () -> emailRandomizer.getRandomValue());
		this.easyRandom = new EasyRandom(parameters);
	}

	@Test
	void shouldReturnSuccess_WhenSendANewCorrectLead() throws Exception {
		//given
		var leadDTO = easyRandom.nextObject(LeadDTO.class);
		when(leadService.create(any(LeadDTO.class))).thenReturn(leadDTO);

		//when
		httpRequest.perform(post("/lead").contentType("application/json").content(mapper.writeValueAsString(leadDTO)))
				.andExpect(status().isCreated()).andExpect(jsonPath("id").value(leadDTO.getId().toString()))
				.andExpect(jsonPath("name").value(leadDTO.getName()))
				.andExpect(jsonPath("email").value(leadDTO.getEmail()));
		
		// then
		verify(leadService, times(1)).create(leadDTO);
	}

    @Test
    void shouldReturnError_WhenSendANewLeadWithInvalidEmail() throws Exception{
    	//given
        var leadDTO = easyRandom.nextObject(LeadDTO.class);
        leadDTO.setEmail("invalid");        
        when(leadService.create(any(LeadDTO.class))).thenReturn(leadDTO);

        //when
        httpRequest.perform(post("/lead")
            .contentType("application/json")
            .content(mapper.writeValueAsString(leadDTO)))
            .andExpect(status().isBadRequest());                

		// then
		verify(leadService, times(0)).create(any(LeadDTO.class));
    }
    
    @Test
    void shouldReturnError_WhenSendANewLeadWithEmptyName() throws Exception{
    	//given
        var leadDTO = easyRandom.nextObject(LeadDTO.class);
        leadDTO.setName("");
        when(leadService.create(any(LeadDTO.class))).thenReturn(leadDTO);

        //when
        httpRequest.perform(post("/lead")
            .contentType("application/json")
            .content(mapper.writeValueAsString(leadDTO)))
            .andExpect(status().isBadRequest());                

		// then
		verify(leadService, times(0)).create(any(LeadDTO.class));
    }
}
