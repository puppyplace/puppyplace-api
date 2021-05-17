package br.com.puppyplace.core.modules.address;

import br.com.puppyplace.core.modules.customer.dto.AddressDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@Validated
@Slf4j
public class AddressController {
    @Autowired
    private  AddressService addressService;

    @PostMapping
    @RequestMapping("/customer/{customer_id}/address")
    public ResponseEntity<AddressDTO> create(
            @PathVariable("customer_id") UUID customer_id, @Valid @RequestBody AddressDTO addressDTO
    ){
        log.info(">>> [POST] A new address received. RequestBody: {}", addressDTO);
        var address = addressService.create(customer_id, addressDTO);
        log.info(">>> Response: {}", address);

        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }
}
