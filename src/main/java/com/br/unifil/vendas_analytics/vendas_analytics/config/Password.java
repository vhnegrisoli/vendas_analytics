package com.br.unifil.vendas_analytics.vendas_analytics.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Password {

    public static void main (String args []) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("tataialinda123"));
    }
}
