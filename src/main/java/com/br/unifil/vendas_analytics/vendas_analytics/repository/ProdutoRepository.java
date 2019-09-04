package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Categoria;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Fornecedor;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    Optional<Produto> findByIdAndUsuarioCadastroIn(Integer id, List<Integer> usuariosCadastro);

    List<Produto> findByUsuarioCadastroIn(List<Integer> usuariosCadastro);

    Integer countByUsuarioCadastro(Integer usuarioCadastro);

    Boolean existsByFornecedor(Fornecedor fornecedor);

    Boolean existsByCategoria(Categoria categoria);
}
