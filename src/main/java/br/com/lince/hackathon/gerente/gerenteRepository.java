package br.com.lince.hackathon.gerente;

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
    @RegisterBeanMapper(Gerente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT CDGERENTE, NOME, CPF, DDD, TELEFONE, EMAIL, CIDADE, ESTADO, PCCOMISSAO, DTCONTRATACAO, INDATIVO FROM GERENTE ORDER BY CDGERENTE OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Gerente> selectPage(@Define("page") int page, @Define("count") int count);

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
    @RegisterBeanMapper(Gerente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT CDGERENTE, NOME, CPF, DDD, TELEFONE, EMAIL, CIDADE, ESTADO, PCCOMISSAO, DTCONTRATACAO, INDATIVO FROM GERENTE")
    Gerente findByBar(@Bind("CDGERENTE") int cdgerente);

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
    void insert(@BindBean("gerente") Gerente gerente);

    /**
     * Atualiza um foo na base de dados, conforme o valor de {bar} desse foo
     *
     * @param foo foo a ser cadastrado
     */
    @UseFreemarkerEngine
    @SqlUpdate("UPDATE foo SET bas = :foo.bas, boo = :foo.boo WHERE bar = :foo.bar")
    void update(@BindBean("foo") Gerente foo);
}
