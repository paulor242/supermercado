package com.proyect.supermercado.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RestockResponseDTO {
    private Long id;
    private LocalDateTime date;
    private String supplierName;
    private List<RestockDetailResponseDTO> details;
}