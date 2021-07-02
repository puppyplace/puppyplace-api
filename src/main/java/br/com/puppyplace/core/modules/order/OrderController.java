package br.com.puppyplace.core.modules.order;

import br.com.puppyplace.core.modules.order.dto.OrderDTO;
import br.com.puppyplace.core.modules.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/order")
@Validated
@Slf4j
@AllArgsConstructor
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Optional<OrderDTO>> createOrder(@Valid @RequestBody OrderDTO order){
        log.info(">>> [POST] A new Order to create received. RequestBody: {}", order);
        var orderDTO =
                Optional.ofNullable(order)
                .map(orderService::create);
        log.info(">>> Response createOder: {}", orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable("id") UUID id){
        log.info(">>> [GET] A new request to get order with ID {}", id);
        var orderDTO = orderService.getOrderId(id);
        log.info(">>> Response getOrder: {}", orderDTO);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping(value = "/customer/{id}")
    public ResponseEntity<Page<OrderDTO>> listOrdersByCustomer(
            @PathVariable("id") UUID id,
            @Valid @RequestParam(value = "size", defaultValue = "10") @Min(1) Integer size,
            @Valid @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page
    ){
        var pageable = PageRequest.of(page, size);
        log.info(">>> [GET] A new request to get order with ID {}", id);
        var orderDTO = orderService.getOrdersByCustomer(id, pageable);
        log.info(">>> Response OrdersByCustom: {}", orderDTO);
        return ResponseEntity.ok(orderDTO);
    }
}
