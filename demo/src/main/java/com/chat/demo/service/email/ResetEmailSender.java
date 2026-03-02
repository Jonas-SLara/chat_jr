package com.chat.demo.service.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResetEmailSender {

    private final JavaMailSender mailSender;

    public void sendRecoveryCode(String to, String code){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Código de recuperação de Senha");
        message.setText("Seu Código de Verificação é: " + code + "\n"
            + "Ele Expira em 15 Minutos"
        );
        mailSender.send(message);
    }

}
