package br.com.puppyplace.core.modules.variant.service;

import br.com.puppyplace.core.modules.variant.dto.VariantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface VariantService {
         VariantDTO create(UUID productID, VariantDTO variantDTO);
         VariantDTO update(UUID productID, UUID id, VariantDTO variantDTO);
         void delete(UUID productID, UUID variantID);
         VariantDTO get(UUID productID, UUID variantID);
         Page<VariantDTO> list(Pageable pageable, UUID productID);
}
