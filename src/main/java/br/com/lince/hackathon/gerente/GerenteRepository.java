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
 * Métodos para manipulação dos associados a Gerente no banco de dados
 */
public interface GerenteRepository {
    /**
     * Consulta lista de Gerentes com paginação, ordenado por {bar}
     *
     * @param page  índice da pagina a ser carregada
     * @param count número de itens na pagina
     * @return lista de Gerentes considerando uma paginação de {count} itens, na página {page}
     */
//    @RegisterBeanMapper(Gerente.class)
//    @UseFreemarkerEngine
//    @SqlQuery("SELECT * FROM gerente ORDER BY nm_gerente OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
//    List<Gerente> selectPage(@Define("page") int page, @Define("count") int count);
//
//    /**
//     * Counts the number of rows in the gerente table
//     *
//     * @return the number of rows
//     */
//    @UseFreemarkerEngine
//    @SqlQuery("SELECT COUNT(*) FROM gerente")
//    int count();
//
//    /**
//     * Carrega o Gerente com o {bar} recebido
//     *
//     * @param cpf valor para selecionar o Gerente correto
//     * @return Gerente
//     */
//    @RegisterBeanMapper(Gerente.class)
//    @UseFreemarkerEngine
//    @SqlQuery("SELECT * FROM gerente WHERE nr_cpf = :cpf")
//    Gerente findByCpf(@Bind("cpf") String cpf);
//
//    /**
//     * Verifica se existe um Gerente com o {bar} informado
//     *
//     * @param cpf valor para verificar a existência de um Gerente correto
//     * @return true caso exista um Gerente com o {bar} informado
//     */
//    @UseFreemarkerEngine
//    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM gerente WHERE nr_cpf = :cpf), 1, 0)")
//    boolean exists(@Bind("cpf") String cpf);
//
//    /**
//     * Remove um Gerente da base de dados
//     *
//     * @param cpf valor para apagar identificar o Gerente a ser apagado
//     */
//    @UseFreemarkerEngine
//    @SqlUpdate("DELETE FROM gerente WHERE nr_cpf = :cpf")
//    void delete(@Bind("cpf") int cpf);

    /**
     * Cadastra um novo Gerente na base de dados.
     *
     * @param gerente Gerente a ser cadastrado
     */
    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO gerente VALUES (:gerente.cpf, :gerente.nome, :gerente.telefone, :gerente.email, :gerente.endereco.cidade, :gerente.endereco.estado, :gerente.percentualComissao, :gerente.dataContratacao)")
    void insert(@BindBean("Gerente") Gerente gerente);

    /**
     * Atualiza um Gerente na base de dados, conforme o valor de {bar} desse Gerente
     *
     * @param gerente Gerente a ser cadastrado
     */
//    @UseFreemarkerEngine
//    @SqlUpdate("UPDATE gerente SET nm_gerente = :gerente.nome WHERE nr_cpf = :gerente.cpf")
//    void update(@BindBean("Gerente") Gerente gerente);
}
