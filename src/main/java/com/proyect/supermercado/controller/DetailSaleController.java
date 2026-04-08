package com.proyect.supermercado.controller;

import com.proyect.supermercado.dto.DetailSaleRequestDTO;
import com.proyect.supermercado.dto.DetailSaleResponseDTO;
import com.proyect.supermercado.service.DetailSaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/details")
@PreAuthorize("hasAnyAuthority('CAJERO', 'ADMINISTRADOR')")
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

    @GetMapping("/{id}")
    public ResponseEntity<DetailSaleResponseDTO> getSale(@PathVariable Long id) {
        DetailSaleResponseDTO responseDTO = detailSaleService.getForId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetailSaleResponseDTO> updateDetail(@PathVariable Long id, @RequestBody DetailSaleRequestDTO request) {
        DetailSaleResponseDTO response = detailSaleService.detailsUpdate(id, request)
                .orElseThrow(() -> new RuntimeException("el id no fue encontrado"));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DetailSaleResponseDTO> delete(@PathVariable Long id) {
        Boolean deleted = detailSaleService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
