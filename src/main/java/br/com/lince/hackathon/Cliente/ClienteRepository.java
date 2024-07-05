package br.com.lince.hackathon.Cliente;

import br.com.lince.hackathon.foo.Foo;
import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface ClienteRepository {
    @RegisterBeanMapper(Cliente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * FROM cliente " +
              "ORDER BY nome " +
              "OFFSET (${page} * ${count}) " +
              "ROWS FETCH NEXT ${count} " +
              "ROWS ONLY")
    List<Cliente> selectPage(@Define("page") int page, @Define("count") int count);

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM cliente")
    int count();

    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM cliente " +
              "WHERE cpf = :cpf), 1, 0)")
    boolean exists(@Bind("cpf") int cpf);

    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO cliente(nome, cpf, dataNascimento, telefone, email, cep, cidade, estado, bairro, rua, numero) " +
            "VALUES (:cliente.nome, " +
                    ":cliente.cpf, " +
                    ":cliente.dataNascimento, " +
                    ":cliente.telefone, " +
                    ":cliente.email, " +
                    ":cliente.cep, " +
                    ":cliente.cidade, " +
                    ":cliente.estado, " +
                    ":cliente.bairro, " +
                    ":cliente.rua, " +
                    ":cliente.numero)")
    void insert(@BindBean("cliente") Cliente cliente);

    /*
        Atualiza o cliente
     */
    @UseFreemarkerEngine
//    @SqlUpdate("UPDATE foo SET bas = :foo.bas, boo = :foo.boo WHERE bar = :foo.bar")
    @SqlUpdate("UPDATE cliente SET nome = :cliente.nome, " +
                                         ":cliente.cpf, " +
                                         ":cliente.dataNascimento, " +
                                         ":cliente.telefone, " +
                                         ":cliente.email, " +
                                         ":cliente.cep, " +
                                         ":cliente.cidade, " +
                                         ":cliente.estado, " +
                                         ":cliente.bairro, " +
                                         ":cliente.rua, " +
                                         ":cliente.numero " +
                                         "where :cliente.cpf")
    void update(@BindBean("cliente") Cliente cliente);

    @RegisterBeanMapper(Cliente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT nome, cpf, dataNascimento, telefone, email, cep, cidade, estado, bairro, rua, numero " +
              "FROM cliente " +
              "WHERE cpf = :cpf")
    Cliente findByCPF(@Bind("cpf") int cpf);

    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM cliente " +
               "WHERE cpf = :cpf")
    void delete(@Bind("cpf") int cpf);

}
