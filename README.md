# Staples Clone - E-commerce REST API

This repository contains the source code for a REST API that mimics the core functionalities of an e-commerce platform like Staples. It is built with Spring Boot and provides endpoints for managing products, customers, and orders.

---

## Tech Stack

- **Backend:** Java 17, Spring Boot 2.7.18 (with Spring Data JPA, Spring Web)
- **Database:** PostgreSQL 14
- **Build Tool:** Apache Maven
- **Logging:** SLF4J with Logback, including Correlation & Request IDs via MDC

---

## Project Setup

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/your-username/staples-clone-api.git](https://github.com/your-username/staples-clone-api.git)
    cd staples-clone-api
    ```

2.  **Configure the database:**
    - Open `src/main/resources/application.properties`.
    - Update the `spring.datasource.username` and `spring.datasource.password` with your local PostgreSQL credentials.

3.  **Run the application:**
    - You can run the `StaplesCloneApplication.java` main method directly from your IDE (IntelliJ IDEA).
    - The application will start on `http://localhost:8080`.

---

## API Endpoints

The API is organized into three main controllers:

### 1. Product Controller (`/api/products`)
- `GET /`: Fetches all products.
- `GET /{id}`: Fetches a single product by its ID.
- `POST /`: Creates a new product.
- `PUT /{id}`: Updates an existing product.
- `DELETE /{id}`: Deletes a product.

### 2. Customer Controller (`/api/customers`)
- `GET /`: Fetches all customers.
- `GET /{id}`: Fetches a single customer by ID.
- `GET /{id}/orders`: Fetches all orders for a specific customer.
- `POST /`: Creates a new customer.
- `PUT /{id}`: Updates an existing customer.
- `DELETE /{id}`: Deletes a customer.

### 3. Order Controller (`/api/orders`)
- `GET /`: Fetches all orders.
- `GET /{id}`: Fetches a single order by ID.
- `POST /`: Creates a new order.
- `PUT /{id}`: Updates an order's status.
- `DELETE /{id}`: Deletes an order.
