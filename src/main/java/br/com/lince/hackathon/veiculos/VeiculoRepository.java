package br.com.lince.hackathon.veiculos;

import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface VeiculoRepository {

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM veiculo")
    int count();

    @RegisterBeanMapper(Veiculos.class)
    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO veiculo(Marca, Modelo, Placa, Cor, AnoDeFabricacao, CustoDeDiaria, DescricaoPromocional, TipoDeCombustivel) VALUES (:veiculos.marca, :veiculos.modelo, :veiculos.placa, :veiculos.cor, :veiculos.anoDeFabricacao, :veiculos.custoDeDiaria, :veiculos.descricaoPromocional, :veiculos.tipoDeCombustivel)")
    void insert(@BindBean("veiculos") Veiculos veiculos);

    @RegisterBeanMapper(Veiculos.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, Marca, Modelo, Placa, Cor, AnoDeFabricacao, CustoDeDiaria, DescricaoPromocional, TipoDeCombustivel FROM veiculo ORDER BY id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Veiculos> selectPage(@Define("page") int page, @Define("count") int count);

    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM veiculo WHERE id = :id")
    void delete(@Bind("id") int id);

    @RegisterBeanMapper(Veiculos.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, Marca, Modelo, Placa, Cor, AnoDeFabricacao, CustoDeDiaria, DescricaoPromocional, TipoDeCombustivel FROM veiculo where veiculo.Marca like '%${veiculoFiltro.marca}%' and veiculo.Modelo like '%${veiculoFiltro.modelo}%' and veiculo.AnoDeFabricacao like '%${veiculoFiltro.anoDeFabricacao}%' and veiculo.cor like '%${veiculoFiltro.cor}%' ORDER BY ${campo}  ${sentido} OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Veiculos> selectFilterPage(@Define("page") int page, @Define("count") int count, @Define("veiculoFiltro") VeiculoFiltro veiculoFiltro, @Define ("campo") String campo, @Define("sentido") String sentido);

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM veiculo where veiculo.Marca like '%${veiculoFiltro.marca}%' and veiculo.Modelo like '%${veiculoFiltro.modelo}%' and veiculo.AnoDeFabricacao like '%${veiculoFiltro.anoDeFabricacao}%' and veiculo.cor like '%${veiculoFiltro.cor}%'")
    int countFilter(@Define("veiculoFiltro") VeiculoFiltro veiculoFiltro);
}