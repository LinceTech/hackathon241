package br.com.lince.hackathon.clientes;

import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.customizer.DefineNamedBindings;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface ClientesRepository {
    @RegisterBeanMapper(Clientes.class)
    @SqlQuery(
            "SELECT * FROM CLIENTES C (NOLOCK) \n" +
                    "WHERE C.nome          LIKE CONCAT('%', :clienteFiltros.nome, '%') \n" +
                    "AND C.cidade          LIKE CONCAT('%', :clienteFiltros.cidade, '%') \n" +
                    "ORDER BY C.nome ASC \n" +
                    "OFFSET (${pagina} * ${qtRegistros}) \n" +
                    "ROWS FETCH NEXT ${qtRegistros} ROWS ONLY"
    )
    @UseFreemarkerEngine
    List<Clientes> consultaPaginacao(
            @Define("pagina") int pagina,
            @Define("qtRegistros") int qtRegistros,
            @BindBean("clienteFiltros") ClientesFiltros clientesFiltros
    );

    @SqlUpdate(
            "INSERT INTO CLIENTES VALUES (" +
                    ":clientes.nome," +
                    ":clientes.cpf," +
                    ":clientes.dataNascimento," +
                    ":clientes.ddd," +
                    ":clientes.telefone," +
                    ":clientes.email," +
                    ":clientes.cep," +
                    ":clientes.cidade," +
                    ":clientes.estado," +
                    ":clientes.bairro," +
                    ":clientes.rua," +
                    ":clientes.numero" +
                    ")"
    )
    void insereCliente(@BindBean("clientes") Clientes clientes);

    @SqlUpdate(
            "UPDATE CLIENTES SET " +
                    "nome=:clientes.nome," +
                    "ddd=:clientes.ddd," +
                    "telefone=:clientes.telefone," +
                    "email=:clientes.email," +
                    "cep=:clientes.cep," +
                    "cpf=:clientes.cpf," +
                    "cidade=:clientes.cidade," +
                    "estado=:clientes.estado," +
                    "bairro=:clientes.bairro," +
                    "rua=:clientes.rua," +
                    "numero=:clientes.numero, " +
                    "data_nascimento=:clientes.dataNascimento " +
                    "WHERE id=:clientes.id"
    )
    void atualizaCliente(@BindBean("clientes") Clientes clientes);

    @RegisterBeanMapper(Clientes.class)
    @SqlQuery("SELECT * FROM CLIENTES (NOLOCK) WHERE id=:id")
    Clientes pegaClientesPeloID(@Bind("id") Long id);

    @SqlUpdate("DELETE FROM CLIENTES WHERE id=:id")
    void deleteCliente(@Bind("id") Long id);

    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM CLIENTES WHERE id=:id), 1, 0)")
    boolean existeCliente(@Bind("id") Long id);

    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM CLIENTES WHERE cpf = :cpf AND id != :id), 1, 0)")
    boolean existeCpf(@Bind("cpf") String cpf, @Bind("id") Long id);
}
