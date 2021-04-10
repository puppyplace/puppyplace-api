package br.com.puppyplace.core.modules.lead;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.puppyplace.core.modules.lead.dtos.LeadDTO;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/lead")
@Validated
@Slf4j
public class LeadController {
    @Autowired
    private LeadService leadService;

    @PostMapping
    public ResponseEntity<LeadDTO> create(@Valid @RequestBody LeadDTO leadDTO){
        log.info(">>> [POST] A new lead received. Request: {}", leadDTO);
        var createdLead = this.leadService.createLead(leadDTO);

        log.info(">>> Response: {}", createdLead);
        return ResponseEntity.ok(createdLead);
    }
}
