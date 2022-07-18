package com.mtshop.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    @Test
    public void testEncodePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "thong123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println(encodedPassword);

        // check if there is a match
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);

        assertThat(matches).isTrue();
    }
}
