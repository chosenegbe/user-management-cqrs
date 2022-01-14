package com.springbank.user.cmd.api.security.impl;

import com.springbank.user.cmd.api.security.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderImpl implements PasswordEncoder {

    @Override
    public String hashPassword(String password) {
        var encoder = new BCryptPasswordEncoder(12);
        var hashPassword = encoder.encode(password);

        return hashPassword;
    }
}
