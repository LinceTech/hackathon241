package br.com.lince.hackathon.gerente;

import br.com.lince.hackathon.gerente.Gerente;
import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface GerenteRepository {

    @RegisterBeanMapper(Gerente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT nome, cpf, telefone, email, cidade, estado, percentualComissao, dataContratacao FROM gerente ORDER BY cpf OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Gerente> selectPage(@Define("page") int page, @Define("count") int count);

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM gerente")
    int count();

    @RegisterBeanMapper(Gerente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT nome, cpf, telefone, email, cidade, estado, percentualComissao, dataContratacao FROM gerente WHERE cpf = :cpf")
    Gerente findByCpf(@Bind("cpf") long cpf);

    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM gerente WHERE cpf = :cpf), 1, 0)")
    boolean exists(@Bind("cpf") long cpf);

    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM gerente WHERE cpf = :cpf")
    void delete(@Bind("cpf") long cpf);

    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO gerente(nome, cpf, telefone, email, cidade, estado, percentualComissao, dataContratacao) VALUES (:gerente.nome, :gerente.cpf, :gerente.telefone, :gerente.email, :gerente.cidade, :gerente.estado, :gerente.percentualComissao, :gerente.dataContratacao)")

    void insert(@BindBean("gerente") Gerente gerente);

    @UseFreemarkerEngine
    @SqlUpdate("UPDATE gerente SET nome = :gerente.nome, cpf = :gerente.cpf, telefone = :gerente.telefone, email = :gerente.email, cidade = :gerente.cidade, estado = :gerente.estado, percentualComissao = :gerente.percentualComissao, dataContratacao = :gerente.dataContratacao WHERE cpf = :gerente.cpf")
    void update(@BindBean("gerente") Gerente gerente);
}
