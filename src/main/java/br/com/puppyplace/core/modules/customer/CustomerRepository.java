package br.com.puppyplace.core.modules.customer;

import br.com.puppyplace.core.entities.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {
    Optional<Customer> findByDocument(String document);
}
