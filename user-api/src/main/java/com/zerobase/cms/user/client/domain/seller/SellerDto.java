package com.zerobase.cms.user.client.domain.seller;

import com.zerobase.cms.user.client.domain.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SellerDto {

  private Long id;
  private String email;
  private Integer balance;

  public static SellerDto from(Seller seller) {
    return new SellerDto(seller.getId(), seller.getEmail(),
        seller.getBalance() == null ? 0 : seller.getBalance());
  }

}
