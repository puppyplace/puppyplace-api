package br.com.puppyplace.core.modules.variant.service.impl;

import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.entities.Variant;
import br.com.puppyplace.core.modules.variant.VariantRepository;
import br.com.puppyplace.core.modules.variant.service.BuildDTOFromVariantEntity;
import br.com.puppyplace.core.modules.variant.service.BuildVariantFromDTO;
import br.com.puppyplace.core.modules.variant.service.VariantService;
import br.com.puppyplace.core.modules.variant.dto.VariantDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class VariantServiceImpl implements VariantService {


    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private BuildVariantFromDTO buildVariantFromDTO;

    @Autowired
    private BuildDTOFromVariantEntity buildDTOFromVariantEntity;

    @Override
    public VariantDTO create(UUID productID, VariantDTO variantDTO) {
        log.info(">>> Starting insert entity");

        var variant = buildVariantFromDTO.execute(productID, variantDTO);
        var insertedVariant = variantRepository.save(variant);

        log.info(">>> Entity updated!");

        return buildDTOFromVariantEntity.execute(insertedVariant);
    }

    @Override
    public VariantDTO update(UUID productID, UUID variantID, VariantDTO variantDTO) {
            log.info(">>> Starting update entity");

            var foundVariant = this.findByIdAndProductId(productID, variantID);
            var variant = buildVariantFromDTO.execute(productID, variantDTO);

            variant.setUpdatedAt(new Date());
            variant.setCreatedAt(foundVariant.getCreatedAt());
            var updatedVariant = variantRepository.save(variant);

            log.info(">>> Entity updated!");

            return buildDTOFromVariantEntity.execute(updatedVariant);

    }

    @Override
    public void delete(UUID productID, UUID variantID) {
        log.info(">>> Get Variant to delete.");

        var variant = this.findByIdAndProductId(productID, variantID);
        variantRepository.delete(variant);

        log.info(">>> Entity deleted!");
    }

    @Override
    public VariantDTO get(UUID productID, UUID variantID) {
        log.info(">>> Get Variant");

        var variant = this.findByIdAndProductId(productID, variantID);

        return buildDTOFromVariantEntity.execute(variant);
    }

    @Override
    public Page<VariantDTO> list(Pageable pageable, UUID productID) {
        log.info(">>> Searching variants list from database");

        var pageOfVariant = variantRepository.findByProductId(productID, pageable);
        var pageOfVariantDTO = pageOfVariant.map(variant -> buildDTOFromVariantEntity.execute(variant));

        log.info(">>> Done");
        return pageOfVariantDTO;
    }


    private Variant findByIdAndProductId(UUID productID, UUID variantID) {
        log.info(">>> Starting find Variant with variantID {} and productID {}", variantID, productID);

        return variantRepository.findByProductIdAndId(productID, variantID).orElseThrow(() -> {
            log.error(">>> Variant not found with variantID {} and productID {}", variantID, productID);
            throw new ResourceNotFoundException("No Variant found with ID " + variantID + "and productID " + productID);
        });
    }
}
