package com.zerobase.cms.user.client.service;

import static org.junit.jupiter.api.Assertions.*;

import com.zerobase.cms.user.client.domain.SignUpform;
import com.zerobase.cms.user.client.domain.model.Customer;
import com.zerobase.cms.user.client.service.customer.SignUpCustomerService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SignUpServiceTest {

    @Autowired
    private SignUpCustomerService signUpCustomerService;

    @Test
    void signUp() {
        SignUpform form = SignUpform.builder()
            .name("name")
            .birth(LocalDate.now())
            .email("abc@gmail.com")
            .password("1")
            .phone("01000000000")
            .build();
        Customer c = signUpCustomerService.signUp(form);
        assertNotNull(c.getId());
        assertNotNull(c.getCreatedAt());
    }

}