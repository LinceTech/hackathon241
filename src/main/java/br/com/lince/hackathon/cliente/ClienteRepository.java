package br.com.lince.hackathon.cliente;

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
public interface ClienteRepository {
    /**
     * Consulta lista de foos com paginação, ordenado por {bar}
     *
     * @param page  índice da pagina a ser carregada
     * @param count número de itens na pagina
     * @return lista de foos considerando uma paginação de {count} itens, na página {page}
     */
    @RegisterBeanMapper(Cliente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT nm_cliente, nr_cpf, dt_nascimento, nr_telefone, ds_email, nm_bairro, nr_cep, nm_cidade, nm_estado, nr_residencia, nm_rua FROM cliente ORDER BY nr_cpf OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Cliente> selectPage(@Define("page") int page, @Define("count") int count);

    /**
     * Counts the number of rows in the foo table
     *
     * @return the number of rows
     */
    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM cliente")
    int count();

    /**
     * Carrega o foo com o {bar} recebido
     *
     * @param nr_cpf valor para selecionar o foo correto
     * @return foo
     */
    @RegisterBeanMapper(Cliente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * FROM cliente WHERE nr_cpf = :nr_cpf")
    Cliente findByCliente(@Bind("cliente") int nr_cpf);

    /**
     * Verifica se existe um foo com o {bar} informado
     *
     * @param nr_cpf valor para verificar a existência de um foo correto
     * @return true caso exista um foo com o {bar} informado
     */
    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM cliente WHERE nr_cpf = :nr_cpf), 1, 0)")
    boolean exists(@Bind("nr_cpf") String nr_cpf);

    /**
     * Remove um foo da base de dados
     *
     * @param bar valor para apagar identificar o foo a ser apagado
     */
    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM Cliente WHERE nr_cpf = :nr_cpf")
    void delete(@Bind("bar") int bar);

    /**
     * Cadastra um novo foo na base de dados.
     *
     * @param cliente foo a ser cadastrado
     */
    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO cliente(nm_cliente, nr_cpf, dt_nascimento, nr_telefone, ds_email, nm_bairro, nr_cep, nm_cidade, nm_estado, nr_residencia, nm_rua) VALUES (:Cliente.nm_cliente, :Cliente.nr_cpf, :Cliente.dt_nascimento, :Cliente.nr_telefone, :Cliente.ds_email, :Cliente.nr_cep, :Cliente.nm_cidade, :Cliente.nm_estado, :Cliente.nm_bairro, :Cliente.nm_rua, :Cliente.nr_residencia)")
    void insert(@BindBean("cliente") Cliente cliente);

    /**
     * Atualiza um foo na base de dados, conforme o valor de {bar} desse foo
     *
     * @param cliente foo a ser cadastrado
     */

    @UseFreemarkerEngine
    @SqlUpdate("UPDATE Cliente SET nm_cliente = :Cliente.bas, nm_cliente = :Cliente.nm_cliente WHERE nr_cpf = :Cliente.bar")
    void update(@BindBean("foo") Cliente cliente);

}

