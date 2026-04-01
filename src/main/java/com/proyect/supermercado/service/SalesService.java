package com.proyect.supermercado.service;

import com.proyect.supermercado.dto.SaleRequestDTO;
import com.proyect.supermercado.dto.SaleResponseDTO;
import com.proyect.supermercado.repository.SalesRepository;
import com.proyect.supermercado.entity.Sales;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class SalesService {
    private final SalesRepository salesRepository;

    public SaleResponseDTO saleCreate(SaleRequestDTO request){
        Sales sale = new Sales();
        sale.setDateSale(request.getDateSale());
        sale.setTotal(request.getTotal());
        sale.setVat(request.getVat());
        sale.setIdEmpleado(request.getIdEmpleado());
        sale.setState(request.getState());
        sale.setSubTotal(request.getSubTotal());

        salesRepository.save(sale);

        SaleResponseDTO response = new SaleResponseDTO();
        response.setId(sale.getId());
        response.setDateSale(sale.getDateSale());
        response.setTotal(sale.getTotal());
        response.setVat(sale.getVat());
        response.setState(sale.getState());
        response.setSubTotal(sale.getSubTotal());
        response.setIdEmpleado(sale.getIdEmpleado());

        return response;
    }

    public List <SaleResponseDTO> getSales(){
        List<Sales> sales = salesRepository.findAll();
        List<SaleResponseDTO> list = new ArrayList<>();

        for (Sales sale : sales ){
            SaleResponseDTO response = new SaleResponseDTO();
            response.setId(sale.getId());
            response.setState(sale.getState());
            response.setDateSale(sale.getDateSale());
            response.setVat(sale.getVat());
            response.setSubTotal(sale.getSubTotal());
            response.setTotal(sale.getTotal());
            response.setIdEmpleado(sale.getIdEmpleado());

            list.add(response);
        }
        return list;
    }

    public SaleResponseDTO getSaleId(Long id){
        Sales sale =salesRepository.findById(id)
                .orElseThrow(()->new RuntimeException("id not found "));

        SaleResponseDTO response = new SaleResponseDTO();
        response.setId(sale.getId());
        response.setIdEmpleado(sale.getIdEmpleado());
        response.setTotal(sale.getTotal());
        response.setDateSale(sale.getDateSale());
        response.setVat(sale.getVat());
        response.setSubTotal(sale.getSubTotal());
        response.setState(sale.getState());

        return response;
    }

}
