package com.proyect.supermercado.service;

import com.proyect.supermercado.dto.DetailSaleRequestDTO;
import com.proyect.supermercado.dto.DetailSaleResponseDTO;
import com.proyect.supermercado.dto.SaleResponseDTO;
import com.proyect.supermercado.entity.DetailSale;
import com.proyect.supermercado.entity.Sales;
import com.proyect.supermercado.repository.DetailSalesRepository;
import com.proyect.supermercado.repository.SalesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Transactional
@RequiredArgsConstructor
@Service
public class DetailSaleService {
    private final DetailSalesRepository detailSalesRepository;
    private  final SalesRepository salesRepository;
    public DetailSaleResponseDTO create(DetailSaleRequestDTO request){

        DetailSale detailSale = new DetailSale();
        detailSale.setAmount(request.getAmount());
        detailSale.setSubTotal(request.getSubTotal());
        detailSale.setUnitPrice(request.getUnitPrice());
        detailSale.setIdProduct(request.getIdProduct());

        Sales sale = salesRepository.findById(request.getSales())
                        .orElseThrow(()->new RuntimeException("venta no encontrada"));
        detailSale.setSales(sale);

        detailSalesRepository.save(detailSale);

        DetailSaleResponseDTO response= new DetailSaleResponseDTO();
        response.setId(detailSale.getId());
        response.setAmount(detailSale.getAmount());
        response.setUnitPrice(detailSale.getUnitPrice());
        response.setSubTotal(detailSale.getSubTotal());
        response.setIdProduct(detailSale.getIdProduct());
        if(detailSale.getSales() !=null){
            SaleResponseDTO detailDTO = new SaleResponseDTO();
            detailDTO.setId(detailSale.getSales().getId());
            detailDTO.setSubTotal(detailSale.getSubTotal());
            detailDTO.setVat(detailSale.getSales().getVat());
            detailDTO.setDateSale(detailSale.getSales().getDateSale());
            detailDTO.setTotal(detailSale.getSales().getTotal());
            detailDTO.setState(detailSale.getSales().getState());
            detailDTO.setIdEmpleado(detailSale.getSales().getIdEmpleado());
            response.setSales((detailDTO));

        }

        return response;
    }



}
