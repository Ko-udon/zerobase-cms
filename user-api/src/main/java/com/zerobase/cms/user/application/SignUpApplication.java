package com.zerobase.cms.user.application;

import com.zerobase.cms.user.client.MailgunClient;
import com.zerobase.cms.user.client.domain.SignUpform;
import com.zerobase.cms.user.client.domain.model.Customer;
import com.zerobase.cms.user.client.domain.model.Seller;
import com.zerobase.cms.user.client.exception.CustomException;
import com.zerobase.cms.user.client.exception.ErrorCode;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import com.zerobase.cms.user.client.service.customer.SignUpCustomerService;
import com.zerobase.cms.user.client.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpApplication {

    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;
    private final SellerService sellerService;

    public void customerVerify(String email,String code) {
        signUpCustomerService.verifyEmail(email,code);
    }

    public String customerSignUp(SignUpform form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            //exception
            throw new CustomException(ErrorCode.Already_REGISTED_ACCOUNT);
        } else {
            Customer c = signUpCustomerService.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("ehddn2202@gmail.com")
                    .to(form.getEmail())
                    .subject("Verification Email!")
                    .text(getVerificationEmailBody(c.getEmail(),c.getName(),"customer", code))
                    .build();

            mailgunClient.sendEmail(sendMailForm);
            signUpCustomerService.ChangeCustomerValidateEmail(c.getId(),code);
            return "회원 가입에 성공하였습니다.";
        }
    }

    public void sellerVerify(String email,String code) {
        sellerService.verifyEmail(email,code);
    }

    public String sellerSignUp(SignUpform form) {

        if (sellerService.isEmailExist(form.getEmail())) {
            //exception
            throw new CustomException(ErrorCode.Already_REGISTED_ACCOUNT);
        } else {
            Seller seller = sellerService.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                .from("ehddn2202@gmail.com")
                .to(form.getEmail())
                .subject("Verification Email!")
                .text(getVerificationEmailBody(seller.getEmail(),seller.getName(),"customer", code))
                .build();

            log.info("Send email result: "+mailgunClient.sendEmail(sendMailForm));
            sellerService.ChangeCustomerValidateEmail(seller.getId(),code);
            return "회원 가입에 성공하였습니다.";
        }


    }

    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String type, String code) {
        StringBuilder sb = new StringBuilder();
        return sb.append("Hello").append(name).append("! Please Click Link to verification. \n\n")
            .append("http://localshot:8080/signup/"+type+"verify?email=")
            .append(email)
            .append("&code=")
            .append(code).toString();
    }

}
