package br.com.puppyplace.core.modules.lead;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.puppyplace.core.entities.Lead;

@Repository
public interface LeadRepository extends CrudRepository<Lead, UUID> {	
	public Optional<Lead> findByEmail(String email);	
}