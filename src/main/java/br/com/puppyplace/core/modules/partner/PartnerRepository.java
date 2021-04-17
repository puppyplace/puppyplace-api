package br.com.puppyplace.core.modules.partner;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.puppyplace.core.entities.Partner;

public interface PartnerRepository extends JpaRepository<Partner, UUID> {
    
}
