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
            "SELECT * FROM GERENTES C (NOLOCK)" +
                    " ORDER BY C.nome ASC OFFSET (${pagina} * ${qtRegistros}) ROWS FETCH NEXT ${qtRegistros} ROWS ONLY"
    )
    @UseFreemarkerEngine
    List<Gerentes> consultaPaginacao(@Define("pagina") int pagina, @Define("qtRegistros") int qtRegistros);


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
            "UPDATE Gerentes SET " +
                    "nome=:nome," +
                    "cpf=:cpf," +
                    "ddd=:ddd," +
                    "telefone=:telefone," +
                    "email=:email," +
                    "cidade=:cidade," +
                    "estado=:estado," +
                    "percentual_comissao=:percentual_comissao," +
                    "data_contratacao=:data_contratacao " +
                    "WHERE id=:id"
    )
    void atualizaGerente(@BindBean Gerentes Gerentes);

    @RegisterBeanMapper(Gerentes.class)
    @SqlQuery("SELECT * FROM GERENTES (NOLOCK) WHERE id=:id")
    Gerentes pegaGerentesPeloID(Long id);

    @SqlUpdate("DELETE FROM GERENTES WHERE id=:id")
    void deleteGerente(Long id);

    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM GERENTES WHERE id=:id), 1, 0)")
    boolean existeGerente(@Bind("id") Long id);
}
