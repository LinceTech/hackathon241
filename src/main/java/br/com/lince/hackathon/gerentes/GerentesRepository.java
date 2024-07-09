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
    @UseFreemarkerEngine
    @SqlQuery("SELECT * FROM tb_gerentes ORDER BY id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Gerentes> selectPage(@Define("page") int page, @Define("count") int count);

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM tb_gerentes")
    int count();

    @RegisterBeanMapper(Gerentes.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * FROM tb_gerentes WHERE id = :id")
    Gerentes findById(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM tb_gerentes WHERE id = :id), 1, 0)")
    boolean exists(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM tb_gerentes WHERE id = :id")
    void delete(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO tb_gerentes(nome, cpf, telefone, email, cidade, estado, percentualComissao, dataContratacao) VALUES (:gerente.nome, :gerente.cpf, :gerente.telefone, :gerente.email, :gerente.cidade, :gerente.estado, :gerente.percentualComissao, :gerente.dataContratacao)")
    void insert(@BindBean("gerente") Gerentes gerente);

    @UseFreemarkerEngine
    @SqlUpdate("UPDATE tb_gerentes SET nome = :gerente.nome, cpf = :gerente.cpf, telefone = :gerente.telefone, email = :gerente.email, cidade = :gerente.cidade, estado = :gerente.estado, percentualComissao = :gerente.percentualComissao, dataContratacao = :gerente.dataContratacao WHERE tb_gerentes.id = :gerente.id")
    void update(@BindBean("gerente") Gerentes gerente);
}
