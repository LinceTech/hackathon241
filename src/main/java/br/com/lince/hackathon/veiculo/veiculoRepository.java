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
public interface veiculoRepository {
    /**
     * Consulta lista de foos com paginação, ordenado por {bar}
     *
     * @param page  índice da pagina a ser carregada
     * @param count número de itens na pagina
     * @return lista de foos considerando uma paginação de {count} itens, na página {page}
     */


    @RegisterBeanMapper(Veiculo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT CDVEICULO, PLACA, CDCOR, ANOFABRICACAO, VALDIARIO, INDPROMOCAO, DESPROMOCAO, CDCOMBUSTIVEL, VALPROMOCAO, CDMARCA, CDMODELO FROM VEICULO ORDER BY ${orderBy} OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Veiculo> selectPage(@Define("page") int page, @Define("count") int count, @Define("orderBy") String orderBy);

    /**
     * Counts the number of rows in the foo table
     *
     * @return the number of rows
     */
    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM VEICULO")
    int count();

    /**
     * Carrega o foo com o {bar} recebido
     *
     * @param bar valor para selecionar o foo correto
     * @return foo
     */
    @RegisterBeanMapper(Veiculo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT CDVEICULO, PLACA, CDCOR, ANOFABRICACAO, VALDIARIO, INDPROMOCAO, DESPROMOCAO, CDCOMBUSTIVEL, VALPROMOCAO, CDMARCA, CDMODELO FROM VEICULO WHERE CDVEICULO = :cdveiculo")
    Veiculo findByBar(@Bind("cdveiculo") int cdveiculo);

    /**
     * Verifica se existe um foo com o {bar} informado
     *
     * @param bar valor para verificar a existência de um foo correto
     * @return true caso exista um foo com o {bar} informado
     */
    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM VEICULO WHERE CDVEICULO = :cdveiculo), 1, 0)")
    boolean exists(@Bind("cdveiculo") int cdveiculo);

    /**
     * Remove um foo da base de dados
     *
     * @param CDGERENTE valor para apagar identificar o foo a ser apagado
     */
    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM VEICULO WHERE CDVEICULO = :cdveiculo")
    void delete(@Bind("cdveiculo") int cdveiculo);

    /**
     * Cadastra um novo foo na base de dados.
     *
     * @param foo foo a ser cadastrado
     */
    @UseFreemarkerEngine
        @SqlUpdate("SET IDENTITY_INSERT VEICULO ON; " +
                "INSERT INTO VEICULO(CDVEICULO, PLACA, CDCOR, ANOFABRICACAO, VALDIARIO, INDPROMOCAO, DESPROMOCAO, CDCOMBUSTIVEL, VALPROMOCAO, CDMARCA, CDMODELO, INDATIVO) " +
                "VALUES (:veiculo.cdveiculo, :veiculo.placa, :veiculo.cdcor, :veiculo.anofabricacao, :veiculo.valdiario, :veiculo.indpromocao, " +
                ":veiculo.despromocao, :veiculo.cdcombustivel, :veiculo.valpromocao, :veiculo.cdmarca, :veiculo.cdmodelo, :veiculo.indativo)")
        void insert(@BindBean("veiculo") Veiculo veiculo);


    /**
     * Atualiza um foo na base de dados, conforme o valor de {bar} desse foo
     *
     * @param foo foo a ser cadastrado
     */
    @UseFreemarkerEngine
    @SqlUpdate("UPDATE VEICULO SET CDCOR = :veiculo.cdcor, ANOFABRICACAO = :veiculo.anofabricacao, VALDIARIO = :veiculo.valdiario, INDPROMOCAO = :veiculo.indpromocao, DESPROMOCAO = :veiculo.despromocao, CDCOMBUSTIVEL = :veiculo.cdcombustivel, VALPROMOCAO = :veiculo.valpromocao, CDMARCA = :veiculo.cdmarca, CDMODELO = :veiculo.cdmodelo")
    void update(@BindBean("veiculo") Veiculo veiculo);
}
