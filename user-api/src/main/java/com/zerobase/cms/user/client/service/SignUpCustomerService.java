package com.zerobase.cms.user.client.service;

import com.zerobase.cms.user.client.domain.SignUpform;
import com.zerobase.cms.user.client.domain.model.Customer;
import com.zerobase.cms.user.client.domain.repository.CustomerRepository;
import com.zerobase.cms.user.client.exception.CustomException;
import com.zerobase.cms.user.client.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

    private final CustomerRepository customRepository;

    public Customer signUp(SignUpform form) {
        return customRepository.save(Customer.from(form));
    }

    public boolean isEmailExist(String email) {
        return customRepository.findByEmail(email.toLowerCase(Locale.ROOT))
            .isPresent();
    }
    @Transactional
    public void verifyEmail(String email, String code) {
        Customer customer = customRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        if (customer.isVerify()) {
            throw new CustomException(ErrorCode.ALREADY_VERIFY);
        } else if (!customer.getVerificationCode().equals(code)) {
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        } else if (customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRE_CODE);
        }
        customer.setVerify(true);

    }

    @Transactional
    public LocalDateTime ChangeCustomerValidateEmail(Long customerId, String verificationCode) {
        Optional<Customer> customerOptional = customRepository.findById(customerId);

        if (customerOptional.isEmpty()) {
            //예외처리
            throw new CustomException(ErrorCode.NOT_FOUND_USER);

        }
        Customer customer = customerOptional.get();
        customer.setVerificationCode(verificationCode);
        customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
        return customer.getVerifyExpiredAt();
    }


}
