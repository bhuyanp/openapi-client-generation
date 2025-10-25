package io.github.bhuyanp.restapp.repo;

import io.github.bhuyanp.restapp.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Crud repository for {@link ProductEntity}
 *
 * @author Prasanta Bhuyan
 */
@Repository
public interface ProductsRepo extends JpaRepository<ProductEntity, String> {
}
