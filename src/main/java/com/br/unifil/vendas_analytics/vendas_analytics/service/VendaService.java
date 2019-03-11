package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.model.*;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ClienteRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ProdutoVendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendaRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaAprovacaoEnum.AGUARDANDO_APROVACAO;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaAprovacaoEnum.APROVADA;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum.ABERTA;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum.FECHADA;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum.FINALIZADA;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoVendaRepository produtoVendaRepository;

    public void save(Venda venda) {
        AtomicReference<Integer> id = new AtomicReference<>(null);
        List<ProdutoVenda> produtos = venda.getProdutos();
        venda.setDataCompra(null);
        LocalDate date = LocalDate.now();
        String mes = date.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt"));
        LocalDateTime dataCompra = LocalDateTime.now();
        venda.setDataCompra(dataCompra);
        venda.setMesCompra(mes);
        Venda vendaCadastrar = venda;
        vendaCadastrar.setProdutos(null);
        vendaRepository.save(vendaCadastrar);
        id.set(vendaCadastrar.getId());
        produtos.forEach(
            produto -> {
                ProdutoVendaId pk = new ProdutoVendaId();
                pk.setProdutoId(produto.getId().getProdutoId());
                pk.setVendaId(id.get());
                produto.setId(pk);
                produtoVendaRepository.save(produto);
            }
        );
    }

    public String getMes(String mes) {
        switch(mes) {
            case "FEBUARY":
                mes = "Fevereiro";
                break;

        }
        return mes;
    }


    public void valindarVendaFechadaOuConcluida(Venda venda) {
        if (venda.getSituacao().equals(FECHADA)
            || venda.getSituacao().equals(FINALIZADA)) {
            throw new ValidationException("A venda está " + venda.getSituacao() + ", ou seja, " +
                    "não é possível fazer alterações.");
        }
    }

    public Venda validarInformacoesVenda(Venda venda) {
        if (venda.getAprovacao().equals(APROVADA)) {
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
//            venda.setDataCompra(date);
            venda.setSituacao(ABERTA);
        } else if (venda.getAprovacao().equals(AGUARDANDO_APROVACAO)) {

        }

        return venda;
    }

    public void validarRejeicaoVenda(Venda venda) {

    }

    public void validarClienteComUsuarioAtivo(Cliente cliente) {

        Usuario usuario = usuarioRepository.findByClienteId(cliente.getId())
                .orElseThrow(() -> new ValidationException("Usuário não existente"));
        if (!usuario.getSituacao().equals(ATIVO)) {
            throw new ValidationException("Não é possível salvar uma venda de um cliente que não possua um usuário" +
                    " ATIVO.");
        }
    }



}
