package com.proyect.supermercado.service;

import com.proyect.supermercado.dto.EmpleadoResponseDTO;
import com.proyect.supermercado.dto.SaleRequestDTO;
import com.proyect.supermercado.dto.SaleResponseDTO;
import com.proyect.supermercado.repository.SalesRepository;
import com.proyect.supermercado.entity.Sales;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesService {

    private final SalesRepository salesRepository;

    public SaleResponseDTO saleCreate(SaleRequestDTO request) {
        Sales sale = new Sales();
        sale.setDateSale(request.getDateSale());
        sale.setTotal(request.getTotal());
        sale.setVat(request.getVat());
        sale.setState(request.getState());
        sale.setSubTotal(request.getSubTotal());
        sale.setIdempleado(request.getIdEmpleado());

        salesRepository.save(sale);

        SaleResponseDTO response = new SaleResponseDTO();
        response.setId(sale.getId());
        response.setDateSale(sale.getDateSale());
        response.setTotal(sale.getTotal());
        response.setVat(sale.getVat());
        response.setState(sale.getState());
        response.setSubTotal(sale.getSubTotal());

        if (sale.getIdempleado() != null) {
            response.setIdEmpleado(sale.getIdempleado().getId());
        }
        return response;
    }

    public List<SaleResponseDTO> getSales() {
        List<Sales> sales = salesRepository.findAll();
        List<SaleResponseDTO> list = new ArrayList<>();

        for (Sales sale : sales) {
            SaleResponseDTO response = new SaleResponseDTO();
            response.setId(sale.getId());
            response.setState(sale.getState());
            response.setDateSale(sale.getDateSale());
            response.setVat(sale.getVat());
            response.setSubTotal(sale.getSubTotal());
            response.setTotal(sale.getTotal());

            if (sale.getIdempleado() != null) {
                response.setIdEmpleado(sale.getIdempleado().getId());
            }

            list.add(response);
        }
        return list;
    }

    public SaleResponseDTO getSaleId(Long id) {
        Sales sale = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id not found"));

        SaleResponseDTO response = new SaleResponseDTO();
        response.setId(sale.getId());
        if (sale.getIdempleado() != null) {
            response.setIdEmpleado(sale.getIdempleado().getId());
        }
        response.setTotal(sale.getTotal());
        response.setDateSale(sale.getDateSale());
        response.setVat(sale.getVat());
        response.setSubTotal(sale.getSubTotal());
        response.setState(sale.getState());

        return response;
    }

    public Optional<SaleResponseDTO> updateSale(Long id, SaleRequestDTO request) {
        Optional<Sales> optionalSale = salesRepository.findById(id);

        if (optionalSale.isPresent()) {
            Sales sale = optionalSale.get();
            sale.setTotal(request.getTotal());
            sale.setVat(request.getVat());
            sale.setSubTotal(request.getSubTotal());
            sale.setDateSale(request.getDateSale());
            sale.setIdempleado(request.getIdEmpleado());
            sale.setState(request.getState());

            Sales updateSale = salesRepository.save(sale);

            SaleResponseDTO response = new SaleResponseDTO();
            response.setId(updateSale.getId());
            response.setDateSale(updateSale.getDateSale());
            response.setVat(updateSale.getVat());
            response.setTotal(updateSale.getTotal());
            response.setState(updateSale.getState());
            if (updateSale.getIdempleado() != null) {
                response.setIdEmpleado(updateSale.getIdempleado().getId());
            }
            response.setSubTotal(updateSale.getSubTotal());

            return Optional.of(response);
        } else {
            return Optional.empty();
        }
    }

    public boolean delete(Long id) {
        Optional<Sales> sales = salesRepository.findById(id);

        if (sales.isPresent()) {
            salesRepository.delete(sales.get());
            return true;
        }
        return false;
    }
}
