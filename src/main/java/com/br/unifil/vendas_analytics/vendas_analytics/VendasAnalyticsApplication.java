package com.br.unifil.vendas_analytics.vendas_analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;

@EntityScan(
		basePackageClasses = { VendasAnalyticsApplication.class, Jsr310JpaConverters.class })
@SpringBootApplication
public class VendasAnalyticsApplication {
	public static void main(String[] args) {
		SpringApplication.run(VendasAnalyticsApplication.class, args);
	}
}
