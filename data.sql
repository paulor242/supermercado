-- ============================================
-- CREACIÓN DE TABLAS - MÓDULO I COMPLETO
-- Incluye: Categorías, Productos
-- ============================================
create database supermercado;
use supermercado;

-- 1. TABLA DE CATEGORÍAS
CREATE TABLE IF NOT EXISTS category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE
);

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
        ON UPDATE CASCADE
);

CREATE TABLE supplier (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    nit VARCHAR(50) NOT NULL UNIQUE,
    phone VARCHAR(20),
    stated BOOLEAN DEFAULT TRUE
);

CREATE TABLE product_supplier (
    product_id BIGINT,
    supplier_id BIGINT,
    PRIMARY KEY (product_id, supplier_id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (supplier_id) REFERENCES supplier(id)
);

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL
);

CREATE TABLE employee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    charge ENUM('ADMINISTRADOR', 'CAGERO', 'AUXILIIAR') NOT NULL,
    date DATE NOT NULL,
    entry_date DATE NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table sale(
id int primary key auto_increment,
dateSale date, 
SubTotal int,
vat int, 
total int,
state varchar (20) default "pending",
idEmployee bigint,
foreign key (idEmployee) references employee (id)
);

create table detailsale(
id int primary key auto_increment,
amount int,
unitPrice int,
subTotal int,
idSale int,
idProduct bigint,
foreign key (idSale) references sale(id),
foreign key (idProduct) references product (id)
);