package br.com.puppyplace.core.modules.order;

import br.com.puppyplace.core.modules.order.dto.OrderDTO;
import br.com.puppyplace.core.modules.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/order")
@Validated
@Slf4j
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/create")
    public ResponseEntity<Optional<OrderDTO>> createOrder(@Valid @RequestBody OrderDTO order){
        log.info(">>> [POST] A new Order to create received. RequestBody: {}", order);
        var orderDTO = Optional.ofNullable(order)
                .map(orderService::createOrder)
                .map(orderService::convertToOrderDTO)
                ;
        log.info(">>> Response: {}", orderDTO);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<OrderDTO>> get(@PathVariable("id") UUID id){
        log.info(">>> [GET] A new request to get order with ID {}", id);
        var orderDTO =
                Optional.of(id)
                .map(s -> orderService.findId(id))
                .map(orderService::convertToOrderDTO);
        log.info(">>> Response: {}", orderDTO);
        return ResponseEntity.ok(orderDTO);
    }
}