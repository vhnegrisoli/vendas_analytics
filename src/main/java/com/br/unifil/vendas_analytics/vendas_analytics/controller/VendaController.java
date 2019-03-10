package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.HistoricoVenda;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Venda;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.HistoricoVendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.service.RelatorioCsvService;
import com.br.unifil.vendas_analytics.vendas_analytics.service.VendaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaAprovacaoEnum.APROVADA;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum.FECHADA;

@CrossOrigin
@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    VendaRepository vendaRepository;

    @Autowired
    HistoricoVendaRepository historicoVendaRepository;

    @Autowired
    VendaService vendaService;

    @Autowired
    RelatorioCsvService relatorioCsvService;

    @GetMapping("/todas")
    public List<Venda> getAllVendas() {
        return vendaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Venda getUmaVenda(@PathVariable int id) throws ValidationException {
        return vendaRepository.findById(id).orElseThrow(
                () -> new ValidationException("Venda não existente"));
    }

    @PostMapping("/salvar")
    public void save(@RequestBody Venda venda) {
        vendaRepository.save(venda);
    }

    @GetMapping("/historico-de-vendas")
    public List<HistoricoVenda> getAllHistoricos() {
        return historicoVendaRepository.findAll();
    }

    @RequestMapping(value = "/relatorio-csv", produces = "text/csv", method = RequestMethod.GET)
    public @ResponseBody String getCsv(@Valid String dataInicial, @Valid String dataFinal,
                                     HttpServletResponse response) throws JsonProcessingException {
        response.setContentType("application/octet-stream");
        response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Content-Disposition","attachment; filename=relatorio_geral_vendas.csv");
        return relatorioCsvService.gerarCsv(dataInicial, dataFinal);
    }

    @GetMapping("/vendas-realizadas")
    public List<Venda> getAllVendasRealizadas() {
        return vendaRepository.findBySituacaoAndAprovacao(FECHADA, APROVADA);
    }

    @GetMapping("/vendas-nao-realizadas")
    public List<Venda> getAllVendasNaoRealizadas() {
        return vendaRepository.findBySituacaoNot(FECHADA);
    }

}
