package com.proyect.supermercado.service;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.proyect.supermercado.repository.CategoryRepository;
import com.proyect.supermercado.dto.CategoryRequestDTO;
import com.proyect.supermercado.dto.CategoryResponseDTO;
import com.proyect.supermercado.entity.Category;
import com.proyect.supermercado.entity.Product;

import java.util.ArrayList;
import java.util.List;  
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Obtiene todas las categorías activas
     */
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findByActiveTrueOrderByNameAsc()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una categoría por ID con sus productos vinculados
     * Regla de Negocio 3: Incluir lista de productos
     */
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        
        return convertToResponseWithProducts(category);
    }

    /**
     * Crea una nueva categoría
     */
    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO request) {
        // Validar que no exista categoría con el mismo nombre
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Ya existe una categoría con el nombre: " + request.getName());
        }

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .active(request.getActive() != null ? request.getActive() : true)
                .build();

        category = categoryRepository.save(category);
        return convertToResponse(category);
    }

    
    /**
     * Actualiza una categoría existente
     */
    @Transactional
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));

        // Validar que el nuevo nombre no exista en otras categorías
        if (!category.getName().equals(request.getName()) && 
            categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Ya existe una categoría con el nombre: " + request.getName());
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        if (request.getActive() != null) {
            category.setActive(request.getActive());
        }

        category = categoryRepository.save(category);
        return convertToResponse(category);
    }

    /**
     * Aplica borrado lógico en una categoría
     * Regla de Negocio 1: Soft Delete
     */
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        
        // Soft delete: solo desactivar, no eliminar físicamente
        category.setActive(false);
        categoryRepository.save(category);
    }

    /**
     * Convierte entidad a DTO de respuesta
     */
    private CategoryResponseDTO convertToResponse(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .active(category.getActive())
                .build();
    }

    /**
     * Convierte entidad a DTO de respuesta incluyendo productos
     * Maneja correctamente la colección de productos para evitar null
     */
    private CategoryResponseDTO convertToResponseWithProducts(Category category) {
        // Obtener productos de la categoría, si es null usar lista vacía
        List<Product> categoryProducts = category.getProducts();
        if (categoryProducts == null) {
            categoryProducts = new ArrayList<>();
        }

        List<CategoryResponseDTO.ProductSummary> productSummaries = categoryProducts.stream()
                .filter(product -> product.getActive() != null && product.getActive())
                .map(product -> CategoryResponseDTO.ProductSummary.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .barcode(product.getBarcode())
                        .active(product.getActive())
                        .build())
                .collect(Collectors.toList());

        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .active(category.getActive())
                .products(productSummaries)
                .build();
    }

   
}
