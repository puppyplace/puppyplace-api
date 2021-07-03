package br.com.puppyplace.core.modules.address;

import br.com.puppyplace.core.modules.address.dto.AddressDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@Validated
@Slf4j
@CrossOrigin(origins = "${base-frontend-url}", maxAge = 3600)
public class AddressController {
    @Autowired
    private  AddressService addressService;

    @PostMapping("/customer/{customer_id}/address")
    public ResponseEntity<AddressDTO> create(
            @PathVariable("customer_id") UUID customerID, @Valid @RequestBody AddressDTO addressDTO
    ){
        log.info(">>> [POST] A new address received. RequestBody: {}", addressDTO);
        var addressDTOCreated = addressService.create(customerID, addressDTO);
        log.info(">>> Response: {}", addressDTOCreated);

        return ResponseEntity.status(HttpStatus.CREATED).body(addressDTOCreated);
    }

    @PutMapping("/customer/{customer_id}/address/{id}")
    public ResponseEntity<AddressDTO> update(
            @PathVariable("customer_id") UUID customerID,
            @PathVariable("id") UUID addressID,
            @Valid @RequestBody AddressDTO addressDTO) {
        log.info(">>> [PUT] A new request to update address with ID {}", addressID);
        var addressDTOUpdated = addressService.update(customerID, addressID, addressDTO);
        log.info(">>> Response: {}", addressDTOUpdated);

        return ResponseEntity.ok(addressDTOUpdated);
    }

    @PatchMapping("/customer/{customer_id}/address/{id}")
    public ResponseEntity<UUID> makeMain(
            @PathVariable("customer_id") UUID customerID,
            @PathVariable("id") UUID addressID) {
        log.info(">>> [PUT] A new request to update address with ID {}", addressID);
       var addressMainID= addressService.makeMain(customerID, addressID);
        log.info(">>> Response: {}", addressMainID);

        return ResponseEntity.ok(addressMainID);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/customer/{customer_id}/address/{id}")
    public ResponseEntity<UUID> delete(@PathVariable("customer_id") UUID customerID, @PathVariable("id") UUID addressID){
        log.info(">>> [DELETE] A new request to delete address with ID {}", addressID);
        var addressIDRemoved = addressService.delete(customerID, addressID);
        log.info(">>> Address deleted! No response.");

        return ResponseEntity.ok(addressIDRemoved);
    }

    @GetMapping("/customer/{customer_id}/address/{id}")
    public ResponseEntity<AddressDTO> get(@PathVariable("customer_id") UUID customerID, @PathVariable("id") UUID addressID){
        log.info(">>> [GET] A new request to get address with ID {}", addressID);
        var addressDTO = addressService.get(customerID, addressID);
        log.info(">>> Response: {}", addressDTO);

        return ResponseEntity.ok(addressDTO);
    }

    @GetMapping("/customer/{customer_id}/address")
    public ResponseEntity<Page<AddressDTO>> list(
            @PathVariable("customer_id") UUID customerID,
            @Valid @RequestParam(value = "size", defaultValue = "10") @Min(1) Integer size,
            @Valid @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page){
        var pageable = PageRequest.of(page, size);

        log.info(">>> [GET] A new request to get list of products in page {} with size {}", page, size);
        var pageOfAddressDTO =
                addressService.list(pageable, customerID);
        log.info(">>> Response: {}", pageOfAddressDTO);

        return ResponseEntity.ok(pageOfAddressDTO);
    }
}
