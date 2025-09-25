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
    log.debug("Order request payload: {}", orderDto); // DEBUG for detailed info

    try {
      Order newOrder = orderService.createOrder(orderDto);
      OrderResponseDTO orderRespDto = convertToDto(newOrder);
      log.info("Successfully created order with id: {}", newOrder.getOrderId());
      return new ResponseEntity<>(orderRespDto, HttpStatus.CREATED);
    } catch (Exception e) {
      log.error("Error creating order for customerId: {}", orderDto.getCustomerId(), e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
    log.info("Received GET request for order with id: {}", id);
    return orderService
        .getOrderById(id)
        .map(
            order -> {
              log.info("Found order with id: {}", id);
              OrderResponseDTO respDto = convertToDto(order);
              return ResponseEntity.ok(respDto);
            })
        .orElseGet(
            () -> {
              log.warn("Order not found with id: {}", id);
              return ResponseEntity.notFound().build();
            });
  }

  @GetMapping
  public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
    log.info("Received GET request for all orders");
    List<Order> orders = orderService.getAllOrders();
    List<OrderResponseDTO> orderDtos =
        orders.stream().map(this::convertToDto).collect(Collectors.toList());
    return ResponseEntity.ok(orderDtos);
  }

  // NEW Method
  @PutMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> updateOrderStatus(
      @PathVariable Long id, @RequestBody OrderUpdateDTO updateDTO) {
    log.info("Received PUT request to update status for order id: {}", id);
    return orderService
        .updateOrderStatus(id, updateDTO.getStatus())
        .map(this::convertToDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    log.info("Received DELETE request for order id: {}", id);
    if (orderService.deleteOrder(id)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
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
