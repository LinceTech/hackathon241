package br.com.lince.hackathon.veiculos;

import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface VeiculosRepository {
    @RegisterBeanMapper(Veiculos.class)
    @SqlQuery(
            "SELECT * FROM Veiculos C (NOLOCK)" +
                    "ORDER BY C.modelo ASC OFFSET (${pagina} * ${qtRegistros}) ROWS FETCH NEXT ${qtRegistros} ROWS ONLY"
    )
    @UseFreemarkerEngine
    List<Veiculos> consultaPaginacao(@Define("pagina") int pagina, @Define("qtRegistros") int qtRegistros);
    
    @SqlUpdate(
            "INSERT INTO Veiculos VALUES (" +
                    ":marca," +
                    ":modelo," +
                    ":placa," +
                    ":cor," +
                    ":ano_fabricacao," +
                    ":custo_diaria," +
                    ":descricaoPromocional," +
                    ":tipoCombustivel" +
                    ")"
    )
    void insereVeiculo(@BindBean Veiculos Veiculos);

    @SqlUpdate(
            "UPDATE Veiculos SET " +
                    "marca=:marca," +
                    "modelo=:modelo," +
                    "placa=:placa," +
                    "cor=:cor," +
                    "ano_fabricacao=:ano_fabricacao," +
                    "custo_diaria=:custo_diaria," +
                    "descricao_promocional=:descricao_promocional," +
                    "tipo_combustivel=:tipo_combustivel " +
                    "WHERE id=:id"
    )
    void atualizaVeiculo(@BindBean Veiculos Veiculos);

    @RegisterBeanMapper(Veiculos.class)
    @SqlQuery("SELECT * FROM Veiculos (NOLOCK) WHERE id=:id")
    Veiculos pegaVeiculosPeloID(Long id);

    @SqlUpdate("DELETE FROM Veiculos WHERE id=:id")
    void deleteVeiculo(Long id);
}
