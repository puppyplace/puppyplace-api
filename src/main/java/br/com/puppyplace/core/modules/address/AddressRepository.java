package br.com.puppyplace.core.modules.address;

import br.com.puppyplace.core.entities.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AddressRepository extends CrudRepository<Address, UUID> {}
