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

/**
 * Servicio con la lógica de negocio para los detalles de venta.
 * Necesita acceso a SalesRepository porque al crear un detalle
 * hay que verificar que la venta padre realmente exista en BD.
 */
@Transactional
@RequiredArgsConstructor
@Service
public class DetailSaleService {

    private final DetailSalesRepository detailSalesRepository;
    private final SalesRepository salesRepository;

    /**
     * Crea un detalle de venta y lo asocia a su venta padre.
     * Si la venta padre no existe, lanza excepción antes de guardar nada.
     * La respuesta incluye el objeto completo de la venta embebido.
     */
    public DetailSaleResponseDTO create(DetailSaleRequestDTO request) {
        DetailSale detailSale = new DetailSale();
        detailSale.setAmount(request.getAmount());
        detailSale.setSubTotal(request.getSubTotal());
        detailSale.setUnitPrice(request.getUnitPrice());
        detailSale.setIdProduct(request.getIdProduct());

        // buscamos la venta padre — si no existe, no tiene sentido guardar el detalle
        Sales sale = salesRepository.findById(request.getSales())
                .orElseThrow(() -> new RuntimeException("venta no encontrada"));
        detailSale.setSales(sale);

        detailSalesRepository.save(detailSale);

        // construimos la respuesta con el detalle y la venta embebida
        DetailSaleResponseDTO response = new DetailSaleResponseDTO();
        response.setId(detailSale.getId());
        response.setAmount(detailSale.getAmount());
        response.setUnitPrice(detailSale.getUnitPrice());
        response.setSubTotal(detailSale.getSubTotal());
        response.setIdProduct(detailSale.getIdProduct());

        if (detailSale.getSales() != null) {
            response.setSales(toSaleDTO(detailSale.getSales()));
        }

        return response;
    }

    /**
     * Devuelve todos los detalles de venta con su venta padre embebida.
     * Si un detalle no tiene venta asociada (caso raro), el campo sales queda null.
     */
    public List<DetailSaleResponseDTO> get() {
        List<DetailSale> detailSales = detailSalesRepository.findAll();
        List<DetailSaleResponseDTO> list = new ArrayList<>();

        for (DetailSale detailSale : detailSales) {
            DetailSaleResponseDTO response = new DetailSaleResponseDTO();
            response.setId(detailSale.getId());
            response.setIdProduct(detailSale.getIdProduct());
            response.setAmount(detailSale.getAmount());
            response.setUnitPrice(detailSale.getUnitPrice());
            response.setSubTotal(detailSale.getSubTotal());

            // embebemos la venta solo si existe — si no, dejamos null
            response.setSales(detailSale.getSales() != null ? toSaleDTO(detailSale.getSales()) : null);

            list.add(response);
        }
        return list;
    }

    /**
     * Busca un detalle de venta por su ID.
     * Si no existe lanza excepción con mensaje descriptivo.
     */
    public DetailSaleResponseDTO getForId(Long id) {
        DetailSale detail = detailSalesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("el id del registro de la venta no fue encontrado"));

        DetailSaleResponseDTO responseDTO = new DetailSaleResponseDTO();
        responseDTO.setId(detail.getId());
        responseDTO.setAmount(detail.getAmount());
        responseDTO.setIdProduct(detail.getIdProduct());
        responseDTO.setSubTotal(detail.getSubTotal());
        responseDTO.setUnitPrice(detail.getUnitPrice());

        responseDTO.setSales(detail.getSales() != null ? toSaleDTO(detail.getSales()) : null);

        return responseDTO;
    }

    /**
     * Convierte una entidad Sales a su DTO de respuesta.
     * Método privado reutilizable para no repetir el mismo mapeo en create, get y getForId.
     */
    private SaleResponseDTO toSaleDTO(Sales sale) {
        SaleResponseDTO dto = new SaleResponseDTO();
        dto.setId(sale.getId());
        dto.setDateSale(sale.getDateSale());
        dto.setVat(sale.getVat());
        dto.setTotal(sale.getTotal());
        dto.setState(sale.getState());
        dto.setIdEmpleado(sale.getIdEmpleado());
        dto.setSubTotal(sale.getSubTotal());
        return dto;
    }
}
