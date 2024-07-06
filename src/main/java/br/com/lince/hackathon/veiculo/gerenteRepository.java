package br.com.lince.hackathon.veiculo;

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
public interface gerenteRepository {
    /**
     * Consulta lista de foos com paginação, ordenado por {bar}
     *
     * @param page  índice da pagina a ser carregada
     * @param count número de itens na pagina
     * @return lista de foos considerando uma paginação de {count} itens, na página {page}
     */


    @RegisterBeanMapper(Veiculo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT CDGERENTE, NOME, CPF, DDD, TELEFONE, EMAIL, CIDADE, ESTADO, PCCOMISSAO, DTCONTRATACAO, INDATIVO FROM GERENTE ORDER BY ${orderBy} OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Veiculo> selectPage(@Define("page") int page, @Define("count") int count, @Define("orderBy") String orderBy);

    /**
     * Counts the number of rows in the foo table
     *
     * @return the number of rows
     */
    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM GERENTE")
    int count();

    /**
     * Carrega o foo com o {bar} recebido
     *
     * @param bar valor para selecionar o foo correto
     * @return foo
     */
    @RegisterBeanMapper(Veiculo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT CDGERENTE, NOME, CPF, DDD, TELEFONE, EMAIL, CIDADE, ESTADO, PCCOMISSAO, DTCONTRATACAO, INDATIVO FROM GERENTE WHERE CDGERENTE = :cdgerente")
    Veiculo findByBar(@Bind("cdgerente") int cdgerente);

    /**
     * Verifica se existe um foo com o {bar} informado
     *
     * @param bar valor para verificar a existência de um foo correto
     * @return true caso exista um foo com o {bar} informado
     */
    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM GERENTE WHERE CDGERENTE = :cdgerente), 1, 0)")
    boolean exists(@Bind("cdgerente") int cdgerente);

    /**
     * Remove um foo da base de dados
     *
     * @param CDGERENTE valor para apagar identificar o foo a ser apagado
     */
    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM GERENTE WHERE CDGERENTE = :cdgerente")
    void delete(@Bind("cdgerente") int cdgerente);

    /**
     * Cadastra um novo foo na base de dados.
     *
     * @param foo foo a ser cadastrado
     */
    @UseFreemarkerEngine
    @SqlUpdate("SET IDENTITY_INSERT GERENTE ON INSERT INTO GERENTE(CDGERENTE, NOME, CPF, DDD, TELEFONE, EMAIL, CIDADE, ESTADO, PCCOMISSAO, DTCONTRATACAO, INDATIVO) VALUES (:gerente.cdgerente, :gerente.nome, :gerente.cpf, :gerente.ddd, :gerente.telefone, :gerente.email, :gerente.cidade, :gerente.estado, :gerente.pccomissao, :gerente.dtcontratacao, :gerente.indativo)")
    void insert(@BindBean("gerente") Veiculo veiculo);

    /**
     * Atualiza um foo na base de dados, conforme o valor de {bar} desse foo
     *
     * @param foo foo a ser cadastrado
     */
    @UseFreemarkerEngine
    @SqlUpdate("UPDATE GERENTE SET  NOME = :gerente.nome, DDD = :gerente.ddd, TELEFONE = :gerente.telefone, EMAIL = :gerente.email, CIDADE = :gerente.cidade, ESTADO = :gerente.estado, PCCOMISSAO = :gerente.pccomissao, DTCONTRATACAO = :gerente.dtcontratacao WHERE CDGERENTE = :gerente.cdgerente")
    void update(@BindBean("gerente") Veiculo veiculo);
}
