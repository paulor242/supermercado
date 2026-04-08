package com.proyect.supermercado.repository;
import com.proyect.supermercado.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
/**
 * Repositorio para la entidad Category
 * Proporciona métodos para operaciones CRUD y consultas personalizadas
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Busca una categoría por nombre (automático por convención de nombres)
     */
    Optional<Category> findByName(String name);

    /**
     * Verifica si existe una categoría por nombre (automático)
     */
    boolean existsByName(String name);

    /**
     * Busca todas las categorías activas ordenadas por nombre
     */
    List<Category> findByActiveTrueOrderByNameAsc();

 
}
