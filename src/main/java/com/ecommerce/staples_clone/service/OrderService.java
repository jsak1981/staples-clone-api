package com.ecommerce.staples_clone.service;

import com.ecommerce.staples_clone.dto.OrderRequestDTO;
import com.ecommerce.staples_clone.dto.OrderUpdateDTO;
import com.ecommerce.staples_clone.exception.ResourceNotFoundException;
import com.ecommerce.staples_clone.model.*;
import com.ecommerce.staples_clone.repository.CustomerRepository;
import com.ecommerce.staples_clone.repository.OrderRepository;
import com.ecommerce.staples_clone.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
  private static final Logger log = LoggerFactory.getLogger(OrderService.class);

  private final OrderRepository orderRepository;
  private final CustomerRepository customerRepository;
  private final ProductRepository productRepository;

  @Autowired
  public OrderService(OrderRepository o, CustomerRepository c, ProductRepository p) {
    this.orderRepository = o;
    this.customerRepository = c;
    this.productRepository = p;
  }

  @Transactional(readOnly = true)
  public List<Order> getAllOrders() {
    log.debug("Fetching al Orders from the database");
    return orderRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Order getOrderById(Long orderId) {
    log.debug("Querying database for order with id: {}", orderId);
    return orderRepository
        .findById(orderId)
        .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
  }

  @Transactional
  public Order createOrder(OrderRequestDTO request) {
    Customer existingCustomer =
        customerRepository
            .findById(request.getCustomerId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Cannot create order: Product not found with id: "
                            + request.getCustomerId()));
    Order order = new Order();
    order.setCustomer(existingCustomer);
    order.setStatus("PENDING");

    BigDecimal totalAmount = BigDecimal.ZERO;
    for (OrderRequestDTO.OrderItemDTO itemDto : request.getItems()) {
      Product product =
          productRepository
              .findById(itemDto.getProductId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Cannot create order: Product not found with id: "
                              + itemDto.getProductId()));
      OrderItem orderItem = new OrderItem();
      orderItem.setProduct(product);
      orderItem.setQuantity(itemDto.getQuantity());
      orderItem.setPriceAtPurchase(product.getPrice());
      order.addItem(orderItem);
      totalAmount =
          totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
    }
    order.setTotalAmount(totalAmount);
    return orderRepository.save(order);
  }

  @Transactional
  public Order updateOrderStatus(Long id, OrderUpdateDTO orderDto) {

    Order exsitingOrder = getOrderById(id);
    exsitingOrder.setStatus(orderDto.getStatus());
    return orderRepository.save(exsitingOrder);
  }

  @Transactional
  public void deleteOrder(Long id) {
    if (!orderRepository.existsById(id)) {
      throw new ResourceNotFoundException("Order not found with id: " + id);
    }
    orderRepository.deleteById(id);
  }
}
