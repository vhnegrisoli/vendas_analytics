package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DwService {

    @Autowired
    private VendaRepository vendaRepository;

    @Scheduled(cron = "30 12 * * * *")
    private void atualizarDataWarehouse() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:ss");
        System.out.println("Iniciando atualização do Data Warehouse às " + LocalDateTime.now().format(formatter));
        vendaRepository.atualizarDataWarehouse();
    }
}
