package br.com.lince.hackathon.locacao;

import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface LocacoesRepository {
    @RegisterBeanMapper(Locacao.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * FROM tb_locacoes ORDER BY id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Locacao> selectPage(@Define("page") int page, @Define("count") int count);

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM tb_locacoes")
    int count();

    @RegisterBeanMapper(Locacao.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * FROM tb_locacoes WHERE id = :id")
    Locacao findById(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM tb_locacoes WHERE id = :id), 1, 0)")
    boolean exists(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM tb_locacoes WHERE id = :id")
    void delete(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO tb_locacoes(cliente, gerente_responsavel, veiculo, data_inicio, data_entrega_veiculo, valor_diaria, percentual_comissao_gerente, valor_total_pago, data_pagamento) VALUES (:locacao.cliente, :locacao.gerenteResponsavel, :locacao.veiculo, :locacao.dataInicio, :locacao.dataEntregaVeiculo, :locacao.valorDiaria, :locacao.percentualComissaoGerente, :locacao.valorTotalPago, :locacao.pagamento)")
    void insert(@BindBean("locacao") Locacao locacao);

    @UseFreemarkerEngine
    @SqlUpdate("UPDATE tb_locacoes SET cliente = :locacao.cliente, gerente_responsavel = :locacao.gerenteResponsavel, veiculo = :locacao.veiculo, data_inicio = :locacao.dataInicio, data_entrega_veiculo = :locacao.dataEntregaVeiculo, valor_diaria = :locacao.valorDiaria, percentual_comissao_gerente = :locacao.percentualComissaoGerente, valor_total_pago = :locacao.valorTotalPago, data_pagamento = :locacao.dataPagamento WHERE tb_locacoes.id = :locacao.id")
    void update(@BindBean("locacao") Locacao locacao);
}
