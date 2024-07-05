package br.com.lince.hackathon.client;

import br.com.lince.hackathon.foo.Foo;
import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface ClientRepository {

    @RegisterBeanMapper(Client.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, name, cpf, birth_date, phone, email, cep, city, state, neighborhood, street, number\n" +
            "FROM client ORDER BY id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Client> selectPage(@Define("page") int page, @Define("count") int count);

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM client")
    int count();

    @RegisterBeanMapper(Client.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, name, cpf, birth_date, phone, email, cep, city, state, neighborhood, street, number FROM client WHERE cpf = :cpf")
    Foo findByCpf(@Bind("cpf") String cpf);

    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM client WHERE cpf = :cpf), 1, 0)")
    boolean exists(@Bind("cpf") String cpf);

    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO client(name, cpf, birth_date, phone, email, cep, city, state, neighborhood, street, number) " +
            "VALUES (:client.name, :client.cpf, :client.birth_date, :client.phone, :client.email, :client.cep, :client.city, :client.state, :client.neighborhood, :client.street, :client.number)")
    void insert(@BindBean("client") Client client);

    @UseFreemarkerEngine
    @SqlUpdate("UPDATE client SET name = :client.name, " +
            "birth_date = :client.birth_date" +
            "phone = :client.phone" +
            "email = :client.email" +
            "cep = :client.cep" +
            "city = :client.city" +
            "state = :client.state" +
            "neighborhood = :client.neighborhood" +
            "street = :client.street" +
            "number = :client.number" +
            " WHERE cpf = :client.cpf")
    void update(@BindBean("client") Client client);
}
