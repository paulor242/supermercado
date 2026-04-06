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
) ENGINE=InnoDB D

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

INSERT INTO user (username, password) VALUES ('admin', '$2a$10$examplehashedpassword');
INSERT INTO user_roles (user_id, role) VALUES (1, 'ADMINISTRADOR');

INSERT INTO user (username, password) VALUES ('auxiliar', '$2a$10$examplehashedpassword');
INSERT INTO user_roles (user_id, role) VALUES (2, 'AUXILIAR');

INSERT INTO supplier (name, contact) VALUES ('Proveedor A', 'contacto@a.com');
INSERT INTO supplier (name, contact) VALUES ('Proveedor B', 'contacto@b.com');

INSERT INTO product_supplier (product_id, supplier_id) VALUES (1, 1);
INSERT INTO product_supplier (product_id, supplier_id) VALUES (2, 2);