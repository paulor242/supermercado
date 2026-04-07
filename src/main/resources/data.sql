-- ============================================
-- CREACIÓN DE TABLAS - MÓDULO I COMPLETO
-- Incluye: Categorías, Productos
-- ============================================

-- 1. TABLA DE CATEGORÍAS
CREATE TABLE IF NOT EXISTS category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    
    INDEX idx_category_name (name),
    INDEX idx_category_active (active)
) 

-- 2. TABLA DE PRODUCTOS

CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    barcode VARCHAR(50) NOT NULL UNIQUE,           -- REGLA DE NEGOCIO 2: Único
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0), -- Precio no negativo
    stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0), -- Stock no negativo
    active BOOLEAN NOT NULL DEFAULT TRUE,          -- REGLA DE NEGOCIO 1: Soft Delete
    category_id BIGINT NOT NULL,                   -- FK a Category

    -- Restricciones de llave foránea
    CONSTRAINT fk_product_category 
        FOREIGN KEY (category_id) 
        REFERENCES category(id)
        ON DELETE RESTRICT    -- No eliminar categoría si tiene productos
        ON UPDATE CASCADE,
    
    -- Índices para optimizar búsquedas
    INDEX idx_product_barcode (barcode),
    INDEX idx_product_category (category_id),
    INDEX idx_product_active (active),
    INDEX idx_product_name (name)
)

-- Datos de prueba para el Módulo 1
-- Categorías y Productos para un supermercado

-- ==========================================
-- CATEGORÍAS
-- ==========================================

INSERT INTO category (name, description, active) VALUES 
('Lácteos', 'Productos lácteos: leche, queso, yogurt', TRUE),
('Bebidas', 'Bebidas alcohólicas y no alcohólicas', TRUE),
('Panadería', 'Productos de panadería y pastelería', TRUE),
('Carnes', 'Carnes frescas y procesadas', TRUE),
('Frutas y Verduras', 'Productos frescos del campo', TRUE),
('Limpieza', 'Productos de limpieza del hogar', TRUE),
('Abarrotes', 'Productos de despensa básicos', TRUE);

-- ==========================================
-- PRODUCTOS
-- ==========================================

-- Productos de Lácteos
INSERT INTO product (name, description, barcode, price, stock, active, category_id) VALUES
('Leche Entera 1L', 'Leche entera pasteurizada 1 litro', '750100010001', 25.50, 100, TRUE, 1),
('Queso Panela 500g', 'Queso panela fresco 500 gramos', '750100010002', 45.00, 50, TRUE, 1),
('Yogurt Natural 1kg', 'Yogurt natural sin azúcar 1kg', '750100010003', 35.00, 75, TRUE, 1),
('Mantequilla 250g', 'Mantequilla sin sal 250 gramos', '750100010004', 42.00, 60, TRUE, 1);

-- Productos de Bebidas
INSERT INTO product (name, description, barcode, price, stock, active, category_id) VALUES
('Agua Natural 1L', 'Agua purificada 1 litro', '750100020001', 15.00, 200, TRUE, 2),
('Refresco Cola 2L', 'Refresco de cola 2 litros', '750100020002', 28.00, 150, TRUE, 2),
('Jugo Naranja 1L', 'Jugo de naranja natural 1 litro', '750100020003', 32.00, 80, TRUE, 2),
('Cerveza 6-pack', 'Cerveza lager 6 botellas 355ml', '750100020004', 95.00, 40, TRUE, 2);

-- Productos de Panadería
INSERT INTO product (name, description, barcode, price, stock, active, category_id) VALUES
('Pan Blanco', 'Pan de caja blanco 680g', '750100030001', 38.00, 60, TRUE, 3),
('Croissants', 'Croissants de mantequilla 4 piezas', '750100030002', 45.00, 30, TRUE, 3),
('Galletas María', 'Galletas María 400g', '750100030003', 22.00, 100, TRUE, 3);

-- Productos de Carnes
INSERT INTO product (name, description, barcode, price, stock, active, category_id) VALUES
('Pollo Entero', 'Pollo fresco entero kg', '750100040001', 85.00, 25, TRUE, 4),
('Carne Molida 500g', 'Carne de res molida 500g', '750100040002', 95.00, 30, TRUE, 4),
('Salchichas 1kg', 'Salchichas de pavo 1kg', '750100040003', 75.00, 45, TRUE, 4);

-- Productos de Frutas y Verduras
INSERT INTO product (name, description, barcode, price, stock, active, category_id) VALUES
('Manzana Roja kg', 'Manzana roja importada kg', '750100050001', 45.00, 80, TRUE, 5),
('Plátano kg', 'Plátano tabasco kg', '750100050002', 18.00, 120, TRUE, 5),
('Tomate kg', 'Tomate rojo maduro kg', '750100050003', 25.00, 90, TRUE, 5);

-- Productos de Limpieza
INSERT INTO product (name, description, barcode, price, stock, active, category_id) VALUES
('Detergente 3kg', 'Detergente en polvo 3kg', '750100060001', 85.00, 50, TRUE, 6),
('Cloro 1L', 'Cloro desinfectante 1 litro', '750100060002', 22.00, 70, TRUE, 6),
('Jabón Líquido', 'Jabón líquido para trastes 1L', '750100060003', 35.00, 65, TRUE, 6);

-- Productos de Abarrotes
INSERT INTO product (name, description, barcode, price, stock, active, category_id) VALUES
('Arroz 1kg', 'Arroz blanco grano largo 1kg', '750100070001', 18.00, 150, TRUE, 7),
('Frijoles 500g', 'Frijoles negros 500g', '750100070002', 22.00, 120, TRUE, 7),
('Aceite Vegetal 1L', 'Aceite vegetal comestible 1L', '750100070003', 32.00, 100, TRUE, 7),
('Sal 1kg', 'Sal yodada 1kg', '750100070004', 12.00, 200, TRUE, 7);

INSERT INTO user (username, password) VALUES ('admin', '$2a$10$examplehashedpassword');
INSERT INTO user_roles (user_id, role) VALUES (1, 'ADMINISTRADOR');

INSERT INTO user (username, password) VALUES ('auxiliar', '$2a$10$examplehashedpassword');
INSERT INTO user_roles (user_id, role) VALUES (2, 'AUXILIAR');

INSERT INTO supplier (name, contact) VALUES ('Supplier A', 'contact@a.com');
INSERT INTO supplier (name, contact) VALUES ('Supplier B', 'contact@b.com');


INSERT INTO product_supplier (product_id, supplier_id) VALUES (1, 1);
INSERT INTO product_supplier (product_id, supplier_id) VALUES (2, 2);