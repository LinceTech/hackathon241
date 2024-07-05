package br.com.lince.hackathon.gerente;

import br.com.lince.hackathon.foo.Foo;
import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface GerenteRepository {
    /**
     * Consulta lista de foos com paginação, ordenado por {bar}
     *
     * @param page  índice da pagina a ser carregada
     * @param count número de itens na pagina
     * @return lista de foos considerando uma paginação de {count} itens, na página {page}
     */
    @RegisterBeanMapper(Gerente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT nome, cpf, telefone, email, cidade, estado, percentualComissao, dataContratacao FROM gerente ORDER BY cpf OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Gerente> selectPage(@Define("page") int page, @Define("count") int count);

    /**
     * Counts the number of rows in the foo table
     *
     * @return the number of rows
     */
    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM gerente")
    int count();

    /**
     * Carrega o foo com o {bar} recebido
     *
     * @param cpf valor para selecionar o foo correto
     * @return foo
     */
    @RegisterBeanMapper(Gerente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT nome, cpf, telefone, email, cidade, estado, replace(percentualComissao,'.',',') as percentualComissao, dataContratacao FROM gerente WHERE cpf = :cpf")
    Gerente findByCpf(@Bind("cpf") int cpf);

    /**
     * Verifica se existe um foo com o {bar} informado
     *
     * @param cpf valor para verificar a existência de um foo correto
     * @return true caso exista um foo com o {bar} informado
     */
    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM gerente WHERE cpf = :cpf), 1, 0)")
    boolean exists(@Bind("cpf") int cpf);

    /**
     * Remove um foo da base de dados
     *
     * @param cpf valor para apagar identificar o foo a ser apagado
     */
    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM gerente WHERE cpf = :cpf")
    void delete(@Bind("cpf") int cpf);

    /**
     * Cadastra um novo gerente na base de dados.
     *
     * @param gerente gerente a ser cadastrado
     */
    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO gerente(nome, cpf, telefone, email, cidade, estado, percentualComissao, dataContratacao) VALUES (:gerente.nome, :gerente.cpf, :gerente.telefone, :gerente.email, :gerente.cidade, :gerente.estado, :gerente.percentualComissao, :gerente.dataContratacao)")

    void insert(@BindBean("gerente") Gerente gerente);


    /**
     * Atualiza um gerente na base de dados, conforme o valor de {bar} desse foo
     *
     * @param gerente gerente a ser cadastrado
     */
    @UseFreemarkerEngine
    @SqlUpdate("UPDATE gerente SET nome = :gerente.nome, cpf = :gerente.cpf, telefone = :gerente.telefone, email = :gerente.email, cidade = :gerente.cidade, estado = :gerente.estado, percentualComissao = :gerente.percentualComissao, dataContratacao = :gerente.dataContratacao WHERE cpf = :gerente.cpf")
    void update(@BindBean("gerente") Gerente gerente);
}
