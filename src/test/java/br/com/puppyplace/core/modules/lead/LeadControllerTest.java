package br.com.puppyplace.core.modules.lead;

import br.com.puppyplace.core.modules.lead.dtos.LeadDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LeadController.class)
public class LeadControllerTest {
    @Autowired
    private MockMvc httpRequest;

    @Autowired
    private ObjectMapper mapper;

    EasyRandom easyRandom = new EasyRandom();

    @MockBean
    private LeadService leadService;

    @Test
    void createLeadWithSuccess() throws Exception{
        var leadDTO = easyRandom.nextObject(LeadDTO.class);
        when(leadService.createLead(any(LeadDTO.class))).thenReturn(leadDTO);

        httpRequest.perform(post("/lead")
                .contentType("application/json")
                .content(mapper.writeValueAsString(leadDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(leadDTO.getId()))
                .andExpect(jsonPath("name").value(leadDTO.getName()))
                .andExpect(jsonPath("email").value(leadDTO.getEmail()))
                .andExpect(jsonPath("cellphone").value(leadDTO.getCellphone()))
                .andExpect(jsonPath("interest").value(leadDTO.getInterest().toString()));

    }
}
