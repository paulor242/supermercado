package com.proyect.supermercado.service;

import java.util.stream.Collectors;

import org.junit.experimental.categories.Category;
import org.springframework.stereotype.Service;

import com.proyect.supermercado.repository.CategoryRepository;
import com.proyect.supermercado.repository.ProductRepository;
import com.proyect.supermercado.dto.ProductRequestDTO;
import com.proyect.supermercado.dto.ProductResponseDTO;
import com.proyect.supermercado.entity.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    /**
     * Crea un nuevo producto
     * Valida unicidad de código de barras
     */
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        // Regla de Negocio 2: Validar unicidad de barcode
        if (productRepository.existsByBarcode(request.getBarcode())) {
            throw new RuntimeException("Ya existe un producto con el código de barras: " + request.getBarcode());
        }

        // Verificar que la categoría existe y está activa
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + request.getCategoryId()));

        if (!category.isActive()) {
            throw new RuntimeException("La categoría está inactiva");
        }

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .barcode(request.getBarcode())
                .price(request.getPrice())
                .stock(request.getStock())
                .active(request.getActive() != null ? request.getActive() : true)
                .category(category)
                .build();

        product = productRepository.save(product);
        return convertToResponse(product);
    }


    /**
     * Obtiene todos los productos activos
     */
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findByActiveTrueOrderByNameAsc()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un producto por ID
     */
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        
        if (!product.isActive()) {
            throw new RuntimeException("El producto está inactivo/eliminado");
        }
        
        return convertToResponse(product);
    }

     /**
     * Busca productos por código de barras
     * Regla de Negocio 2: Validación de unicidad
     */
    public ProductResponseDTO getProductByBarcode(String barcode) {
        Product product = productRepository.findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con barcode: " + barcode));
        
        return convertToResponse(product);
    }

    /**
     * Obtiene productos por categoría
     */
    public List<ProductResponseDTO> getProductsByCategory(Long categoryId) {
        // Verificar que la categoría existe
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoryId));

        return productRepository.findByCategoryIdAndActiveTrueOrderByNameAsc(categoryId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }



}
