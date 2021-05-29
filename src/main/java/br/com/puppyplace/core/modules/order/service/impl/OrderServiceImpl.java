package br.com.puppyplace.core.modules.order.service.impl;

import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.entities.Address;
import br.com.puppyplace.core.entities.Customer;
import br.com.puppyplace.core.entities.Order;
import br.com.puppyplace.core.entities.ProductOrder;
import br.com.puppyplace.core.modules.address.AddressRepository;
import br.com.puppyplace.core.modules.customer.CustomerRepository;
import br.com.puppyplace.core.modules.order.OrderRepository;
import br.com.puppyplace.core.modules.order.dto.OrderAddressDTO;
import br.com.puppyplace.core.modules.order.dto.OrderCustomerDTO;
import br.com.puppyplace.core.modules.order.dto.OrderDTO;
import br.com.puppyplace.core.modules.order.dto.ProductOrderDTO;
import br.com.puppyplace.core.modules.order.service.OrderService;
import br.com.puppyplace.core.modules.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        var order = modelMapper.map(orderDTO, Order.class);
        order.setCustomer(validCustomer(orderDTO));
        order.setAddress(validAddress(orderDTO));
        List<ProductOrder> productOrders = orderDTO.getProductOrderDTOS()
                .stream()
                .map(this::validProduct)
                .collect(Collectors.toList());

        productOrders.forEach(s->{
            order.getProductOrders().add(s);
            s.setOrder(order);
        });

        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        return orderRepository.save(order);
    }

    @Override
    public ProductOrder validProduct(ProductOrderDTO productOrderDTO) {
        var product = productRepository.findById(productOrderDTO.getProductId())
                .orElseThrow(() -> {
                    log.error(">>> Product not found with ID {}", productOrderDTO.getProductId());
                    throw new ResourceNotFoundException("No Product found with ID " + productOrderDTO.getProductId());
                });

        var productOrder = new ProductOrder(productOrderDTO);
        productOrder.setProduct(product);
        productOrder.setCreatedAt(new Date());
        productOrder.setUpdatedAt(new Date());
        return productOrder;
    }

    @Override
    public Address validAddress(OrderDTO orderDTO) {
        return addressRepository.findById(orderDTO.getAddressId())
                .orElseThrow(() -> {
                    log.error(">>> Address not found with ID {}", orderDTO.getAddressId()+ " for Customer ID " + orderDTO.getCustomerId());
                    throw new ResourceNotFoundException("No Address found with ID " + orderDTO.getAddressId() + " for Customer ID " + orderDTO.getCustomerId());
                });
    }

    @Override
    public Customer validCustomer(OrderDTO orderDTO) {
        return customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> {
                    log.error(">>> Customer not found with ID {}", orderDTO.getCustomerId());
                    throw new ResourceNotFoundException("No Customer found with ID " + orderDTO.getCustomerId());
                });
    }

    @Override
    public OrderDTO findId(UUID id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(">>> Order not found with ID {}", id);
                    throw new ResourceNotFoundException("No Order found with ID " + id);
                });
        return convertToOrderDTO(order);
    }

    @Override
    public OrderDTO create(OrderDTO order) {
        return convertToOrderDTO(createOrder(order));
    }

    @Override
    public OrderDTO convertToOrderDTO(Order order){

        var orderDTO = modelMapper.map(order, OrderDTO.class);
        orderDTO.setCustomer(modelMapper.map(order.getCustomer(), OrderCustomerDTO.class));
        orderDTO.setAddress(modelMapper.map(order.getAddress(), OrderAddressDTO.class));

        order.getProductOrders().forEach(s->
                orderDTO.getProductOrderDTOS().add(modelMapper.map(s, ProductOrderDTO.class))
        );

        return orderDTO;
    }

}
