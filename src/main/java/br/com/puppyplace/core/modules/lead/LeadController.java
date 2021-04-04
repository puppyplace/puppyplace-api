package br.com.puppyplace.core.modules.lead;

import br.com.puppyplace.core.modules.lead.dtos.LeadDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lead")
@Slf4j
public class LeadController {
    @Autowired
    private LeadService leadService;

    @PostMapping
    @Operation(summary = "Create Lead")
    public ResponseEntity<LeadDTO> create(@RequestBody LeadDTO leadDTO){
        log.info("[POST][LEAD] Request: {}", leadDTO);
        var createdLead = leadService.createLead(leadDTO);

        log.info("[POST][LEAD] Response: {}", createdLead);
        return ResponseEntity.ok(createdLead);
    }
}
