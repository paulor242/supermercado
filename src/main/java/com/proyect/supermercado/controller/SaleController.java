package com.proyect.supermercado.controller;

import com.proyect.supermercado.dto.SaleRequestDTO;
import com.proyect.supermercado.dto.SaleResponseDTO;
import com.proyect.supermercado.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>>getSale (){
        List<SaleResponseDTO>response = sales.getSales();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO>getSaleId(@PathVariable Long id ){
        SaleResponseDTO responseDTO = sales.getSaleId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponseDTO>update(@PathVariable Long id, @RequestBody SaleRequestDTO requestDTO){
        SaleResponseDTO responseDTO= sales.updateSale(id, requestDTO)
                .orElseThrow(()->new RuntimeException("venta no encontrada"));
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <SaleResponseDTO>delete(@PathVariable Long id){
        Boolean deleted =sales.delete(id);
        if (deleted){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
