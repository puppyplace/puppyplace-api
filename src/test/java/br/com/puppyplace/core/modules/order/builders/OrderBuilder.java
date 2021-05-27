package br.com.puppyplace.core.modules.order.builders;

import br.com.puppyplace.core.entities.*;
import br.com.puppyplace.core.modules.order.dto.OrderDTO;
import br.com.puppyplace.core.modules.order.dto.ProductOrderDTO;
import org.jeasy.random.EasyRandom;

public class OrderBuilder {

    private Order order;
    private Customer customer;
    private Product product;
    private static EasyRandom easyRandom = new EasyRandom();

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
}
