package com.proyect.supermercado.service;

import com.proyect.supermercado.dto.EmpleadoResponseDTO;
import com.proyect.supermercado.dto.SaleRequestDTO;
import com.proyect.supermercado.dto.SaleResponseDTO;
import com.proyect.supermercado.entity.Empleado;
import com.proyect.supermercado.entity.Sales;
import com.proyect.supermercado.exception.ResourceNotFoundException;
import com.proyect.supermercado.repository.EmpleadoRepository;
import com.proyect.supermercado.repository.SalesRepository;
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
    private final EmpleadoRepository empleadoRepository;

    /**
     * Crea una nueva venta a partir de los datos del request.
     * Mapea el DTO a entidad, guarda en BD y devuelve el DTO de respuesta con el ID generado.
     */
    public SaleResponseDTO saleCreate(SaleRequestDTO request) {
        Sales sale = new Sales();
        Empleado empleado = empleadoRepository.findById(request.getIdEmpleado())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado"));

        sale.setDateSale(request.getDateSale());
        sale.setTotal(request.getTotal());
        sale.setVat(request.getVat());
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

        if (sale.getIdempleado()!=null){
            EmpleadoResponseDTO responseDTO= new EmpleadoResponseDTO();
            responseDTO.setId(sale.getIdempleado().getId());
        }
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

            if (sale.getIdempleado()!=null){
                EmpleadoResponseDTO responseDTO= new EmpleadoResponseDTO();
                responseDTO.setId(sale.getIdempleado().getId());
            }

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
        response.setIdEmpleado(sale.getIdempleado() != null ? sale.getIdempleado().getId() : null);
        response.setTotal(sale.getTotal());
        response.setDateSale(sale.getDateSale());
        response.setVat(sale.getVat());
        response.setSubTotal(sale.getSubTotal());
        response.setState(sale.getState());

        if (sale.getIdempleado()!=null){
            EmpleadoResponseDTO responseDTO= new EmpleadoResponseDTO();
            responseDTO.setId(sale.getIdempleado().getId());
        }

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
            Empleado empleado = empleadoRepository.findById(request.getIdEmpleado())
                    .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado"));
            sale.setIdempleado(empleado);
            sale.setState(request.getState());

            Sales updateSale = salesRepository.save(sale);

            SaleResponseDTO response = new SaleResponseDTO();
            response.setId(updateSale.getId());
            response.setDateSale(updateSale.getDateSale());
            response.setVat(updateSale.getVat());
            response.setTotal(updateSale.getTotal());
            response.setState(updateSale.getState());
            response.setIdEmpleado(empleado.getId());
            response.setSubTotal(updateSale.getSubTotal());
            if (sale.getIdempleado()!=null){
                EmpleadoResponseDTO responseDTO= new EmpleadoResponseDTO();
                responseDTO.setId(sale.getIdempleado().getId());
            }

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
