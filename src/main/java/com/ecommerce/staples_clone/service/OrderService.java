package com.ecommerce.staples_clone.service;

import com.ecommerce.staples_clone.dto.OrderRequestDTO;
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
  public Optional<Order> getOrderById(Long orderId) {
    log.debug("Querying database for order with id: {}", orderId);
    return orderRepository.findById(orderId);
  }

  /**
   * @param request
   * @return
   */
  @Transactional
  public Order createOrder(OrderRequestDTO request) {
    Customer customer =
        customerRepository
            .findById(request.getCustomerId())
            .orElseThrow(() -> new RuntimeException("Customer not found"));

    Order order = new Order();
    order.setCustomer(customer);
    order.setStatus("PENDING");

    Set<OrderItem> orderItems = new HashSet<>();
    BigDecimal totalAmount = BigDecimal.ZERO;

    for (OrderRequestDTO.OrderItemDTO itemDto : request.getItems()) {
      Product product =
          productRepository
              .findById(itemDto.getProductId())
              .orElseThrow(() -> new RuntimeException("Product not found"));

      OrderItem orderItem = new OrderItem();
      orderItem.setOrder(order);
      orderItem.setProduct(product);
      orderItem.setQuantity(itemDto.getQuantity());
      orderItem.setPriceAtPurchase(product.getPrice());
      orderItems.add(orderItem);

      totalAmount =
          totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
    }
    order.setItems(orderItems);
    order.setTotalAmount(totalAmount);
    return orderRepository.save(order);
  }

  @Transactional
  public Optional<Order> updateOrderStatus(Long id, String status) {
    Optional<Order> updatedOrder =
        orderRepository
            .findById(id)
            .map(
                existingOrder -> {
                  existingOrder.setStatus(status);
                  return orderRepository.save(existingOrder);
                });
    return updatedOrder;
  }

  @Transactional
  public boolean deleteOrder(Long id) {
    if (orderRepository.existsById(id)) {
      orderRepository.deleteById(id);
      log.info("Successfully deleted order with id: {}", id);
      return true;
    }
    log.warn("Could not delete. Order not found with id: {}", id);
    return false;
  }
}
