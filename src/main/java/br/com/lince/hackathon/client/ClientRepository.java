package br.com.lince.hackathon.client;

import br.com.lince.hackathon.client.Client;
import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * Métodos para manipulação dos associados a Foo no banco de dados
 */
public interface ClientRepository {

    @RegisterBeanMapper(Client.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, nome, cpf, data_nascimento, telefone, email, cep, cidade, estado, bairro, rua, numero FROM tb_clientes ORDER BY id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Client> selectPageClient(@Define("page") int page, @Define("count") int count);


    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM tb_clientes")
    int countClient();


    @RegisterBeanMapper(Client.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, nome, cpf, data_nascimento, telefone, email, cep, cidade, estado, bairro, rua, numero FROM tb_clientes WHERE id = :id")
    Client findClientById(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM tb_clientes WHERE id = :id), 1, 0)")
    boolean existsClient(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM tb_clientes WHERE id = :id")
    void deleteClientById(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO tb_clientes(nome, cpf, data_nascimento, telefone, email, cep, cidade, estado, bairro, rua, numero) " +
            "VALUES (:client.nome, :client.cpf, :client.data_nascimento, :client.telefone, :client.email, :client.cep, :client.cidade, :client.estado, :client.bairro, :client.rua, :client.numero)")
    void insertClient(@BindBean("client") Client client);


    @UseFreemarkerEngine
    @SqlUpdate("UPDATE tb_clientes SET nome = :client.name, cpf = :client.cpf, data_nascimento = :client.data_nascimento," +
            " telefone = :client.telefone, " +
            " email = :client.email, " +
            " cep = :client.cep, " +
            " cidade = :client.cep, " +
            " estado = :client.estado, " +
            " bairro = :client.bairro, " +
            " rua = :client.rua, " +
            " numero = :client.numero " +
            "WHERE id = :client.id")
    void updateClientById(@BindBean("client") Client client);
}
