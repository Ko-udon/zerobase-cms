package com.zerobase.order.service;

import com.zerobase.order.domain.model.Product;
import com.zerobase.order.domain.repository.ProductRepository;
import com.zerobase.order.exception.CustomException;
import com.zerobase.order.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

  private final ProductRepository productRepository;

  public List<Product> searchByName(String name){
    return productRepository.searchByName(name);
  }

  public Product getByProductId(Long productId) {
    return productRepository.findWithProductItemById(productId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
  }

  public List<Product> getListByProductIds(List<Long> productIds) {
    return productRepository.findAllById(productIds);
  }

}
