package br.com.lince.hackathon.locacao;

import br.com.lince.hackathon.Cliente.Cliente;
import br.com.lince.hackathon.gerente.Gerente;
import br.com.lince.hackathon.veiculo.Veiculo;
import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface LocacaoRepository {
    @RegisterBeanMapper(Locacao.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, dataInicio, dataEntrega, valorDiaria, percentualComissao, valorTotalPago, dataPagamento, clienteCpf, gerenteCpf, placaVeiculo FROM locacao ORDER BY id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Locacao> selectPage(@Define("page") int page, @Define("count") int count);

    /**
     * Counts the number of rows in the foo table
     *
     * @return the number of rows
     */
    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM locacao")
    int count();

    @RegisterBeanMapper(Locacao.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, dataInicio, dataEntrega, valorDiaria, percentualComissao, valorTotalPago, dataPagamento, placaVeiculo, gerenteCpf, clienteCpf FROM locacao (nolock) WHERE id = :id")
    Locacao findById(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM locacao WHERE id = :id), 1, 0)")
    boolean exists(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM locacao WHERE id = :id")
    void delete(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO locacao(dataInicio, dataEntrega, valorDiaria, percentualComissao, valorTotalPago, dataPagamento, placaVeiculo, gerenteCpf, clienteCpf) VALUES (:locacao.dataInicio, :locacao.dataEntrega, :locacao.valorDiaria, :locacao.percentualComissao, :locacao.valorTotalPago, :locacao.dataPagamento, :locacao.placaVeiculo, :locacao.gerenteCpf, :locacao.clienteCpf)")

    void insert(@BindBean("locacao") Locacao locacao);

    @UseFreemarkerEngine
    @SqlUpdate("UPDATE locacao SET dataEntrega = :locacao.dataEntrega, valorDiaria = :locacao.valorDiaria, percentualComissao = :locacao.percentualComissao, valorTotalPago = :locacao.valorTotalPago, dataPagamento = :locacao.dataPagamento, clienteCpf = :locacao.clienteCpf WHERE id = :locacao.id")
    void update(@BindBean("locacao") Locacao locacao);
}