package br.com.puppyplace.core.modules.variant.service.impl;

import br.com.puppyplace.core.entities.Variant;
import br.com.puppyplace.core.modules.variant.dto.VariantDTO;
import br.com.puppyplace.core.modules.variant.service.BuildDTOFromVariantEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BuildDTOFromVariantEntityImpl  implements BuildDTOFromVariantEntity {

    private final ModelMapper mapper;

    @Override
    public VariantDTO execute(Variant variant) {
        log.info(">>> Building VariantDTO from Variant entity");

        var variantDTO = mapper.map(variant, VariantDTO.class);
        variantDTO.setPricePromotional(variant.getPromotionalPrice());

        log.info(">>> Done");

        return variantDTO;
    }
}
