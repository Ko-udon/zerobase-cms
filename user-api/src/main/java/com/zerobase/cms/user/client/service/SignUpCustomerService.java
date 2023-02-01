package com.zerobase.cms.user.client.service;

import com.zerobase.cms.user.client.domain.SignUpform;
import com.zerobase.cms.user.client.domain.model.Customer;
import com.zerobase.cms.user.client.domain.repository.CustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

    private final CustomRepository customRepository;

    public Customer signUp(SignUpform form) {
        return customRepository.save(Customer.from(form));
    }

}
