package com.br.unifil.vendas_analytics.vendas_analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(
		basePackageClasses = { VendasAnalyticsApplication.class, Jsr310JpaConverters.class })
@SpringBootApplication
public class VendasAnalyticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendasAnalyticsApplication.class, args);
	}

}
