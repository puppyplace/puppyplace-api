package br.com.puppyplace.core.modules.order.service;

import br.com.puppyplace.core.entities.Order;
import br.com.puppyplace.core.modules.order.dto.OrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public interface OrderService {

    public Order createOrder(OrderDTO order);
    public OrderDTO convertToOrderDTO(Order order);
    public Order findId(UUID id);
}
