package br.com.lince.hackathon.locacoes;

import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface LocacaoRepository {
    @RegisterBeanMapper(Locacao.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * FROM locacoes ORDER BY id")
    List<Locacao> getAll();

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM locacoes")
    int count();

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM locacoes")
    int countFilter(@Define("locacaoFiltro") LocacaoFiltro locacaoFiltro);

    @RegisterBeanMapper(Locacao.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, id_cliente, id_gerente, id_veiculo, data_inicio, data_entrega, valor_diaria, percentual_comissao, valor_total_pago, data_pagamento " +
            "FROM locacoes " +
            "ORDER BY ${campo}  ${sentido} OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Locacao> selectFilterPage(@Define("page") int page, @Define("count") int count, @Define("locacaoFiltro") LocacaoFiltro locacaoFiltro, @Define ("campo") String campo, @Define("sentido") String sentido);

}
