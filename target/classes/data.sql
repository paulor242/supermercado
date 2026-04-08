-- Usuario administrador por defecto para pruebas
-- Contraseña: "admin" encriptada con BCrypt
INSERT INTO users (username, password, role) VALUES ('admin_daniela', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN');

-- Proveedores de ejemplo para arrancar con datos
INSERT INTO proveedores (nit, nombre) VALUES ('800123456-1', 'Proveedor A');
INSERT INTO proveedores (nit, nombre) VALUES ('900987654-2', 'Proveedor B');
