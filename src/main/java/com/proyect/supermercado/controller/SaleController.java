package com.proyect.supermercado.controller;

import com.proyect.supermercado.dto.SaleRequestDTO;
import com.proyect.supermercado.dto.SaleResponseDTO;
import com.proyect.supermercado.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el CRUD completo de ventas.
 * Base URL: /sale
 * Todas las rutas requieren token JWT — no son públicas.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/sale")
public class SaleController {

    private final SalesService sales;

    /**
     * Crea una nueva venta y devuelve el registro con el ID generado.
     * Devuelve 201 Created en lugar de 200 porque se está creando un recurso nuevo.
     */
    @PostMapping
    public ResponseEntity<SaleResponseDTO> create(@RequestBody SaleRequestDTO request) {
        SaleResponseDTO response = sales.saleCreate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Devuelve todas las ventas registradas.
     * Si no hay ninguna, devuelve lista vacía con 200 — no un 404.
     */
    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>> getSale() {
        List<SaleResponseDTO> response = sales.getSales();
        return ResponseEntity.ok(response);
    }

    /**
     * Busca una venta por su ID.
     * Si no existe, el servicio lanza RuntimeException y Spring devuelve 500.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> getSaleId(@PathVariable Long id) {
        SaleResponseDTO responseDTO = sales.getSaleId(id);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Actualiza todos los campos de una venta existente.
     * Si el ID no existe, el Optional vacío lanza RuntimeException con 500.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> update(@PathVariable Long id, @RequestBody SaleRequestDTO requestDTO) {
        SaleResponseDTO responseDTO = sales.updateSale(id, requestDTO)
                .orElseThrow(() -> new RuntimeException("venta no encontrada"));
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Elimina una venta por ID.
     * Devuelve 204 No Content si se borró, 404 si no existía.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> delete(@PathVariable Long id) {
        boolean deleted = sales.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
