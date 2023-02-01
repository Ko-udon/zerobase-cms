package com.zerobase.cms.user.client.service;

import com.zerobase.cms.user.client.MailgunClient;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailSendServiceTest {

    @Autowired
    private MailgunClient mailgunClient;

    @Test
    public void EmailTest() {
        SendMailForm form = SendMailForm.builder()
            .from("보내는 메일@gmail.com")
            .subject("메일 제목")
            .text("메일 내용")
            .to("받는매일@gmail.com")
            .build();
        mailgunClient.sendEmail(form);
    }

}