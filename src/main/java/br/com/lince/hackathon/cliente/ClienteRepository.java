package br.com.lince.hackathon.cliente;

import br.com.lince.hackathon.foo.Foo;
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
    @SqlQuery("SELECT * FROM cliente ORDER BY nm_cliente OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
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
    @SqlQuery("SELECT bar, bas, boo FROM foo WHERE bar = :nr_cpf")
    Cliente findByBar(@Bind("bar") int nr_cpf);

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
     * @param foo foo a ser cadastrado
     */
    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO foo(bas, boo) VALUES (:Cliente.bas, :Cliente.boo)")
    void insert(@BindBean("foo") Foo foo);

    /**
     * Atualiza um foo na base de dados, conforme o valor de {bar} desse foo
     *
     * @param cliente foo a ser cadastrado
     */
    /*
    @UseFreemarkerEngine
    @SqlUpdate("UPDATE Cliente SET bas = :Cliente.bas, boo = :Cliente.boo WHERE nr_cpf = :Cliente.bar")
    void update(@BindBean("foo") Cliente cliente);
    */
}

