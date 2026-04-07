package com.proyect.supermercado.dto;

import lombok.Data;
import java.util.List;

@Data
public class RestockRequestDTO {
    private Long supplierId;
    private List<RestockDetailRequestDTO> details;
}