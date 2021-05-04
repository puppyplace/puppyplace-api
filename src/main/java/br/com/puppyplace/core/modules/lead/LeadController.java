package br.com.puppyplace.core.modules.lead;

import br.com.puppyplace.core.modules.lead.dto.LeadDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/lead")
@Validated
@Slf4j
public class LeadController {

    @Autowired
    private LeadService leadService;

    @PostMapping
    @CrossOrigin(origins = "${base-frontend-url}", maxAge = 3600)
    public ResponseEntity<LeadDTO> create(@Valid @RequestBody LeadDTO leadDTO){
           
        log.info(">>> [POST] A new lead received. RequestBody: {}", leadDTO);
        leadDTO = this.leadService.create(leadDTO);
        log.info(">>> Response: {}", leadDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(leadDTO);
    }
    
}