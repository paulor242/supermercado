package com.proyect.supermercado.controller;

import com.proyect.supermercado.dto.RestockRequestDTO;
import com.proyect.supermercado.dto.RestockResponseDTO;
import com.proyect.supermercado.service.RestockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restocks")
public class RestockController {

    @Autowired
    private RestockService restockService;

    @PostMapping
    public ResponseEntity<RestockResponseDTO> createRestock(@RequestBody RestockRequestDTO request) {
        RestockResponseDTO response = restockService.createRestock(request);
        return ResponseEntity.ok(response);
    }
}