package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.model.RelatoriosPowerBi;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.PowerBiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PowerBiService {

    @Autowired
    private PowerBiRepository powerBiRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<RelatoriosPowerBi> findByUsuario(Integer id) {
        return powerBiRepository.findByUsuario(usuarioService.buscarUm(id));
    }
}
