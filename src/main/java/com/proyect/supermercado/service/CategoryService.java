package com.proyect.supermercado.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.proyect.supermercado.repository.CategoryRepository;
import com.proyect.supermercado.dto.CategoryResponseDTO;
import com.proyect.supermercado.entity.Category;
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
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        
        return convertToResponseWithProducts(category);
    }

    private CategoryResponseDTO convertToResponse(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    private CategoryResponse convertToResponseWithProducts(Category category) {
        // TODO: Implement conversion with products
        return null;
    }

}
