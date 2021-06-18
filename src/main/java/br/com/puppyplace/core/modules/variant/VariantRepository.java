package br.com.puppyplace.core.modules.variant;

import br.com.puppyplace.core.entities.Variant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface VariantRepository extends CrudRepository<Variant, UUID> {
    Page<Variant> findByProductId(UUID productID, Pageable pageable);
    Optional<Variant> findByProductIdAndId(UUID productID, UUID variantID);
}

