package br.com.puppyplace.core.modules.order;

import br.com.puppyplace.core.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, UUID> {
    Page<Order> findByCustomerId(UUID id, Pageable pageable);
}
