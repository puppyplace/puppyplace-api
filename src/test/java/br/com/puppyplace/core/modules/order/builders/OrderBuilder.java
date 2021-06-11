package br.com.puppyplace.core.modules.order.builders;

import br.com.puppyplace.core.entities.*;
import br.com.puppyplace.core.modules.order.dto.OrderAddressDTO;
import br.com.puppyplace.core.modules.order.dto.OrderCustomerDTO;
import br.com.puppyplace.core.modules.order.dto.OrderDTO;
import br.com.puppyplace.core.modules.order.dto.ProductOrderDTO;
import br.com.puppyplace.core.modules.order.service.OrderService;
import org.apache.commons.lang3.tuple.Pair;
import org.jeasy.random.EasyRandom;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderBuilder {

    private Order order;
    private Customer customer;
    private Product product;
    private static EasyRandom easyRandom = new EasyRandom();
    private static ModelMapper modelMapper = new ModelMapper();

    OrderBuilder(){}

    public static OrderBuilder completeOrder(){

        OrderBuilder builder = new OrderBuilder();

        builder.order = easyRandom.nextObject(Order.class);
        builder.order.getProductOrders().add(easyRandom.nextObject(ProductOrder.class));

        builder.product = easyRandom.nextObject(Product.class);
        builder.order.getProductOrders().get(0).setProduct(builder.product);

        builder.customer = easyRandom.nextObject(Customer.class);
        builder.customer.getAddresses().add(easyRandom.nextObject((Address.class)));

        builder.order.setCustomer(builder.customer);
        builder.order.setAddress(builder.customer.getAddresses().get(0));

        return builder;
    }

    public Order getMock(){
        return order;
    }

    public Pair<Order, OrderDTO> getPairMock(){
        OrderDTO orderDTO = convertToOrderDTO(order);
        return Pair.of(order,orderDTO);
    }

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
