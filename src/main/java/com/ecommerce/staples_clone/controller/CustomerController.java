package com.ecommerce.staples_clone.controller;

import com.ecommerce.staples_clone.dto.CustomerRequestDTO;
import com.ecommerce.staples_clone.dto.CustomerResponseDTO;
import com.ecommerce.staples_clone.dto.OrderItemResponseDTO;
import com.ecommerce.staples_clone.dto.OrderResponseDTO;
import com.ecommerce.staples_clone.model.Customer;
import com.ecommerce.staples_clone.model.Order;
import com.ecommerce.staples_clone.service.CustomerService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
  private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

  private final CustomerService customerService;

  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping
  public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
    log.info("Received GET request for all customers");
    List<Customer> customers = customerService.getAllCustomers();
    List<CustomerResponseDTO> customerDTOs = customers.stream().map(this::convertToDto).toList();
    return ResponseEntity.ok(customerDTOs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
    log.info("Received GET request for customer with id: {}", id);
    return customerService
        .getCustomerById(id)
        .map(this::convertToDto) // Use the new mapping method
        .map(ResponseEntity::ok)
        .orElseGet(
            () -> {
              log.warn("Customer not found with id: {}", id);
              return ResponseEntity.notFound().build();
            });
  }

  @GetMapping("/{id}/orders")
  public ResponseEntity<Set<OrderResponseDTO>> getCustomerOrders(@PathVariable Long id) {
    log.info("Received GET request for orders for customer with id: {}", id);
    return customerService
        .getCustomerById(id)
        .map(
            customer -> {
              log.info("Found {} orders for customer with id: {}", customer.getOrders().size(), id);
              Set<OrderResponseDTO> orderDtos =
                  customer.getOrders().stream()
                      .map(this::convertOrderToDto)
                      .collect(Collectors.toSet());
              return ResponseEntity.ok(orderDtos);
            })
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<CustomerResponseDTO> createCustomer(
      @RequestBody CustomerRequestDTO cusDTO) {
    log.info("Received POST request to create new customer");
    Customer createCustomer = customerService.createCustomer(cusDTO);
    CustomerResponseDTO customerResponseDTO = convertToDto(createCustomer);
    return new ResponseEntity<>(customerResponseDTO, HttpStatus.CREATED);
  }

  // ***************  Helper methods *****************//
  private CustomerResponseDTO convertToDto(Customer customer) {
    CustomerResponseDTO cusDTO = new CustomerResponseDTO();
    cusDTO.setCustomerId(customer.getCustomerId());
    cusDTO.setFirstName(customer.getFirstName());
    cusDTO.setLastName(customer.getLastName());
    cusDTO.setEmail(customer.getEmail());

    Set<OrderResponseDTO> orderDTOs =
        customer.getOrders().stream().map(this::convertOrderToDto).collect(Collectors.toSet());
    cusDTO.setOrders(orderDTOs);
    return cusDTO;
  }

  private OrderResponseDTO convertOrderToDto(Order order) {
    OrderResponseDTO orderDTO = new OrderResponseDTO();
    orderDTO.setOrderId(order.getOrderId());
    orderDTO.setOrderDate(order.getOrderDate());
    orderDTO.setStatus(order.getStatus());
    orderDTO.setTotalAmount(order.getTotalAmount());

    Set<OrderItemResponseDTO> itemDTOs =
        order.getItems().stream()
            .map(
                item -> {
                  OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
                  itemDTO.setProductId(item.getProduct().getProductId());
                  itemDTO.setProductName(item.getProduct().getName());
                  itemDTO.setQuantity(item.getQuantity());
                  itemDTO.setPriceAtPurchase(item.getPriceAtPurchase());
                  return itemDTO;
                })
            .collect(Collectors.toSet());
    orderDTO.setItems(itemDTOs);
    return orderDTO;
  }
}
