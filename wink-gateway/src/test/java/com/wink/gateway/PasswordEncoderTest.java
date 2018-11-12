package com.wink.gateway;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest extends BaseTest {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void encode() {
        String pwd = "admin";
        final String encodePwd = passwordEncoder.encode(pwd);
        prettyResult(encodePwd);
    }
}