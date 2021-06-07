package br.com.puppyplace.core.modules.customer;

import br.com.puppyplace.core.modules.customer.dto.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;
@RestController
@RequestMapping("/customer")
@Validated
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@Valid @RequestBody CustomerDTO customerDTO){
        var customer = customerService.create(customerDTO);
        log.info(">>> Customer created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@Valid @RequestBody CustomerDTO customerDTO, @PathVariable("id") UUID id){
        log.info(">>> [PUT] A new request to get product with ID {}", id);
        var customerDTOUpdated = customerService.update(customerDTO, id);
        log.info(">>> Response: {}", customerDTOUpdated);

        return ResponseEntity.ok(customerDTOUpdated);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id){
        log.info(">>> [DELETE] A new request to delete customer with ID {}", id);
        customerService.delete(id);
        log.info(">>> Customer deleted! No response.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> get(@PathVariable("id") UUID customerID){
        log.info(">>> [GET] A new request to get customer with ID {}", customerID);
        var customerDTO = customerService.get(customerID);
        log.info(">>> Response: {}", customerDTO);
        return ResponseEntity.ok(customerDTO);
    }
}
