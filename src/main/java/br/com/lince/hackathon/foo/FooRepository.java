package br.com.lince.hackathon.foo;

import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlScript;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface FooRepository {
    @RegisterBeanMapper(Foo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT bar, bas, boo FROM foo ORDER BY bar OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Foo> selectPage(@Define("page") int page, @Define("count") int count);

    @RegisterBeanMapper(Foo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT bar, bas, boo FROM foo WHERE bar = :bar")
    Foo findByBar(@Bind("bar") int bar);

    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM foo WHERE bar = :bar), 1, 0)")
    boolean exists(@Bind("bar") int bar);

    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM foo WHERE bar = :bar")
    void delete(@Bind("bar") int bar);

    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO foo(bas, boo) VALUES (:foo.bas, :foo.boo)")
    void insert(@BindBean("foo") Foo foo);

    @UseFreemarkerEngine
    @SqlUpdate("UPDATE foo SET bas = :foo.bas, boo = :foo.boo WHERE bar = :foo.bar")
    void update(@BindBean("foo") Foo foo);
}
