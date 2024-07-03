package br.com.lince.hackathon.foo;

import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlScript;

import java.util.List;

public interface FooRepository {
    @RegisterBeanMapper(Foo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT bar, bas, boo FROM foo ORDER BY bar OFFSET ${offset} ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Foo> selectPage(
            @Define("offset") int offset,
            @Define("count") int count
    );

    @UseFreemarkerEngine
    @SqlQuery("SELECT 1 FROM foo WHERE bar = ${bar}")
    boolean exists(@Define("bar") int bar);

    @UseFreemarkerEngine
    @SqlQuery("DELETE FROM foo WHERE bar = ${bar}")
    void delete(@Define("bar") int bar);

    @UseFreemarkerEngine
    @SqlScript("INSERT INTO foo(bas, boo) VALUES (${foo.bas}, ${foo.boo})")
    void insert(@Define("foo") Foo foo);

    @UseFreemarkerEngine
    @SqlScript("UPDATE foo SET bas = ${foo.bas}, boo = ${foo.boo} WHERE bar = ${foo.bar}")
    void update(@Define("foo") Foo foo);
}
