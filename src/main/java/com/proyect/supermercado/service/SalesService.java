package com.proyect.supermercado.service;

import com.proyect.supermercado.dto.SaleRequestDTO;
import com.proyect.supermercado.dto.SaleResponseDTO;
import com.proyect.supermercado.entity.Empleado;
import com.proyect.supermercado.repository.SalesRepository;
import com.proyect.supermercado.entity.Sales;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio con toda la lógica de negocio de ventas.
 * @Transactional garantiza que si algo falla a mitad de una operación,
 * la BD vuelve al estado anterior automáticamente (rollback).
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SalesService {

    private final SalesRepository salesRepository;
    private  final Empleado empleado;

    /**
     * Crea una nueva venta a partir de los datos del request.
     * Mapea el DTO a entidad, guarda en BD y devuelve el DTO de respuesta con el ID generado.
     */
    public SaleResponseDTO saleCreate(SaleRequestDTO request) {
        Sales sale = new Sales();
        sale.setDateSale(request.getDateSale());
        sale.setTotal(request.getTotal());
        sale.setVat(request.getVat());
        sale.setIdempleado(empleado.getId());
        sale.setState(request.getState());
        sale.setSubTotal(request.getSubTotal());

        salesRepository.save(sale); // después del save, sale.getId() ya tiene el ID de la BD

        SaleResponseDTO response = new SaleResponseDTO();
        response.setId(sale.getId());
        response.setDateSale(sale.getDateSale());
        response.setTotal(sale.getTotal());
        response.setVat(sale.getVat());
        response.setState(sale.getState());
        response.setSubTotal(sale.getSubTotal());
        response.setIdEmpleado(empleado.getId());

        return response;
    }

    /**
     * Trae todas las ventas de la BD y las convierte a DTOs.
     * Devuelve lista vacía si no hay ventas — nunca null.
     */
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
            response.setIdEmpleado(empleado.getId());

            list.add(response);
        }
        return list;
    }

    /**
     * Busca una venta por ID.
     * Si no existe lanza RuntimeException — el GlobalExceptionHandler debería capturarla.
     */
    public SaleResponseDTO getSaleId(Long id) {
        Sales sale = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id not found"));

        SaleResponseDTO response = new SaleResponseDTO();
        response.setId(sale.getId());
        response.setIdEmpleado(empleado.getId());
        response.setTotal(sale.getTotal());
        response.setDateSale(sale.getDateSale());
        response.setVat(sale.getVat());
        response.setSubTotal(sale.getSubTotal());
        response.setState(sale.getState());

        return response;
    }

    /**
     * Actualiza una venta existente con los nuevos datos.
     * Devuelve Optional vacío si el ID no existe — el controlador decide qué hacer con eso.
     */
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

            Sales updateSale = salesRepository.save(sale); // save también sirve para actualizar si el ID ya existe

            SaleResponseDTO response = new SaleResponseDTO();
            response.setId(updateSale.getId());
            response.setDateSale(updateSale.getDateSale());
            response.setVat(updateSale.getVat());
            response.setTotal(updateSale.getTotal());
            response.setState(updateSale.getState());
            response.setIdEmpleado(empleado.getId());
            response.setSubTotal(updateSale.getSubTotal());

            return Optional.of(response);
        } else {
            return Optional.empty(); // el controlador lanzará excepción si llega aquí
        }
    }

    /**
     * Elimina una venta por ID.
     * Devuelve true si se borró, false si el ID no existía.
     */
    public boolean delete(Long id) {
        Optional<Sales> sales = salesRepository.findById(id);

        if (sales.isPresent()) { // isPresent() es más claro que !isEmpty()
            salesRepository.delete(sales.get());
            return true;
        }
        return false;
    }



}
