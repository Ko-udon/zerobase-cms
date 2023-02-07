package com.zerobase.order.domain.repository;

import com.zerobase.order.domain.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

  Optional<Product> findBySellerIdAndId(Long sellerId, Long id);

  @EntityGraph(attributePaths = {"productItems"},type = EntityGraphType.LOAD)
  Optional<Product> findWithProductItemById(Long id);

}
