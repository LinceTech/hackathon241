package br.com.lince.hackathon.veiculos;

import br.com.lince.hackathon.veiculos.VeiculoFiltros;
import br.com.lince.hackathon.veiculos.Veiculos;
import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface VeiculosRepository {
    @RegisterBeanMapper(Veiculos.class)
    @SqlQuery(
            "SELECT * FROM VEICULOS C (NOLOCK) \n" +
            "WHERE C.marca              LIKE CONCAT('%', :veiculoFiltros.marca, '%') \n" +
            "AND   C.modelo             LIKE CONCAT('%', :veiculoFiltros.modelo, '%') \n" +
            "AND   C.ano_fabricacao     LIKE CONCAT('%', :veiculoFiltros.anoFabricacao, '%') \n" +
            "AND   C.placa              LIKE CONCAT('%', :veiculoFiltros.placa, '%') \n" +
            "AND   C.cor                LIKE CONCAT('%', :veiculoFiltros.cor, '%') \n" +
            "AND   C.tipo_combustivel   LIKE CONCAT('%', :veiculoFiltros.tipoCombustivel, '%') \n" +
            "ORDER BY C.marca ASC \n" +
            "OFFSET (${pagina} * ${qtRegistros}) \n" +
            "ROWS FETCH NEXT ${qtRegistros} ROWS ONLY"
    )
    @UseFreemarkerEngine
    List<Veiculos> consultaPaginacao(
            @Define("pagina") int pagina,
            @Define("qtRegistros") int qtRegistros,
            @BindBean("veiculoFiltros") VeiculoFiltros veiculoFiltros
    );
    
    @SqlUpdate(
            "INSERT INTO VEICULOS VALUES (" +
                    ":veiculos.marca," +
                    ":veiculos.modelo," +
                    ":veiculos.placa," +
                    ":veiculos.cor," +
                    ":veiculos.ano_fabricacao," +
                    ":veiculos.custo_diaria," +
                    ":veiculos.descricaoPromocional," +
                    ":veiculos.tipoCombustivel" +
                    ")"
    )
    void insereVeiculo(@BindBean("veiculos") Veiculos veiculos);


    @SqlUpdate(
            "UPDATE VEICULOS SET " +
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
    void atualizaVeiculo(@BindBean("veiculos") Veiculos veiculos);

    @RegisterBeanMapper(Veiculos.class)
    @SqlQuery("SELECT * FROM VEICULOS (NOLOCK) WHERE id=:id")
    Veiculos pegaVeiculosPeloID(@Bind("id") Long id);

    @SqlUpdate("DELETE FROM VEICULOS WHERE id=:id")
    void deleteVeiculo(@Bind ("id") Long id);

    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM VEICULOS WHERE id=:id), 1, 0)")
    boolean existeVeiculo(@Bind("id") Long id);

    @RegisterBeanMapper(Veiculos.class)
    @SqlQuery("SELECT * FROM VEICULOS (NOLOCK) WHERE marca=:marca")
    Veiculos pesquisaVeiculosNome(@Bind("nome") String marca);
}
