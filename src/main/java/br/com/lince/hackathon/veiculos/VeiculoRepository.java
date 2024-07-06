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
    @SqlUpdate("INSERT INTO veiculo(Marca, Modelo, Placa, Cor, AnoDeFabricação, CustoDeDiaria, DescriçãoPromocional, TipoDeCombustivel) VALUES (:veiculos.marca, :veiculos.modelo, :veiculos.placa, :veiculos.cor, :veiculos.anoDeFabricacao, :veiculos.custoDeDiaria, :veiculos.descricaoPromocional, :veiculos.tipoDeCombustivel)")
    void insert(@BindBean("veiculos") Veiculos veiculos);

    @RegisterBeanMapper(Veiculos.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, Nome, CPF, Telefone, Email, Cidade, Estado, PercentualDeComissao, DataDeContratacao FROM gerente ORDER BY nome, cidade, DataDeContratacao OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Veiculos> selectPage(@Define("page") int page, @Define("count") int count);

    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM gerente WHERE id = :id")
    void delete(@Bind("id") int id);

    @RegisterBeanMapper(Veiculos.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, Nome, CPF, Telefone, Email, Cidade, Estado, PercentualDeComissao, DataDeContratacao FROM gerente where gerente.Nome like '%${gerenteFiltro.nome}%' and gerente.CPF like '%${gerenteFiltro.cpf}%' and gerente.Cidade like '%${gerenteFiltro.cidade}%' and gerente.Estado like '%${gerenteFiltro.estado}%' ORDER BY ${campo}  ${sentido} OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Veiculos> selectFilterPage(@Define("page") int page, @Define("count") int count, @Define("gerenteFiltro") VeiculoFiltro gerenteFiltro, @Define ("campo") String campo, @Define("sentido") String sentido);

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM gerente where gerente.Nome like '%${gerenteFiltro.nome}%' and gerente.CPF like '%${gerenteFiltro.cpf}%' and gerente.Cidade like '%${gerenteFiltro.cidade}%' and gerente.Estado like '%${gerenteFiltro.estado}%'")
    int countFilter(@Define("gerenteFiltro") VeiculoFiltro gerenteFiltro);
}