package com.zerobase.cms.user.client.service.seller;

import com.zerobase.cms.user.client.domain.SignUpform;
import com.zerobase.cms.user.client.domain.model.Customer;
import com.zerobase.cms.user.client.domain.model.Seller;
import com.zerobase.cms.user.client.domain.repository.SellerRepository;
import com.zerobase.cms.user.client.exception.CustomException;
import com.zerobase.cms.user.client.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    public Optional<Seller> findByIdAndEmail(Long id, String email) {
        return sellerRepository.findByIdAndEmail(id, email);
    }

    public Optional<Seller> findValidSeller(String email, String password) {
        return sellerRepository.findByEmailAndPasswordAndVerifyIsTrue(email, password);
    }

    //seller 회원가입
    public Seller signUp(SignUpform form) {
        return sellerRepository.save(Seller.from(form));
    }

    //seller 로그인
    public boolean isEmailExist(String email) {
        return sellerRepository.findByEmail(email).isPresent();
    }

    public void verifyEmail(String email, String code) {
        Seller seller = sellerRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        if (seller.isVerify()) {
            throw new CustomException(ErrorCode.ALREADY_VERIFY);
        } else if (!seller.getVerificationCode().equals(code)) {
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        } else if (seller.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRE_CODE);
        }
        seller.setVerify(true);
    }

    @Transactional
    public LocalDateTime ChangeCustomerValidateEmail(Long customerId, String verificationCode) {
        Optional<Seller> sellerOptional = sellerRepository.findById(customerId);

        if (sellerOptional.isEmpty()) {
            //예외처리
            throw new CustomException(ErrorCode.NOT_FOUND_USER);

        }
        Seller seller = sellerOptional.get();
        seller.setVerificationCode(verificationCode);
        seller.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
        return seller.getVerifyExpiredAt();
    }



}
