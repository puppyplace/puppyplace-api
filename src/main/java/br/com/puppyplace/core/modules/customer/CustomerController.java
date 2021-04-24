package br.com.puppyplace.core.modules.customer;

import br.com.puppyplace.core.entities.Customer;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import br.com.puppyplace.core.modules.customer.dto.CustomerDTO;
@RestController
@RequestMapping("/customer")
@Validated
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> create(@Valid @RequestBody CustomerDTO customerDTO){
        log.info(">>> [POST] A new customer received. RequestBody: {}", customerDTO);
        var customer = customerService.create(customerDTO);
        log.info(">>> Response: {}", customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
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
}
