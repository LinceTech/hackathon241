package br.com.lince.hackathon.veiculos;

import br.com.lince.hackathon.client.Client;
import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface VeiculoRepository {

    @RegisterBeanMapper(Veiculo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, marca, modelo, placa, cor, ano_fabricacao, custo_diaria, descricao_promocional, tipo_combustivel FROM tb_veiculos ORDER BY id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Veiculo> selectPageVeiculos(@Define("page") int page, @Define("count") int count);


    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM tb_veiculos")
    int countVeiculos();


    @RegisterBeanMapper(Veiculo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT  id, marca, modelo, placa, cor, ano_fabricacao, custo_diaria, descricao_promocional, tipo_combustivel FROM tb_veiculos WHERE id = :id")
    Veiculo findVeiculoById(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM tb_veiculos WHERE id = :id), 1, 0)")
    boolean existsVeiculos(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM tb_veiculos WHERE id = :id")
    void deleteVeiculoById(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO tb_veiculos(marca, modelo, placa, cor, ano_fabricacao, custo_diaria, descricao_promocional, tipo_combustivel) " +
            "VALUES (:veiculos.marca, :veiculos.modelo, :veiculos.placa, :veiculos.cor, :veiculos.anoFabricacao, :veiculos.custoDiaria, :veiculos.descricaoPromocional, :veiculos.tipoCombustivel)")
    void insertVeiculos(@BindBean("veiculos") Veiculo veiculos);


    @UseFreemarkerEngine
    @SqlUpdate("UPDATE tb_veiculos SET marca = :veiculos.marca," +
            " modelo = :veiculos.modelo," +
            " placa = :veiculos.placa," +
            " cor = :veiculos.cor, " +
            " ano_fabricacao = :veiculos.anoFabricacao, " +
            " custo_diaria = :veiculos.custoDiaria, " +
            " descricao_promocional = :veiculos.descricaoPromocional, " +
            " tipo_combustivel = :veiculos.tipoCombustivel " +
            "WHERE id = :veiculos.id")
    void updateVeiculosById(@BindBean("veiculos") Veiculo veiculos);
}
