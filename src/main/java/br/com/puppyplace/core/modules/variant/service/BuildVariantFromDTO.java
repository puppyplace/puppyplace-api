package br.com.puppyplace.core.modules.variant.service;

import br.com.puppyplace.core.entities.Variant;
import br.com.puppyplace.core.modules.variant.dto.VariantDTO;

import java.util.UUID;

public interface BuildVariantFromDTO {
    Variant execute(UUID productID, VariantDTO variantDTO);
}
