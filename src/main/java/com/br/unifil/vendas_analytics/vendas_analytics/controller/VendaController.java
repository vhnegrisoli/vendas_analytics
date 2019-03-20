package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.ProdutosDaVendaDto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.*;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.HistoricoVendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ProdutoVendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.service.RelatorioCsvService;
import com.br.unifil.vendas_analytics.vendas_analytics.service.VendaService;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.security.Timestamp;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaAprovacaoEnum.APROVADA;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaAprovacaoEnum.REJEITADA;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum.FECHADA;
import static java.lang.System.currentTimeMillis;

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

    @Autowired
    ProdutoVendaRepository produtoVendaRepository;

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
        vendaService.save(venda);
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
        return vendaRepository.findByAprovacao(REJEITADA);
    }

    @GetMapping("/aprovar-venda/{id}")
    public void aprovarVenda(@PathVariable int id) {
        vendaService.aprovarVenda(id);
    }

    @GetMapping("/rejeitar-venda/{id}")
    public void rejeitarVenda(@PathVariable int id) {
        vendaService.rejeitarVenda(id);
    }

    @GetMapping("/vendas-produtos/{id}")
    public List<ProdutosDaVendaDto> getProdutosDaVenda(@PathVariable int id) {
        return vendaRepository.findAllProdutosDaVendaByVendaId(id);
    }

}
