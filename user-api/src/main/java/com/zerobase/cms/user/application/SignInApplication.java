package com.zerobase.cms.user.application;

import com.zerobase.cms.user.client.domain.SignInform;
import com.zerobase.cms.user.client.domain.model.Customer;
import com.zerobase.cms.user.client.domain.model.Seller;
import com.zerobase.cms.user.client.exception.CustomException;
import com.zerobase.cms.user.client.exception.ErrorCode;
import com.zerobase.cms.user.client.service.customer.CustomerService;
import com.zerobase.cms.user.client.service.seller.SellerService;
import com.zerobase.domain.common.UserType;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;
    private final SellerService sellerService;
    private final JwtAuthenticationProvider provider;

    public String customerLoginToken(SignInform form) {
        //1. 로그인 가능 여부
        Customer c = customerService.findValidCustomer(form.getEmail(), form.getPassword())
            .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));
        //2. 토큰을 발행
        //3. 토큰을 Response

        return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
    }

    public String sellerLoginToken(SignInform form) {
        //1. 로그인 가능 여부
        Seller s = sellerService.findValidSeller(form.getEmail(), form.getPassword())
            .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));
        //2. 토큰을 발행
        //3. 토큰을 Response

        return provider.createToken(s.getEmail(), s.getId(), UserType.SELLER);
    }

}
