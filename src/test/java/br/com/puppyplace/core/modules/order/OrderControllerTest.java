package br.com.puppyplace.core.modules.order;

import br.com.puppyplace.core.entities.Order;
import br.com.puppyplace.core.modules.order.builders.OrderBuilder;
import br.com.puppyplace.core.modules.order.dto.OrderDTO;
import br.com.puppyplace.core.modules.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc httpRequest;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    private Order orderMock;

    private OrderDTO orderDTOMock;

    private static EasyRandom easyRandom = new EasyRandom();

    @BeforeEach
    void setUp() {
        Pair<Order, OrderDTO> pairMock = OrderBuilder.completeOrder().getPairMock();
        this.orderMock = pairMock.getLeft();
        this.orderDTOMock = pairMock.getRight();
    }

    @Test
    void shouldReturnSuccess_whenCreateOrder() throws Exception {
        // given
        when(orderService.create(any(OrderDTO.class))).thenReturn(orderDTOMock);

        // When
        httpRequest
                .perform(
                        post("/order")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(orderDTOMock)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        // then
        verify(orderService, times(1)).create(orderDTOMock);
    }

    @Test
    void shouldReturnSuccess_whenFindIdOrder() throws Exception {
        // given
        when(orderService.getOrderId(any(UUID.class))).thenReturn(orderDTOMock);

        // When Then
        httpRequest
                .perform(
                        get("/order/{id}", orderMock.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(orderDTOMock.getId().toString()));

        verify(orderService, times(1)).getOrderId(orderMock.getId());
    }

}