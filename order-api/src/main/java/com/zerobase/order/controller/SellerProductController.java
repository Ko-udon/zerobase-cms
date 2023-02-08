package com.zerobase.order.controller;

import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.order.domain.product.AddProductForm;
import com.zerobase.order.domain.product.AddProductItemForm;
import com.zerobase.order.domain.product.ProductDto;
import com.zerobase.order.domain.product.ProductItemDto;
import com.zerobase.order.domain.product.UpdateProductForm;
import com.zerobase.order.domain.product.UpdateProductItemForm;
import com.zerobase.order.service.ProductItemService;
import com.zerobase.order.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller/product")
@RequiredArgsConstructor
public class SellerProductController {

  private final ProductService productService;
  private final ProductItemService productItemService;
  private final JwtAuthenticationProvider provider;


  @PostMapping
  public ResponseEntity<ProductDto> addProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestBody AddProductForm form) {

    return ResponseEntity.ok(
        ProductDto.from(productService.addProduct(provider.getUserVo(token).getId(), form)));
  }

  @PostMapping("/item")
  public ResponseEntity<ProductDto> addProductItem(
      @RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestBody AddProductItemForm form) {

    return ResponseEntity.ok(ProductDto.from(
        productItemService.addProductItem(provider.getUserVo(token).getId(), form)));
  }

  @PutMapping
  public ResponseEntity<ProductDto> updateProductItem(
      @RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestBody UpdateProductForm form) {
    return ResponseEntity.ok(ProductDto.from(
        productService.updateProduct(provider.getUserVo(token).getId(), form)));

  }


  @PutMapping("/item")
  public ResponseEntity<ProductItemDto> addProduct(
      @RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestBody UpdateProductItemForm form) {
    return ResponseEntity.ok(ProductItemDto.from(
        productItemService.updateProductItem(provider.getUserVo(token).getId(), form)));

  }

}