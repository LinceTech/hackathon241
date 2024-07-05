package br.com.lince.hackathon.clientes;

import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.customizer.DefineNamedBindings;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface ClientesRepository {
    @RegisterBeanMapper(Clientes.class)
    @SqlQuery(
            "SELECT * FROM CLIENTES C (NOLOCK)" +
                    "ORDER BY (${orderBy}) ASC OFFSET (${pagina} * ${qtRegistros}) ROWS FETCH NEXT ${qtRegistros} ROWS ONLY"
    )
    @UseFreemarkerEngine
    List<Clientes> consultaPaginacao(
            @Define("pagina") int pagina,
            @Define("qtRegistros") int qtRegistros,
            @Define("ordernarPor") String ordernarPor
    );


    @SqlUpdate(
            "INSERT INTO CLIENTES VALUES (" +
                    ":nome," +
                    ":cpf," +
                    ":dataNascimento," +
                    ":ddd," +
                    ":telefone," +
                    ":email," +
                    ":cep," +
                    ":cidade," +
                    ":estado," +
                    ":bairro," +
                    ":rua," +
                    ":numero" +
                    ")"
    )
    void insereCliente(@BindBean Clientes clientes);

    @SqlUpdate(
            "UPDATE CLIENTES SET " +
                    "nome=:nome," +
                    "ddd=:ddd," +
                    "telefone=:telefone," +
                    "email=:email," +
                    "cep=:cep," +
                    "cidade=:cidade," +
                    "estado=:estado," +
                    "bairro=:bairro," +
                    "rua=:rua," +
                    "numero=:numero " +
                    "WHERE id=:id"
    )
    void atualizaCliente(@BindBean Clientes clientes);

    @RegisterBeanMapper(Clientes.class)
    @SqlQuery("SELECT * FROM CLIENTES (NOLOCK) WHERE id=:id")
    Clientes pegaClientesPeloID(Long id);

    @SqlUpdate("DELETE FROM CLIENTES WHERE id=:id")
    void deleteCliente(Long id);

    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM LOCACAO WHERE id_cliente = :bar AND devolvido = 0), 1, 0)")
    boolean verificaAlocacaoCliente(int id);
}
