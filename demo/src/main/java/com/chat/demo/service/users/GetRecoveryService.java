package com.chat.demo.service.users;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chat.demo.exceptions.CodeException;
import com.chat.demo.model.users.RecoveryCode;
import com.chat.demo.model.users.Users;
import com.chat.demo.repository.RecoveryCodeRepository;
import com.chat.demo.repository.UserRepository;
import com.chat.demo.service.email.ResetEmailSender;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetRecoveryService {
    private final UserRepository userRepository;
    private final ResetEmailSender resetEmailSender;
    private final RecoveryCodeRepository recoveryCodeRepository;
    private final PasswordEncoder passwordEncoder;

    public void generateRecoveryCode(String email){
        this.userRepository.findByEmail(email).orElseThrow(
            () -> new RuntimeException("email inválido")
        );

        recoveryCodeRepository.deleteByEmail(email);

        String code = String.format("%06d", new Random().nextInt(999999));

        RecoveryCode recoveryCode = new RecoveryCode();
        recoveryCode.setCode(code);
        recoveryCode.setCreatedAt(new Date());
        recoveryCode.setEmail(email);

        recoveryCodeRepository.save(recoveryCode);

        resetEmailSender.sendRecoveryCode(email, code);
    }

    public boolean validateCode(String email, String code) {
        boolean isValid = recoveryCodeRepository.findByEmailAndCode(email, code).isPresent();
        if (!isValid) {
            throw new CodeException("Código: " + code + " Inválido ou expirado");
        }
        return true;
    }

    @Transactional
    public void resetPassword(String email, String code, String newPassword) {
        if(!validateCode(email, code)) {
            throw new CodeException("Código: " + code + " Inválido ou expirado");
        }

        Users user = userRepository.findByEmail(email).orElseThrow(
            () -> new NoSuchElementException("usuario nao encontrado")
        );

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        recoveryCodeRepository.deleteByEmail(email);

    }
}
