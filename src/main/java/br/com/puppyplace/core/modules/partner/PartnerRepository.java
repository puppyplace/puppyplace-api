package br.com.puppyplace.core.modules.partner;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.puppyplace.core.entities.Partner;

public interface PartnerRepository extends CrudRepository<Partner, UUID> {
    
}
