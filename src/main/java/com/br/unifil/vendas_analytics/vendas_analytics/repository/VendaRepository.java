package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Venda;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Integer> {

    List<Venda> findByVendedor(Vendedor vendedor);

    List<Venda> findByVendedorIdIn(List<Integer> vendedoresId);

    @Procedure(procedureName = "ATUALIZA_DATA_WAREHOUSE")
    void atualizarDataWarehouse();
}
