package com.zerobase.order.controller;

import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.order.domain.product.ProductDto;
import com.zerobase.order.service.ProductSearchService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/product")
@RequiredArgsConstructor
public class SearchController {
  private final ProductSearchService productSearchService;
  private final JwtAuthenticationProvider provider;

  @GetMapping
  public ResponseEntity<List<ProductDto>> searchByName(@RequestParam String name){
    return ResponseEntity.ok(
        productSearchService.searchByName(name).stream()
            .map(ProductDto::withtouItemsfrom).collect(Collectors.toList())
    );
  }

  @GetMapping("/detail")
  public ResponseEntity<ProductDto> getDetail(@RequestParam Long productId){
    return ResponseEntity.ok(
        ProductDto.from(productSearchService.getByProductId(productId))
    );
  }

}
