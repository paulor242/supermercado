package com.proyect.supermercado.service;

import com.proyect.supermercado.dto.DetailSaleRequestDTO;
import com.proyect.supermercado.dto.DetailSaleResponseDTO;
import com.proyect.supermercado.dto.SaleResponseDTO;
import com.proyect.supermercado.entity.DetailSale;
import com.proyect.supermercado.entity.Product;
import com.proyect.supermercado.entity.Sales;
import com.proyect.supermercado.repository.DetailSalesRepository;
import com.proyect.supermercado.repository.ProductRepository;
import com.proyect.supermercado.repository.SalesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Transactional
@RequiredArgsConstructor
@Service
public class DetailSaleService {
    private final DetailSalesRepository detailSalesRepository;
    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;

    public DetailSaleResponseDTO create(DetailSaleRequestDTO request) {
        Product product = productRepository.findById(request.getIdProduct())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        if (product.getStock() < request.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Stock insuficiente. Disponible: " + product.getStock() + ", solicitado: " + request.getAmount());
        }

        Double unitPrice = product.getPrice();
        BigDecimal subTotal = BigDecimal.valueOf(request.getAmount()).multiply(BigDecimal.valueOf(unitPrice));
        BigDecimal vat = subTotal.multiply(new BigDecimal("0.19"));
        BigDecimal total = subTotal.add(vat);

        DetailSale detailSale = new DetailSale();
        detailSale.setAmount(request.getAmount());
        detailSale.setSubTotal(request.getSubTotal());
        detailSale.setUnitPrice(request.getUnitPrice());



        Sales sale = salesRepository.findById(request.getSales())
                .orElseThrow(() -> new RuntimeException("venta no encontrada"));
        detailSale.setSales(sale);

        detailSalesRepository.save(detailSale);

        product.setStock(product.getStock()-request.getAmount());
        productRepository.save(product);

        DetailSaleResponseDTO response = new DetailSaleResponseDTO();
        response.setId(detailSale.getId());
        response.setAmount(detailSale.getAmount());
        response.setUnitPrice(detailSale.getUnitPrice());
        response.setSubTotal(detailSale.getSubTotal());

        if (detailSale.getSales() != null) {
            SaleResponseDTO detailDTO = new SaleResponseDTO();
            detailDTO.setId(detailSale.getSales().getId());
            detailDTO.setSubTotal(detailSale.getSubTotal());
            detailDTO.setVat(detailSale.getSales().getVat());
            detailDTO.setDateSale(detailSale.getSales().getDateSale());
            detailDTO.setTotal(detailSale.getSales().getTotal());
            detailDTO.setState(detailSale.getSales().getState());
            response.setSales((detailDTO));
        }


        return response;
    }

    public List<DetailSaleResponseDTO> get() {
        List<DetailSale> detailSales = detailSalesRepository.findAll();
        List<DetailSaleResponseDTO> list = new ArrayList<>();

        for (DetailSale detailSale : detailSales) {
            DetailSaleResponseDTO response = new DetailSaleResponseDTO();
            response.setId(detailSale.getId());

            response.setAmount(detailSale.getAmount());
            response.setUnitPrice(detailSale.getUnitPrice());
            response.setSubTotal(detailSale.getSubTotal());
            if (detailSale.getSales() != null) {
                SaleResponseDTO responseDTO = new SaleResponseDTO();
                responseDTO.setId(detailSale.getSales().getId());
                responseDTO.setVat(detailSale.getSales().getVat());
                responseDTO.setDateSale(detailSale.getSales().getDateSale());
                responseDTO.setState(detailSale.getSales().getState());
                responseDTO.setSubTotal(detailSale.getSales().getSubTotal());
                responseDTO.setTotal(detailSale.getSales().getTotal());
                response.setSales(responseDTO);
            } else {
                response.setSales(null);
            }
            list.add(response);
        }
        return list;
    }

    public DetailSaleResponseDTO getForId(Long id) {
        DetailSale detail = detailSalesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("el id del registro de la venta no fue encontrado "));
        DetailSaleResponseDTO responseDTO = new DetailSaleResponseDTO();
        responseDTO.setId(detail.getId());
        responseDTO.setAmount(detail.getAmount());
        responseDTO.setSubTotal(detail.getSubTotal());
        responseDTO.setUnitPrice(detail.getUnitPrice());
        if (detail.getSales() != null) {
            SaleResponseDTO response = new SaleResponseDTO();
            response.setId(detail.getSales().getId());
            response.setDateSale(detail.getSales().getDateSale());
            response.setVat(detail.getSales().getVat());
            response.setTotal(detail.getSales().getTotal());
            response.setState(detail.getSales().getState());
            response.setSubTotal(detail.getSales().getSubTotal());
            responseDTO.setSales(response);
        } else {
            responseDTO.setSales(null);
        }
        return responseDTO;
    }

    public Optional<DetailSaleResponseDTO> detailsUpdate(Long id, DetailSaleRequestDTO requestDTO) {
        Optional<DetailSale> optionalDetail = detailSalesRepository.findById(id);
        if (optionalDetail.isPresent()) {
            DetailSale detail = optionalDetail.get();

            detail.setAmount(requestDTO.getAmount());
            detail.setUnitPrice(requestDTO.getUnitPrice());


            if (requestDTO.getSales() != null) {
                Sales sale = salesRepository.findById(requestDTO.getSales())
                        .orElseThrow(() -> new RuntimeException("venta no encontrada con ese id"));
                detail.setSales(sale);
            }

            DetailSale detailUpdate = detailSalesRepository.save(detail);

            DetailSaleResponseDTO response = new DetailSaleResponseDTO();
            response.setId(detailUpdate.getId());
            response.setAmount(detailUpdate.getAmount());
            response.setUnitPrice(detailUpdate.getUnitPrice());
            response.setSubTotal(detailUpdate.getSubTotal());

            if (detailUpdate.getSales() != null) {
                SaleResponseDTO responseDTO = new SaleResponseDTO();
                responseDTO.setId(detailUpdate.getSales().getId());
                responseDTO.setVat(detailUpdate.getSales().getVat());
                responseDTO.setDateSale(detailUpdate.getSales().getDateSale());
                responseDTO.setTotal(detailUpdate.getSales().getTotal());
                responseDTO.setState(detail.getSales().getState());
                responseDTO.setSubTotal(detailUpdate.getSales().getSubTotal());
                response.setSales(responseDTO);
            } else {
                response.setSales(null);
            }
            return Optional.of(response);
        }
        return Optional.empty();
    }

    public Boolean delete(Long id) {
        Optional<DetailSale> detail = detailSalesRepository.findById(id);

        if (detail.isPresent()) {
            detailSalesRepository.delete(detail.get());
            return true;
        } else {
            return false;
        }
    }
}