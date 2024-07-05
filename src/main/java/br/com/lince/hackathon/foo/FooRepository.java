package br.com.lince.hackathon.foo;

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
public interface FooRepository {
    /**
     * Consulta lista de foos com paginação, ordenado por {bar}
     *
     * @param page  índice da pagina a ser carregada
     * @param count número de itens na pagina
     * @return lista de foos considerando uma paginação de {count} itens, na página {page.hbs}
     */
    @RegisterBeanMapper(Foo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT bar, bas, boo FROM foo ORDER BY bar OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Foo> selectPage(@Define("page.hbs") int page, @Define("count") int count);

    /**
     * Counts the number of rows in the foo table
     *
     * @return the number of rows
     */
    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM foo")
    int count();

    /**
     * Carrega o foo com o {bar} recebido
     *
     * @param bar valor para selecionar o foo correto
     * @return foo
     */
    @RegisterBeanMapper(Foo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT bar, bas, boo FROM foo WHERE bar = :bar")
    Foo findByBar(@Bind("bar") int bar);

    /**
     * Verifica se existe um foo com o {bar} informado
     *
     * @param bar valor para verificar a existência de um foo correto
     * @return true caso exista um foo com o {bar} informado
     */
    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM foo WHERE bar = :bar), 1, 0)")
    boolean exists(@Bind("bar") int bar);

    /**
     * Remove um foo da base de dados
     *
     * @param bar valor para apagar identificar o foo a ser apagado
     */
    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM foo WHERE bar = :bar")
    void delete(@Bind("bar") int bar);

    /**
     * Cadastra um novo foo na base de dados.
     *
     * @param foo foo a ser cadastrado
     */
    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO foo(bas, boo) VALUES (:foo.bas, :foo.boo)")
    void insert(@BindBean("foo") Foo foo);

    /**
     * Atualiza um foo na base de dados, conforme o valor de {bar} desse foo
     *
     * @param foo foo a ser cadastrado
     */
    @UseFreemarkerEngine
    @SqlUpdate("UPDATE foo SET bas = :foo.bas, boo = :foo.boo WHERE bar = :foo.bar")
    void update(@BindBean("foo") Foo foo);
}
