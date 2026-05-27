package com.example.annualleave.app.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendMail(String email,String link){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("연차관리 서비스 초대");
        message.setText("초대 링크: " + link);
        mailSender.send(message);
    }
}
