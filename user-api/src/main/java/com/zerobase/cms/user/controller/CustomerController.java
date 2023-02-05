package com.zerobase.cms.user.controller;

import com.zerobase.cms.user.client.domain.customer.ChangeBalanceFrom;
import com.zerobase.cms.user.client.domain.customer.CustomerDto;
import com.zerobase.cms.user.client.domain.model.Customer;
import com.zerobase.cms.user.client.exception.CustomException;
import com.zerobase.cms.user.client.exception.ErrorCode;
import com.zerobase.cms.user.client.service.customer.CustomerBalanceService;
import com.zerobase.cms.user.client.service.customer.CustomerService;
import com.zerobase.domain.common.UserVo;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customer")
public class CustomerController {

  private final JwtAuthenticationProvider provider;

  private final CustomerService customerService;

  private final CustomerBalanceService customerBalanceService;

  @GetMapping("/getInfo")
  public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
    UserVo vo = provider.getUserVo(token);
    Customer c = customerService.findByIdAndEmail(vo.getId(), vo.getEmail()).orElseThrow(
        () -> new CustomException(ErrorCode.NOT_FOUND_USER));

    return ResponseEntity.ok(CustomerDto.from(c));

  }

  @PostMapping("/balance")
  public ResponseEntity<Integer> changeBalance(@RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestBody ChangeBalanceFrom from) {
    //ㅇㄹ
    return null;

  }


}
