package br.com.puppyplace.core.modules.order.service;

import br.com.puppyplace.core.entities.Address;
import br.com.puppyplace.core.entities.Customer;
import br.com.puppyplace.core.entities.Order;
import br.com.puppyplace.core.entities.ProductOrder;
import br.com.puppyplace.core.modules.order.dto.OrderDTO;
import br.com.puppyplace.core.modules.order.dto.ProductOrderDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<OrderDTO> getOrdersByCustomer(UUID id);
    OrderDTO create(OrderDTO order);
    OrderDTO convertToOrderDTO(Order order);
    OrderDTO getOrderId(UUID id);
    Order createOrder(OrderDTO order);
    Customer validCustomer(OrderDTO orderDTO);
    Address validAddress(OrderDTO orderDTO);
    ProductOrder validProduct(ProductOrderDTO productOrderDTO);
}
