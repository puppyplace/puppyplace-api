package br.com.puppyplace.core.modules.variant.service;

import br.com.puppyplace.core.entities.Variant;
import br.com.puppyplace.core.modules.variant.dto.VariantDTO;

public interface BuildDTOFromVariantEntity {
    VariantDTO execute(Variant variant);
}
