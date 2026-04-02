package com.proyect.supermercado.service;

import com.proyect.supermercado.dto.RestockRequestDTO;
import com.proyect.supermercado.dto.RestockResponseDTO;
import com.proyect.supermercado.entity.Product;
import com.proyect.supermercado.entity.Restock;
import com.proyect.supermercado.entity.RestockDetail;
import com.proyect.supermercado.entity.Supplier;
import com.proyect.supermercado.repository.ProductRepository;
import com.proyect.supermercado.repository.RestockRepository;
import com.proyect.supermercado.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestockService {

    @Autowired
    private RestockRepository restockRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public RestockResponseDTO createRestock(RestockRequestDTO request) {
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Restock restock = new Restock();
        restock.setDate(LocalDateTime.now());
        restock.setSupplier(supplier);

        List<RestockDetail> details = request.getDetails().stream().map(detailRequest -> {
            Product product = productRepository.findById(detailRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            product.setStock(product.getStock() + detailRequest.getQuantity());
            productRepository.save(product);

            RestockDetail detail = new RestockDetail();
            detail.setProduct(product);
            detail.setQuantity(detailRequest.getQuantity());
            return detail;
        }).collect(Collectors.toList());

        restock.setDetails(details);
        restock = restockRepository.save(restock);

        RestockResponseDTO response = new RestockResponseDTO();
        response.setId(restock.getId());
        response.setDate(restock.getDate());
        response.setSupplierName(supplier.getName());
        response.setDetails(details.stream().map(d -> {
            RestockDetailResponseDTO dto = new RestockDetailResponseDTO();
            dto.setProductName(d.getProduct().getName());
            dto.setQuantity(d.getQuantity());
            return dto;
        }).collect(Collectors.toList()));

        return response;
    }
}