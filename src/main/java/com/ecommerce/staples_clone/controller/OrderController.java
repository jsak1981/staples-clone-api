package com.ecommerce.staples_clone.controller;

import com.ecommerce.staples_clone.dto.OrderItemResponseDTO;
import com.ecommerce.staples_clone.dto.OrderRequestDTO;
import com.ecommerce.staples_clone.dto.OrderResponseDTO;
import com.ecommerce.staples_clone.dto.OrderUpdateDTO;
import com.ecommerce.staples_clone.model.*;
import com.ecommerce.staples_clone.service.OrderService;
import java.util.*;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private static final Logger log = LoggerFactory.getLogger(OrderController.class);

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderDto) {
    log.info("Received request to create an order for customerId: {}", orderDto.getCustomerId());
    log.debug("Order request payload: {}", orderDto);

    Order createdOrder = orderService.createOrder(orderDto);
    return new ResponseEntity<>(convertToDto(createdOrder), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
    log.info("Received GET request for order with id: {}", id);

    Order exsitingOrder = orderService.getOrderById(id);
    return new ResponseEntity<>(convertToDto(exsitingOrder), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
    log.info("Received GET request for all orders");
    List<Order> orders = orderService.getAllOrders();

    List<OrderResponseDTO> orderDtos =
        orders.stream().map(this::convertToDto).collect(Collectors.toList());

    return ResponseEntity.ok(orderDtos);
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> updateOrderStatus(
      @PathVariable Long id, @RequestBody OrderUpdateDTO updateDTO) {
    log.info("Received PUT request to update status for order id: {}", id);
    Order updatedOrder = orderService.updateOrderStatus(id, updateDTO);
    return ResponseEntity.ok(convertToDto(updatedOrder));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    log.info("Received DELETE request for order id: {}", id);
    orderService.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }

  // --- Private Helper Method for DTO Conversion ---
  private OrderResponseDTO convertToDto(Order order) {
    OrderResponseDTO orderDto = new OrderResponseDTO();
    orderDto.setOrderId(order.getOrderId());
    orderDto.setOrderDate(order.getOrderDate());
    orderDto.setStatus(order.getStatus());
    orderDto.setTotalAmount(order.getTotalAmount());

    Set<OrderItemResponseDTO> itemDtos =
        order.getItems().stream()
            .map(
                item -> {
                  OrderItemResponseDTO itemDto = new OrderItemResponseDTO();
                  itemDto.setProductId(item.getProduct().getProductId());
                  itemDto.setProductName(item.getProduct().getName());
                  itemDto.setQuantity(item.getQuantity());
                  itemDto.setPriceAtPurchase(item.getPriceAtPurchase());
                  return itemDto;
                })
            .collect(Collectors.toSet());
    orderDto.setItems(itemDtos);

    return orderDto;
  }
}
