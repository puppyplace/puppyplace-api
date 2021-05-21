package br.com.puppyplace.core.modules.order;

import br.com.puppyplace.core.entities.Order;
import br.com.puppyplace.core.modules.order.dto.OrderDTO;
import br.com.puppyplace.core.modules.order.service.OrderService;
import br.com.puppyplace.core.modules.order.service.impl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/order")
@Validated
@Slf4j
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO order){
        log.info(">>> [POST] A new Order to create received. RequestBody: {}", order);
        var orderDTO = Optional.of(order)
                .map(s -> orderService.createOrder(s))
                .map(s -> orderService.convertToOrderDTO(s));
        log.info(">>> Response: {}", orderDTO);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> createOrder(@PathVariable("id") UUID id){
        log.info(">>> [GET] A new request to get order with ID {}", id);
        var orderDTO = Optional.of(id)
                .map(s -> orderService.findId(id))
                .map(s -> orderService.convertToOrderDTO(s));
        log.info(">>> Response: {}", orderDTO);
        return ResponseEntity.ok(orderDTO);
    }
}
