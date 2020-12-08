package com.miracle.payment.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class BCryptUtil {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String hash = passwordEncoder.encode("root");
        log.info("password---{}",hash);
        boolean flag = passwordEncoder.matches("root", hash);
        log.info(String.valueOf(flag));
    }

}
