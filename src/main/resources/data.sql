-- =================================================================================================
-- Staples.com Clone - Development Database Seed Script
-- =================================================================================================
-- This script is automatically executed by Spring Boot on application startup after the schema
-- has been created or updated by Hibernate (due to ddl-auto=update). Its primary purpose is to
-- populate the database with a consistent and realistic set of data for local development,
-- automated testing, and QA environments. A rich and predictable dataset is invaluable for
-- efficient developer onboarding and for building a reliable CI/CD pipeline.
--
-- KEY CONCEPTS:
-- 1. Idempotency: The 'ON CONFLICT DO NOTHING' clause is a PostgreSQL-specific feature. It ensures
--    that if this script is run a second time against a database that already contains this data,
--    it will not fail with a "duplicate key" error. This makes the script "idempotent" (safe to run multiple times),
--    which is essential for a stable development workflow.
-- 2. Execution Order: The INSERT statements are ordered carefully to respect foreign key constraints.
--    Parent tables (like 'product_categories' and 'customers') must be populated before the child
--    tables that reference them ('products', 'orders', 'order_items'). This mimics the natural
--    data dependencies within the application.
-- 3. Sequence Synchronization: Because we are manually inserting records with specific primary key IDs,
--    we must manually reset PostgreSQL's auto-incrementing sequences at the end of the script. Failure
--    to do so would cause the application to throw a "duplicate key" error when it tries to create a new
--    record (e.g., a new customer with ID 5, which already exists). This is a non-negotiable step
--    for any manual seeding strategy that specifies primary keys.
-- 4. Development vs. Production: This script is for development environments only. For production, a
--    dedicated database migration tool like Flyway or Liquibase would be used to manage schema
--    evolution and static data seeding in a more robust, version-controlled manner.
-- =================================================================================================

-- Clean slate: To ensure a predictable starting state, especially during development, we first
-- wipe all data. Deletions must happen from child tables first to avoid foreign key constraint violations.
DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM products;
DELETE FROM customers;
DELETE FROM product_categories;


-- 1. Populate Product Categories (Static Lookup Data)
-- These are the foundational categories for all products. This data is static and rarely changes.
INSERT INTO product_categories (category_id, category_name, description) VALUES (1, 'Writing Instruments', 'Pens, pencils, markers, and highlighters for every need.') ON CONFLICT (category_id) DO NOTHING;
INSERT INTO product_categories (category_id, category_name, description) VALUES (2, 'Paper Products', 'Copy paper, notebooks, sticky notes, and business envelopes.') ON CONFLICT (category_id) DO NOTHING;
INSERT INTO product_categories (category_id, category_name, description) VALUES (3, 'Office Electronics', 'Printers, scanners, keyboards, and other essential accessories.') ON CONFLICT (category_id) DO NOTHING;
INSERT INTO product_categories (category_id, category_name, description) VALUES (4, 'Filing & Storage', 'Binders, file folders, and durable storage boxes for organization.') ON CONFLICT (category_id) DO NOTHING;
INSERT INTO product_categories (category_id, category_name, description) VALUES (5, 'Desk Accessories', 'Staplers, tape dispensers, organizers, and other desktop tools.') ON CONFLICT (category_id) DO NOTHING;
INSERT INTO product_categories (category_id, category_name, description) VALUES (6, 'Office Furniture', 'Chairs, desks, and other ergonomic furniture.') ON CONFLICT (category_id) DO NOTHING;


-- 2. Populate Customers (Test User Accounts)
-- Note: The password_hash for all users is a BCrypt hash for the simple password "password123".
-- This allows for easy login with any of these test users during development.
INSERT INTO customers (customer_id, first_name, last_name, email, password_hash, created_at, updated_at) VALUES (1, 'Michael', 'Scott', 'michael.scott@dundermifflin.com', '$2a$10$j8.A5t.d2a.iP4bO7O.9iOCGe..8JZo63l2a/iQz.W9g8b.xO9eY2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ON CONFLICT (customer_id) DO NOTHING;
INSERT INTO customers (customer_id, first_name, last_name, email, password_hash, created_at, updated_at) VALUES (2, 'Jim', 'Halpert', 'jim.halpert@dundermifflin.com', '$2a$10$j8.A5t.d2a.iP4bO7O.9iOCGe..8JZo63l2a/iQz.W9g8b.xO9eY2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ON CONFLICT (customer_id) DO NOTHING;
INSERT INTO customers (customer_id, first_name, last_name, email, password_hash, created_at, updated_at) VALUES (3, 'Pam', 'Beesly', 'pam.beesly@dundermifflin.com', '$2a$10$j8.A5t.d2a.iP4bO7O.9iOCGe..8JZo63l2a/iQz.W9g8b.xO9eY2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ON CONFLICT (customer_id) DO NOTHING;
INSERT INTO customers (customer_id, first_name, last_name, email, password_hash, created_at, updated_at) VALUES (4, 'Dwight', 'Schrute', 'dwight.schrute@dundermifflin.com', '$2a$10$j8.A5t.d2a.iP4bO7O.9iOCGe..8JZo63l2a/iQz.W9g8b.xO9eY2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ON CONFLICT (customer_id) DO NOTHING;
INSERT INTO customers (customer_id, first_name, last_name, email, password_hash, created_at, updated_at) VALUES (5, 'Angela', 'Martin', 'angela.martin@dundermifflin.com', '$2a$10$j8.A5t.d2a.iP4bO7O.9iOCGe..8JZo63l2a/iQz.W9g8b.xO9eY2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ON CONFLICT (customer_id) DO NOTHING;


-- 3. Populate Products (Depends on Product Categories)
-- A wider variety of products across all categories to allow for more realistic API testing.
INSERT INTO products (product_id, name, description, sku, price, stock_quantity, category_id, created_at) VALUES (1, 'Executive Gel Pen', 'Smooth writing black gel ink pen, 0.7mm.', 'PEN-GEL-BLK-001', 2.50, 500, 1, CURRENT_TIMESTAMP) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, name, description, sku, price, stock_quantity, category_id, created_at) VALUES (2, 'A4 Copy Paper', 'Standard 80gsm copy paper, 500 sheets.', 'PAP-A4-500S-WHT', 5.00, 250, 2, CURRENT_TIMESTAMP) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, name, description, sku, price, stock_quantity, category_id, created_at) VALUES (3, 'All-in-One Printer', 'Color inkjet printer with scan and copy function.', 'PRT-AIO-CLR-INK', 129.99, 50, 3, CURRENT_TIMESTAMP) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, name, description, sku, price, stock_quantity, category_id, created_at) VALUES (4, 'Manila File Folders', 'Pack of 100 letter-size manila folders.', 'FIL-FLDR-100-LTR', 12.75, 300, 4, CURRENT_TIMESTAMP) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, name, description, sku, price, stock_quantity, category_id, created_at) VALUES (5, 'Electric Stapler', 'Heavy-duty electric stapler, 20-sheet capacity.', 'STP-ELEC-HD-20S', 24.95, 120, 5, CURRENT_TIMESTAMP) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, name, description, sku, price, stock_quantity, category_id, created_at) VALUES (6, 'Highlighters, 5-Pack', 'Assorted fluorescent colors.', 'HIL-AST-5PK-001', 4.50, 800, 1, CURRENT_TIMESTAMP) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, name, description, sku, price, stock_quantity, category_id, created_at) VALUES (7, 'Sticky Notes, 3x3', 'Yellow, 12 pads per pack.', 'STN-YEL-3X3-12P', 8.99, 1200, 2, CURRENT_TIMESTAMP) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, name, description, sku, price, stock_quantity, category_id, created_at) VALUES (8, 'Wireless Mouse', 'Ergonomic wireless optical mouse.', 'MSE-WLS-ERG-BLK', 19.99, 150, 3, CURRENT_TIMESTAMP) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, name, description, sku, price, stock_quantity, category_id, created_at) VALUES (9, '3-Ring Binder, 2-Inch', 'White, heavy-duty view binder.', 'BND-3RG-2IN-WHT', 6.25, 400, 4, CURRENT_TIMESTAMP) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, name, description, sku, price, stock_quantity, category_id, created_at) VALUES (10, 'Desk Organizer', 'Black mesh, 6 compartments.', 'ORG-DSK-MSH-BLK', 14.99, 250, 5, CURRENT_TIMESTAMP) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, name, description, sku, price, stock_quantity, category_id, created_at) VALUES (11, 'Ergonomic Office Chair', 'High-back mesh chair with adjustable lumbar support.', 'CHR-ERG-BLK-01', 189.99, 40, 6, CURRENT_TIMESTAMP) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, name, description, sku, price, stock_quantity, category_id, created_at) VALUES (12, 'Legal Pad, 12-Pack', 'Canary yellow, wide-ruled legal pads.', 'PAD-LGL-YEL-12P', 11.50, 600, 2, CURRENT_TIMESTAMP) ON CONFLICT (product_id) DO NOTHING;


-- 4. Populate Orders (Depends on Customers)
-- Includes various statuses to test different API filtering and retrieval scenarios.
INSERT INTO orders (order_id, customer_id, order_date, status, total_amount) VALUES (1, 1, '2025-10-15 10:30:00', 'SHIPPED', 22.98) ON CONFLICT (order_id) DO NOTHING;
INSERT INTO orders (order_id, customer_id, order_date, status, total_amount) VALUES (2, 2, '2025-10-16 11:00:00', 'DELIVERED', 162.73) ON CONFLICT (order_id) DO NOTHING;
INSERT INTO orders (order_id, customer_id, order_date, status, total_amount) VALUES (3, 1, '2025-10-16 14:00:00', 'PENDING', 12.50) ON CONFLICT (order_id) DO NOTHING;
INSERT INTO orders (order_id, customer_id, order_date, status, total_amount) VALUES (4, 3, '2025-10-16 15:00:00', 'PENDING', 21.00) ON CONFLICT (order_id) DO NOTHING;
INSERT INTO orders (order_id, customer_id, order_date, status, total_amount) VALUES (5, 4, '2025-09-20 09:00:00', 'CANCELED', 5.00) ON CONFLICT (order_id) DO NOTHING;


-- 5. Populate Order Items (Depends on Orders and Products)
-- This section defines the contents of each order, creating complex relationships for testing.
-- Order 1 (Michael)
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (1, 7, 2, 8.99) ON CONFLICT (order_id, product_id) DO NOTHING;
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (1, 1, 2, 2.50) ON CONFLICT (order_id, product_id) DO NOTHING;
-- Order 2 (Jim) - A larger, more complex order
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (2, 3, 1, 129.99) ON CONFLICT (order_id, product_id) DO NOTHING;
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (2, 8, 1, 19.99) ON CONFLICT (order_id, product_id) DO NOTHING;
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (2, 10, 1, 12.75) ON CONFLICT (order_id, product_id) DO NOTHING;
-- Order 3 (Michael's second order)
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (3, 9, 2, 6.25) ON CONFLICT (order_id, product_id) DO NOTHING;
-- Order 4 (Pam)
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (4, 1, 2, 2.50) ON CONFLICT (order_id, product_id) DO NOTHING;
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (4, 12, 1, 11.50) ON CONFLICT (order_id, product_id) DO NOTHING;
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (4, 6, 1, 4.50) ON CONFLICT (order_id, product_id) DO NOTHING;
-- Order 5 (Dwight) - A simple, canceled order
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (5, 2, 1, 5.00) ON CONFLICT (order_id, product_id) DO NOTHING;


-- 6. Reset Primary Key Sequences (CRITICAL for PostgreSQL)
-- After manually inserting records with specific IDs, PostgreSQL's auto-incrementing
-- sequences are not aware of these new, higher ID values. We must manually update
-- the sequences to the highest current ID in each table, plus one. Failure to do this will result in
-- a "duplicate key value violates unique constraint" error the next time the application
-- tries to create a new record (e.g., creating customer with ID 6 when one already exists).
-- The 'true' argument in setval indicates that the next value generated should be after the specified max value.
SELECT setval('customers_customer_id_seq', (SELECT MAX(customer_id) FROM customers), true);
SELECT setval('product_categories_category_id_seq', (SELECT MAX(category_id) FROM product_categories), true);
SELECT setval('products_product_id_seq', (SELECT MAX(product_id) FROM products), true);
SELECT setval('orders_order_id_seq', (SELECT MAX(order_id) FROM orders), true);
-- Note: The 'order_items' table has a composite primary key and therefore does not have its own sequence.

