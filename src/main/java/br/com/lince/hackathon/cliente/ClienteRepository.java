package br.com.lince.hackathon.cliente;

import br.com.lince.hackathon.gerente.Gerente;
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
    Cliente findByCliente(@Bind("nr_cpf") String nr_cpf);

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
     * @param nr_cpf valor para apagar identificar o foo a ser apagado
     */
    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM Cliente WHERE nr_cpf = :nr_cpf")
    void delete(@Bind("nr_cpf") String nr_cpf);


    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM locacao WHERE nr_cpf_cliente = :nr_cpf AND dt_entrega = ''")
    boolean verificaLocacao(@Bind("nr_cpf") String nr_cpf);

    /**
     * Cadastra um novo foo na base de dados.
     *
     * @param cliente foo a ser cadastrado
     */
    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO cliente VALUES (:cliente.nm_cliente, :cliente.nr_cpf, :cliente.dt_nascimento, :cliente.nr_telefone, :cliente.ds_email, :cliente.nr_cep, :cliente.nm_cidade, :cliente.nm_estado, :cliente.nm_bairro, :cliente.nm_rua, :cliente.nr_residencia)")
    void insert(@BindBean("cliente") Cliente cliente);

    /**
     * Atualiza um foo na base de dados, conforme o valor de {bar} desse foo
     *
     * @param cliente foo a ser cadastrado
     */

    @UseFreemarkerEngine
    @SqlUpdate("UPDATE cliente SET nr_telefone = :cliente.nr_telefone , ds_email = :cliente.ds_email , nr_cep = :cliente.nr_cep, nm_cidade = :cliente.nm_cidade, nm_estado = :cliente.nm_estado, nm_bairro = :cliente.nm_bairro, nm_rua = :cliente.nm_rua, nr_residencia = :cliente.nr_residencia WHERE nr_cpf = :cliente.nr_cpf")
    void update(@BindBean("cliente") Cliente cliente);
}

