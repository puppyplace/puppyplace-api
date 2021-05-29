package br.com.puppyplace.core.modules.order.service.impl;

import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.entities.Order;
import br.com.puppyplace.core.modules.address.AddressRepository;
import br.com.puppyplace.core.modules.customer.CustomerRepository;
import br.com.puppyplace.core.modules.order.OrderRepository;
import br.com.puppyplace.core.modules.order.builders.OrderBuilder;
import br.com.puppyplace.core.modules.order.dto.OrderDTO;
import br.com.puppyplace.core.modules.order.dto.ProductOrderDTO;
import br.com.puppyplace.core.modules.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(SpringExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ProductRepository productRepository;

    private Order orderMock;

    private static EasyRandom easyRandom = new EasyRandom();

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(orderService, "modelMapper", new ModelMapper());
        this.orderMock = OrderBuilder.completeOrder().getMock();
    }

    @Test
    void shouldReturnSuccess_whenCreateOrder() {

        // Given
        when(customerRepository.findById(orderMock.getCustomer().getId()))
                .thenReturn(Optional.of(orderMock.getCustomer()));
        when(addressRepository.findById(orderMock.getAddress().getId()))
                .thenReturn(Optional.of(orderMock.getAddress()));
        when(productRepository.findById(orderMock.getProductOrders().get(0).getProduct().getId()))
                .thenReturn(Optional.of(orderMock.getProductOrders().get(0).getProduct()));
        when(orderRepository.save(any(Order.class))).thenReturn(orderMock);

        // When
        var order = orderService.createOrder(orderService.convertToOrderDTO(orderMock));

        // Then
        assertNotNull(order);
        assertEquals(order.getCustomer(), orderMock.getCustomer());
        assertEquals(order.getAddress(), orderMock.getAddress());
        assertEquals(order.getProductOrders().get(0).getProduct(), orderMock.getProductOrders().get(0).getProduct());

    }

    @Test
    void shouldReturnException_whenValidProduct() {

        //Given
        var productOrderDTO = easyRandom.nextObject(ProductOrderDTO.class);
        //When
        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                orderService.validProduct(productOrderDTO));
        //Then
        assertEquals("No Product found with ID " + productOrderDTO.getProductId(), exception.getMessage());
    }

    @Test
    void shouldReturnException_whenValidCustomer() {

        //Given
        var orderDTO = orderService.convertToOrderDTO(orderMock);
        //When
        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                orderService.validCustomer(orderDTO));
        //Then
        assertEquals("No Customer found with ID " + orderDTO.getCustomerId(), exception.getMessage());
    }

    @Test
    void shouldReturnException_whenValidAddress() {

        //Given
        var orderDTO = orderService.convertToOrderDTO(orderMock);
        //When
        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                orderService.validAddress(orderDTO));
        //Then
        assertEquals("No Address found with ID " + orderDTO.getAddressId() +
                " for Customer ID " + orderDTO.getCustomerId(), exception.getMessage());
    }

    @Test
    void shouldReturnSucess_whenFindOrder(){

        //Given
        when(orderRepository.findById(orderMock.getId()))
                .thenReturn(Optional.of(orderMock));
        //When
        OrderDTO order = orderService.findId(orderMock.getId());
        //Then
        assertNotNull(order);
        assertEquals(orderMock.getId(), order.getId());
    }

    @Test
    void shouldReturnException_whenFindOrder(){

        //Given
        UUID orderId = UUID.randomUUID();
        //When
        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                orderService.findId(orderId));
        //Then
        assertEquals("No Order found with ID "+ orderId, exception.getMessage());
    }
}