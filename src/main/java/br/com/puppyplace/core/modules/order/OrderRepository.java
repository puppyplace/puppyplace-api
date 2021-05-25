package br.com.puppyplace.core.modules.order;

import br.com.puppyplace.core.entities.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID> {
}
