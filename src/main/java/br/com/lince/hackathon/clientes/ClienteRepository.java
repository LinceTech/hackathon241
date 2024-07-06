package br.com.lince.hackathon.clientes;

import br.com.lince.hackathon.foo.Foo;
import br.com.lince.hackathon.gerentes.GerenteFiltro;
import br.com.lince.hackathon.gerentes.Gerentes;
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
    @SqlQuery("SELECT * FROM clientes ORDER BY id")
    List<Cliente> getAll();

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM clientes")
    int count();

    @RegisterBeanMapper(Cliente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * FROM clientes WHERE id = :id")
    Cliente findById(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM clientes WHERE id = :id), 1, 0)")
    boolean exists(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM clientes WHERE email = :email), 1, 0)")
    boolean findByEmail(@Bind("email") String email);

    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM clientes WHERE cpf = :cpf and id != :id), 1, 0)")
    boolean findByCPF(@Bind("cpf") String cpf, @Bind("id") int id);

    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM clientes WHERE cpf = :email and id != :id), 1, 0)")
    boolean findByEmail(@Bind("email") String email, @Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM clientes WHERE id = :id")
    void delete(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO clientes(nome, cpf, data_nascimento, telefone, email, CEP, cidade, estado, bairro, rua, numero) " +
            "VALUES (:cliente.nome, :cliente.cpf, :cliente.dataNascimento, :cliente.telefone, " +
            ":cliente.email, :cliente.cep, :cliente.cidade, :cliente.estado, " +
            ":cliente.bairro, :cliente.rua, :cliente.numero)")
    void insert(@BindBean("cliente") Cliente cliente);

    @UseFreemarkerEngine
    @SqlUpdate("UPDATE clientes SET nome = :cliente.nome, cpf = :cliente.cpf, data_nascimento = :cliente.dataNascimento, " +
            "telefone = :cliente.telefone, email = :cliente.email, CEP = :cliente.cep, cidade = :cliente.cidade, " +
            "estado = :cliente.estado, bairro = :cliente.bairro, rua = :cliente.rua, numero = :cliente.numero " +
            "WHERE id = :cliente.id")
    void update(@BindBean("cliente") Cliente cliente);

    @RegisterBeanMapper(Cliente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * FROM clientes where clientes.Nome like '%${clienteFiltro.nome}%' or clientes.Cidade like '%${clienteFiltro.cidade}%' or clientes.data_nascimento = '${clienteFiltro.data_nascimento}' ORDER BY ${coluna} OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Cliente> selectFilterPage(@Define("page") int page, @Define("count") int count, @Define("clienteFiltro") ClienteFiltro clienteFiltro, @Define("coluna") String coluna);

    @RegisterBeanMapper(Cliente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * FROM clientes ORDER BY nome OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Cliente> selectPage(@Define("page") int page, @Define("count") int count);

    @RegisterBeanMapper(Cliente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * FROM clientes WHERE clientes.nome like '%${clienteFiltro.nome}%' OR clientes.cidade like '%${clienteFiltro.cidade}%' or clientes.data_nascimento = '${clienteFiltro.data_nascimento}' ORDER BY id ")
    int countFilter(@Define("clienteFiltro") ClienteFiltro clienteFiltro);
}
