-- ============================================
-- CREACIÓN DE TABLAS - MÓDULO I COMPLETO
-- Incluye: Categorías, Productos
-- ============================================
<<<<<<< HEAD
create database supermercado;
=======
>>>>>>> feature/ximena-abastecimiento
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

<<<<<<< HEAD
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
=======


CREATE TABLE producto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    codigo_barras VARCHAR(50) NOT NULL UNIQUE,
    stock INT DEFAULT 0,
    precio DOUBLE,
    estado BOOLEAN DEFAULT TRUE
);

CREATE TABLE proveedor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    nit VARCHAR(50) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    estado BOOLEAN DEFAULT TRUE
);


CREATE TABLE producto_proveedor (
    producto_id BIGINT,
    proveedor_id BIGINT,
    PRIMARY KEY (producto_id, proveedor_id),
    FOREIGN KEY (producto_id) REFERENCES producto(id),
    FOREIGN KEY (proveedor_id) REFERENCES proveedor(id)
);


CREATE TABLE usuario (
>>>>>>> feature/ximena-abastecimiento
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL
);

<<<<<<< HEAD
CREATE TABLE employee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    charge ENUM('ADMINISTRADOR', 'CAGERO', 'AUXILIIAR') NOT NULL,
    date DATE NOT NULL,
    entry_date DATE NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
=======

CREATE TABLE empleados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    cedula VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,

    cargo ENUM('ADMINISTRADOR', 'CAGERO', 'AUXILIIAR') NOT NULL,

    fecha DATE NOT NULL,
    fecha_ingreso DATE NOT NULL,

    salario DECIMAL(10,2) NOT NULL,

>>>>>>> feature/ximena-abastecimiento
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

<<<<<<< HEAD
=======
select * from sales;


drop table detailsale;

>>>>>>> feature/ximena-abastecimiento
create table sale(
id int primary key auto_increment,
dateSale date, 
SubTotal int,
vat int, 
total int,
state varchar (20) default "pending",
<<<<<<< HEAD
idEmployee bigint,
foreign key (idEmployee) references employee (id)
);

=======
idEmpleado bigint,
foreign key (idEmpleado) references empleados (id)
);
>>>>>>> feature/ximena-abastecimiento
create table detailsale(
id int primary key auto_increment,
amount int,
unitPrice int,
subTotal int,
idSale int,
idProduct bigint,
foreign key (idSale) references sale(id),
<<<<<<< HEAD
foreign key (idProduct) references product (id)
);
=======
foreign key (idProduct) references producto (id)
);

drop table sale;
>>>>>>> feature/ximena-abastecimiento
