package com.proyect.supermercado.controller;

import com.proyect.supermercado.dto.DetailSaleRequestDTO;
import com.proyect.supermercado.dto.DetailSaleResponseDTO;
import com.proyect.supermercado.service.DetailSaleService;

import lombok.RequiredArgsConstructor;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/details")
public class DetailSaleController {

    private final DetailSaleService detailSaleService;

    @PostMapping
    public ResponseEntity <DetailSaleResponseDTO> create(@RequestBody DetailSaleRequestDTO request){
        DetailSaleResponseDTO response = detailSaleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<DetailSaleResponseDTO>>getDetails(){
        List<DetailSaleResponseDTO> response =detailSaleService.get();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping ("/{id}")
    public  ResponseEntity<DetailSaleResponseDTO> getUSer(@PathVariable Long id ){
        DetailSaleResponseDTO responseDTO = detailSaleService.getForId(id);
        return ResponseEntity.ok(responseDTO);
    }


}
