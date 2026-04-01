package com.proyect.supermercado.controller;

import com.proyect.supermercado.dto.SaleRequestDTO;
import com.proyect.supermercado.dto.SaleResponseDTO;
import com.proyect.supermercado.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/sale")
public class SaleController {
    private final SalesService sales;

    @PostMapping
    public ResponseEntity<SaleResponseDTO> create (@RequestBody SaleRequestDTO request){
        SaleResponseDTO response =sales.saleCreate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




}
