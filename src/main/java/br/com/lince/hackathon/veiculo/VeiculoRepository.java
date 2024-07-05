package br.com.lince.hackathon.veiculo;

import br.com.lince.hackathon.veiculo.Veiculo;
import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import javax.servlet.annotation.WebServlet;
import java.util.List;

public interface VeiculoRepository {
    @RegisterBeanMapper(Veiculo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT marca, modelo, placa, cor, anoDeFabricacao, custoDeDiaria, descricaoPromocional, tipoDeCombustivel FROM veiculo ORDER BY placa OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Veiculo> selectPage(@Define("page") int page, @Define("count") int count);

    /**
     * Counts the number of rows in the foo table
     *
     * @return the number of rows
     */
    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM veiculo")
    int count();

    @RegisterBeanMapper(Veiculo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT marca, modelo, placa, cor, anoDeFabricacao, custoDeDiaria, descricaoPromocional, tipoDeCombustivel FROM veiculo WHERE placa = :placa")
    Veiculo findByPlaca(@Bind("placa") String placa);

    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM veiculo WHERE placa = :placa), 1, 0)")
    boolean exists(@Bind("placa") String placa);

    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM veiculo WHERE placa = :placa")
    void delete(@Bind("placa") String placa);

    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO veiculo(marca, modelo, placa, cor, anoDeFabricacao, custoDeDiaria, descricaoPromocional, tipoDeCombustivel) VALUES (:veiculo.marca, :veiculo.modelo, :veiculo.placa, :veiculo.cor, :veiculo.anoDeFabricacao, :veiculo.custoDeDiaria, :veiculo.descricaoPromocional, :veiculo.tipoDeCombustivel)")

    void insert(@BindBean("veiculo") Veiculo veiculo);

    @UseFreemarkerEngine
    @SqlUpdate("UPDATE veiculo SET marca = :veiculo.marca, modelo = :veiculo.modelo, placa = :veiculo.placa, cor = :veiculo.cor, anoDeFabricacao = :veiculo.anoDeFabricacao, custoDeDiaria = :veiculo.custoDeDiaria, descricaoPromocional = :veiculo.descricaoPromocional, tipoDeCombustivel = :veiculo.tipoDeCombustivel WHERE placa = :veiculo.placa")
    void update(@BindBean("veiculo") Veiculo veiculo);
}
