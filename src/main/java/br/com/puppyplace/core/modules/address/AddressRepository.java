package br.com.puppyplace.core.modules.address;

import br.com.puppyplace.core.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends CrudRepository<Address, UUID> {
//    List<Address> findAddressesByCustomerId(UUID customerID);
    Page<Address> findAllByCustomerID(UUID customerID);
}

