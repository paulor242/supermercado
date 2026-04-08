package com.micromarket.controller;

import com.micromarket.dto.ProveedorDTO;
import com.micromarket.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el CRUD de proveedores.
 * Todas las rutas aquí requieren token JWT — no es público.
 * Base URL: /api/proveedores
 */
@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService service;

    public ProveedorController(ProveedorService service) {
        this.service = service;
    }

    /**
     * Crea un nuevo proveedor.
     * @Valid valida que NIT y nombre no vengan vacíos antes de procesar.
     * Si el NIT ya existe en BD, el servicio lanza un error 409.
     */
    @PostMapping
    public ResponseEntity<ProveedorDTO> crear(@Valid @RequestBody ProveedorDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    /**
     * Devuelve la lista completa de proveedores registrados.
     * Si no hay ninguno, devuelve una lista vacía (no un 404).
     */
    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }
}
