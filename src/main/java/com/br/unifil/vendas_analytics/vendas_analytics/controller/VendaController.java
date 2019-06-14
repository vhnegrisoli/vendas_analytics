package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.HistoricoVendaDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.ProdutosDaVendaDto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.*;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.HistoricoVendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.RelatoriosRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.service.RelatorioCsvService;
import com.br.unifil.vendas_analytics.vendas_analytics.service.UsuarioService;
import com.br.unifil.vendas_analytics.vendas_analytics.service.VendaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.lang.*;
import java.util.List;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaAprovacaoEnum.APROVADA;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum.FECHADA;

@CrossOrigin
@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private HistoricoVendaRepository historicoVendaRepository;

    @Autowired
    private VendaService vendaService;

    @Autowired
    private RelatorioCsvService relatorioCsvService;

    @Autowired
    private RelatoriosRepository relatoriosRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/todas")
    public List<Venda> getAllVendas() {
        return vendaService.buscarTodas();
    }

    @GetMapping("/{id}")
    public Venda getUmaVenda(@PathVariable int id) throws ValidationException {
        return vendaService.buscarUma(id);
    }

    @PostMapping("/salvar")
    public void save(@RequestBody Venda venda) {
        vendaService.save(venda);
    }

    @GetMapping("/historico-de-vendas")
    public List<HistoricoVendaDto> getAllHistoricos() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return historicoVendaRepository.historicoDeVenda(usuarioLogado.getId(), usuarioLogado.isSuperAdmin());
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
    public Integer getAllVendasRealizadas() {
        return vendaRepository.countBySituacaoAndAprovacao(FECHADA, APROVADA);
    }

    @GetMapping("/vendas-nao-realizadas")
    public Integer getAllVendasNaoRealizadas() {
        return vendaRepository.countByAprovacaoNot(APROVADA);
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
        return vendaService.getProdutosDaVenda(id, usuarioService.getUsuarioLogado().getId());
    }

}
