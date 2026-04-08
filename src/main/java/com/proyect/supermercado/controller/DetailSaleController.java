package com.proyect.supermercado.controller;

import com.proyect.supermercado.dto.DetailSaleRequestDTO;
import com.proyect.supermercado.dto.DetailSaleResponseDTO;
import com.proyect.supermercado.service.DetailSaleService;
import lombok.RequiredArgsConstructor;

import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para los detalles de venta.
 * Base URL: /details
 * Un detalle es cada línea de producto dentro de una venta.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/details")
public class DetailSaleController {

    private final DetailSaleService detailSaleService;

    /**
     * Crea un nuevo detalle asociado a una venta existente.
     * El body debe incluir el ID de la venta padre (campo "sales").
     * Si esa venta no existe, el servicio lanza excepción y devuelve 500.
     */
    @PostMapping
    public ResponseEntity<DetailSaleResponseDTO> create(@RequestBody DetailSaleRequestDTO request) {
        DetailSaleResponseDTO response = detailSaleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Devuelve todos los detalles de todas las ventas.
     * Cada detalle incluye el objeto completo de su venta padre embebido.
     */
    @GetMapping
    public ResponseEntity<List<DetailSaleResponseDTO>> getDetails() {
        List<DetailSaleResponseDTO> response = detailSaleService.get();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
