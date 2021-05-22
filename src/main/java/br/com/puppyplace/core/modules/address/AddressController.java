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
            @PathVariable("customer_id") UUID customerID, @Valid @RequestBody AddressDTO addressDTO
    ){
        log.info(">>> [POST] A new address received. RequestBody: {}", addressDTO);
        var addressDTOCreated = addressService.create(customerID, addressDTO);
        log.info(">>> Response: {}", addressDTOCreated);

        return ResponseEntity.status(HttpStatus.CREATED).body(addressDTOCreated);
    }

    @PutMapping
    @RequestMapping("/customer/{customer_id}/address/{id}")
    public ResponseEntity<AddressDTO> update(
            @PathVariable("customer_id") UUID customerID,
            @PathVariable("id") UUID addressID,
            @Valid @RequestBody AddressDTO addressDTO) {
        log.info(">>> [PUT] A new request to update address with ID {}", addressID);
        var addressDTOUpdated = addressService.update(customerID, addressID, addressDTO);
        log.info(">>> Response: {}", addressDTOUpdated);

        return ResponseEntity.ok(addressDTOUpdated);
    }
}
