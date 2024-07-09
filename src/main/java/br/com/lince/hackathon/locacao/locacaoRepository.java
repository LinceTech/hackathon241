package br.com.lince.hackathon.locacao;

import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * Métodos para manipulação dos associados a Foo no banco de dados
 */
public interface locacaoRepository {
    /**
     * Consulta lista de foos com paginação, ordenado por {bar}
     *
     * @param page  índice da pagina a ser carregada
     * @param count número de itens na pagina
     * @return lista de foos considerando uma paginação de {count} itens, na página {page}
     */


    @RegisterBeanMapper(Locacao.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT CDLOCACAO, CDCLIENTE, CDGERENTE, CDVEICULO, DTINICIO, DTENTREGA, VALDIARIA, PCCOMISSAO, VALPAGO, DATAPAGAMENTO FROM LOCACAO ORDER BY ${orderBy} OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Locacao> selectPage(@Define("page") int page, @Define("count") int count, @Define("orderBy") String orderBy);

    /**
     * Counts the number of rows in the foo table
     *
     * @return the number of rows
     */
    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM LOCACAO")
    int count();

    /**
     * Carrega o foo com o {bar} recebido
     *
     * @param bar valor para selecionar o foo correto
     * @return foo
     */
    @RegisterBeanMapper(Locacao.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT CDLOCACAO, CDCLIENTE, CDGERENTE, CDVEICULO, DTINICIO, DTENTREGA, VALDIARIA, PCCOMISSAO, VALPAGO, DATAPAGAMENTO FROM LOCACAO WHERE CDLOCACAO = :cdlocacao")
    Locacao findByBar(@Bind("cdlocacao") int cdlocacao);

    /**
     * Verifica se existe um foo com o {bar} informado
     *
     * @param bar valor para verificar a existência de um foo correto
     * @return true caso exista um foo com o {bar} informado
     */
    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM LOCACAO WHERE CDLOCACAO = :cdlocacao), 1, 0)")
    boolean exists(@Bind("cdlocacao") int cdlocacao);

    /**
     * Remove um foo da base de dados
     *
     * @param CDGERENTE valor para apagar identificar o foo a ser apagado
     */
    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM LOCACAO WHERE CDLOCACAO = :cdlocacao")
    void delete(@Bind("cdlocacao") int cdlocacao);

    /**
     * Cadastra um novo foo na base de dados.
     *
     * @param foo foo a ser cadastrado
     */
    @UseFreemarkerEngine
        @SqlUpdate("SET IDENTITY_INSERT LOCACAO ON; " +
                "INSERT INTO LOCACAO(CDLOCACAO, CDCLIENTE, CDGERENTE, CDVEICULO, DTINICIO, DTENTREGA, VALDIARIA, PCCOMISSAO, VALPAGO, DATAPAGAMENTO) " +
                "VALUES (:locacao.cdlocacao, :locacao.cdcliente, :locacao.cdgerente, :locacao.cdveiculo, :locacao.dtinicio, :locacao.dtentrega, " +
                ":locacao.valdiaria, :locacao.pccomissao, :locacao.valpago, :locacao.datapagamento)")
        void insert(@BindBean("locacao") Locacao locacao);


    /**
     * Atualiza um foo na base de dados, conforme o valor de {bar} desse foo
     *
     * @param foo foo a ser cadastrado
     */
    @UseFreemarkerEngine
    @SqlUpdate("UPDATE LOCACAO SET DTINICIO = :locacao.dtinicio, DTENTREGA = :locacao.dtentrega, VALDIARIA = :locacao.valdiaria, PCCOMISSAO = :locacao.pccomissao, VALPAGO = :locacao.valpago, DATAPAGAMENTO = :locacao.datapagamento WHERE CDLOCACAO = :locacao.cdlocacao")
    void update(@BindBean("locacao") Locacao locacao);
}
