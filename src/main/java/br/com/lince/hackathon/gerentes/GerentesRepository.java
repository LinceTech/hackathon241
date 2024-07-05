package br.com.lince.hackathon.gerentes;

import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface GerentesRepository {
    @RegisterBeanMapper(Gerentes.class)
    @SqlQuery(
            "SELECT * FROM GERENTES C (NOLOCK) \n" +
            "WHERE C.nome LIKE CONCAT('%', :clienteFiltros.nome, '%') \n" +
            "AND C.cpf    LIKE CONCAT('%', :clienteFiltros.documento, '%') \n" +
            "AND C.cidade LIKE CONCAT('%', :clienteFiltros.cidade, '%') \n" +
            "AND C.estado LIKE CONCAT('%', :clienteFiltros.estado, '%') \n" +
            "ORDER BY C.nome ASC \n" +
            "OFFSET (${pagina} * ${qtRegistros}) \n" +
            "ROWS FETCH NEXT ${qtRegistros} ROWS ONLY"
    )
    @UseFreemarkerEngine
    List<Gerentes> consultaPaginacao(
            @Define("pagina") int pagina,
            @Define("qtRegistros") int qtRegistros,
            @BindBean("clienteFiltros") GerenteFiltros gerenteFiltros
    );


    @SqlUpdate(
            "INSERT INTO Gerentes VALUES (" +
                    ":gerentes.nome," +
                    ":gerentes.cpf," +
                    ":gerentes.ddd," +
                    ":gerentes.telefone," +
                    ":gerentes.email," +
                    ":gerentes.cidade," +
                    ":gerentes.estado," +
                    ":gerentes.percentualComissao," +
                    ":gerentes.dataContratacao" +
                    ")"
    )
    void insereGerente(@BindBean("gerentes") Gerentes gerentes);

    @SqlUpdate(
            "UPDATE GERENTES SET " +
                    "nome=:gerente.nome," +
                    "cpf=:gerente.cpf," +
                    "ddd=:gerente.ddd," +
                    "telefone=:gerente.telefone," +
                    "email=:gerente.email," +
                    "cidade=:gerente.cidade," +
                    "estado=:gerente.estado," +
                    "percentual_comissao=:gerente.percentualComissao," +
                    "data_contratacao=:gerente.dataContratacao " +
                    "WHERE id=:gerente.id"
    )
    void atualizaGerente(@BindBean("gerente") Gerentes gerentes);

    @RegisterBeanMapper(Gerentes.class)
    @SqlQuery("SELECT * FROM GERENTES (NOLOCK) WHERE id=:id")
    Gerentes pegaGerentesPeloID(@Bind("id") Long id);

    @SqlUpdate("DELETE FROM GERENTES WHERE id=:id")
    void deleteGerente(@Bind("id") Long id);

    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM GERENTES WHERE id=:id), 1, 0)")
    boolean existeGerente(@Bind("id") Long id);

    @RegisterBeanMapper(Gerentes.class)
    @SqlQuery("SELECT * FROM GERENTES (NOLOCK) WHERE nome=:nome")
    Gerentes pesquisaGerenteNome(@Bind("nome") String nome);
}
