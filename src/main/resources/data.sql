INSERT INTO user (username, password) VALUES ('admin', '$2a$10$examplehashedpassword');
INSERT INTO user_roles (user_id, role) VALUES (1, 'ADMINISTRADOR');

INSERT INTO user (username, password) VALUES ('auxiliar', '$2a$10$examplehashedpassword');
INSERT INTO user_roles (user_id, role) VALUES (2, 'AUXILIAR');

INSERT INTO supplier (name, contact) VALUES ('Proveedor A', 'contacto@a.com');
INSERT INTO supplier (name, contact) VALUES ('Proveedor B', 'contacto@b.com');

INSERT INTO product (name, description, price, stock) VALUES ('Producto 1', 'Descripción 1', 10.0, 100);
INSERT INTO product (name, description, price, stock) VALUES ('Producto 2', 'Descripción 2', 20.0, 50);

INSERT INTO product_supplier (product_id, supplier_id) VALUES (1, 1);
INSERT INTO product_supplier (product_id, supplier_id) VALUES (2, 2);