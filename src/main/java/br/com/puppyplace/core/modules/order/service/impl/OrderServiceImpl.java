package br.com.puppyplace.core.modules.order.service.impl;

import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.entities.Order;
import br.com.puppyplace.core.modules.order.OrderRepository;
import br.com.puppyplace.core.modules.order.dto.OrderDTO;
import br.com.puppyplace.core.modules.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(OrderDTO order) {
        return orderRepository.save(modelMapper.map(order, Order.class));
    }

    @Override
    public Order findId(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(">>> Order not found with ID {}", id);
                    throw new ResourceNotFoundException("No Order found with ID " + id.toString());
                });
    }

    @Override
    public OrderDTO convertToOrderDTO(Order order){
        return modelMapper.map(order, OrderDTO.class);
    }

}
