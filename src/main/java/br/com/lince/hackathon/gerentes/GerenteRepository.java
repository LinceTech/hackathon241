package br.com.lince.hackathon.gerentes;

import br.com.lince.hackathon.foo.Foo;
import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface GerenteRepository {

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM gerente")
    int count();

    @RegisterBeanMapper(Gerentes.class)
    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO gerente(Nome, CPF, Telefone, Email, Cidade, Estado, PercentualDeComissao, DataDeContratacao) VALUES (:gerentes.nome, :gerentes.cpf, :gerentes.telefone, :gerentes.email, :gerentes.cidade, :gerentes.estado, :gerentes.percentual, :gerentes.dataContratacao)")
    void insert(@BindBean("gerentes") Gerentes gerentes);

    @RegisterBeanMapper(Gerentes.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, Nome, CPF, Telefone, Email, Cidade, Estado, PercentualDeComissao, DataDeContratacao FROM gerente ORDER BY nome, cidade, DataDeContratacao OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Gerentes> selectPage(@Define("page") int page, @Define("count") int count);

    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM gerente WHERE id = :id")
    void delete(@Bind("id") int id);

    @RegisterBeanMapper(Gerentes.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, Nome, CPF, Telefone, Email, Cidade, Estado, PercentualDeComissao, DataDeContratacao FROM gerente where gerente.Nome like '%${gerenteFiltro.nome}%' and gerente.CPF like '%${gerenteFiltro.cpf}%' and gerente.Cidade like '%${gerenteFiltro.cidade}%' and gerente.Estado like '%${gerenteFiltro.estado}%' ORDER BY nome, cidade, DataDeContratacao OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Gerentes> selectFilterPage(@Define("page") int page, @Define("count") int count, @Define("gerenteFiltro") GerenteFiltro gerenteFiltro);

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM gerente where gerente.Nome like '%${gerenteFiltro.nome}%' and gerente.CPF like '%${gerenteFiltro.cpf}%' and gerente.Cidade like '%${gerenteFiltro.cidade}%' and gerente.Estado like '%${gerenteFiltro.estado}%'")
    int countFilter(@Define("gerenteFiltro") GerenteFiltro gerenteFiltro);
}