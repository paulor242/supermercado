CREATE DATABASE IF NOT EXISTS empleados_db;
USE empleados_db;

CREATE TABLE empleados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    cedula VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,

    cargo ENUM('ADMINISTRADOR', 'CAGERO', 'AUXILIIAR') NOT NULL,

    fecha DATE NOT NULL,
    fecha_ingreso DATE NOT NULL,

    salario DECIMAL(10,2) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
INSERT INTO empleados (cedula, nombre, cargo, fecha, fecha_ingreso, salario)
VALUES 
('123456789', 'Juan Perez', 'ADMINISTRADOR', '2024-01-01', '2024-02-01', 3000000),
('987654321', 'Maria Gomez', 'CAGERO', '2024-01-10', '2024-02-15', 1800000),
('456123789', 'Carlos Ruiz', 'AUXILIIAR', '2024-02-01', '2024-03-01', 1500000);