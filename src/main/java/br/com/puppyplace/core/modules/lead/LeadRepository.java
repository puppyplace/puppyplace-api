package br.com.puppyplace.core.modules.lead;

import br.com.puppyplace.core.entities.Lead;
import org.springframework.data.repository.CrudRepository;

public interface LeadRepository extends CrudRepository<Lead, Long> {
}