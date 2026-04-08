package com.micromarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// scanBasePackages le dice a Spring que busque beans en AMBOS paquetes:
// - com.micromarket: proveedores, auth, seguridad JWT
// - com.proyect: ventas y detalles de venta
// Sin esto, los controladores y servicios de com.proyect serían invisibles para Spring
@SpringBootApplication(scanBasePackages = {"com.micromarket", "com.proyect"})
public class MicromarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicromarketApplication.class, args);
    }
}
