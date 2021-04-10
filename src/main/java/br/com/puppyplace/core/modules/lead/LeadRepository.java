package br.com.puppyplace.core.modules.lead;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.puppyplace.core.entities.Lead;

public interface LeadRepository extends CrudRepository<Lead, Long> {
	
	public Optional<Lead> findByEmail(String email);
	
}