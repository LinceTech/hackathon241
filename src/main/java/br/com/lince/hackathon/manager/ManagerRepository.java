package br.com.lince.hackathon.manager;

import br.com.lince.hackathon.client.Client;
import br.com.lince.hackathon.foo.Foo;
import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface ManagerRepository {

    @RegisterBeanMapper(Manager.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, name, cpf, phone, email, city, state, commission_percentage, hiring_date, date_birth\n" +
            "FROM MANAGER ORDER BY id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Manager> selectPage(@Define("page") int page, @Define("count") int count);

    @RegisterBeanMapper(Manager.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT id, name, cpf, phone, email, city, state, commission_percentage, hiring_date, date_birth FROM MANAGER WHERE id = :id")
    Manager findByManagerId(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO manager(name, cpf, phone, email, city, state, commission_percentage, hiring_date, date_birth) VALUES (:manager.name, " +
            ":manager.cpf, :manager.phone, :manager.email, :manager.city, :manager.state, :manager.commission_percentage, :manager.hiring_date, :manager.date_birth)")
    void insert(@BindBean("manager") Manager manager);

    @UseFreemarkerEngine
    @SqlUpdate("UPDATE manager SET name = :manager.name, cpf = :manager.cpf, phone = :manager.phone, email = :manager.email, city = :manager.city, " +
            "state = :manager.state, commission_percentage = :manager.commission_percentage, hiring_date = :manager.hiring_date, " +
            "date_birth = :manager.date_birth  WHERE CPF = :manager.cpf")
    void update(@BindBean("manager") Manager manager);

    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM MANAGER WHERE ID = :id")
    void delete(@Bind("id") int id);

    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM MANAGER WHERE CPF = :cpf), 1, 0)")
    boolean exists(@Bind("cpf") String  cpf);

    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM MANAGER")
    int count();

}
