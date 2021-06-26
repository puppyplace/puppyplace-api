package br.com.puppyplace.core.modules.variant;

import br.com.puppyplace.core.modules.variant.dto.VariantDTO;
import br.com.puppyplace.core.modules.variant.service.VariantService;
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
public class VariantController {

    @Autowired
    private VariantService variantService;

    @PostMapping("/product/{product_id}/variant")
    public ResponseEntity<VariantDTO> create(
            @PathVariable("product_id") UUID productID, @Valid @RequestBody VariantDTO variantDTO
    ){
        log.info(">>> [POST] A new product variant received. RequestBody: {}", variantDTO);
        var variantDTOCreated = variantService.create(productID, variantDTO);
        log.info(">>> Response: {}", variantDTOCreated);

        return ResponseEntity.status(HttpStatus.CREATED).body(variantDTOCreated);
    }

    @PutMapping("/product/{product_id}/variant/{id}")
    public ResponseEntity<VariantDTO> update(
            @PathVariable("product_id") UUID productID,
            @PathVariable("id") UUID variantID,
            @Valid @RequestBody VariantDTO variantDTO) {
        log.info(">>> [PUT] A new request to update product variant with ID {}", variantDTO);
        var variantDTOUpdated = variantService.update(productID, variantID, variantDTO);
        log.info(">>> Response: {}", variantDTOUpdated);

        return ResponseEntity.ok(variantDTOUpdated);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/product/{product_id}/variant/{id}")
    public void delete(@PathVariable("product_id") UUID productID, @PathVariable("id") UUID variantID) {
        log.info(">>> [DELETE] A new request to delete product variant with ID {}", variantID);
        variantService.delete(productID, variantID);
        log.info(">>> Variant deleted! No response.");
    }

    @GetMapping("/product/{product_id}/variant/{id}")
    public ResponseEntity<VariantDTO> get(@PathVariable("product_id") UUID productID, @PathVariable("id") UUID variantID){
        log.info(">>> [GET] A new request to get product variant with ID {}", variantID);
        var variantDTO = variantService.get(productID, variantID);
        log.info(">>> Response: {}", variantDTO);

        return ResponseEntity.ok(variantDTO);
    }

    @GetMapping("/product/{product_id}/variants")
    public ResponseEntity<Page<VariantDTO>> list(
            @PathVariable("product_id") UUID productID,
            @Valid @RequestParam(value = "size", defaultValue = "10") @Min(1) Integer size,
            @Valid @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page) {
        var pageable = PageRequest.of(page, size);

        log.info(">>> [GET] A new request to get list of product variants in page {} with size {}", page, size);
        var pageOfVarintDTO =
                variantService.list(pageable, productID);
        log.info(">>> Response: {}", pageOfVarintDTO);

        return ResponseEntity.ok(pageOfVarintDTO);
    }
}
