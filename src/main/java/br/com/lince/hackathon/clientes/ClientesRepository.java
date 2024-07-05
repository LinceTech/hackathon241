package br.com.lince.hackathon.clientes;

import br.com.lince.hackathon.gerentes.Gerentes;
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
            "SELECT * FROM CLIENTES C (NOLOCK)" +
                    " ORDER BY C.nome ASC OFFSET (${pagina} * ${qtRegistros}) ROWS FETCH NEXT ${qtRegistros} ROWS ONLY"
    )
    @UseFreemarkerEngine
    List<Clientes> consultaPaginacao(
            @Define("pagina") int pagina,
            @Define("qtRegistros") int qtRegistros
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
                    "cidade=:clientes.cidade," +
                    "estado=:clientes.estado," +
                    "bairro=:clientes.bairro," +
                    "rua=:clientes.rua," +
                    "numero=:clientes.numero " +
                    "WHERE id=:clientes.id"
    )
    void atualizaCliente(@BindBean("clientes") Clientes clientes);

    @RegisterBeanMapper(Clientes.class)
    @SqlQuery("SELECT * FROM CLIENTES (NOLOCK) WHERE id=:id")
    Clientes pegaClientesPeloID(@Bind("id") Long id);



    @SqlUpdate("DELETE FROM CLIENTES WHERE id=:id")
    void deleteCliente(@Bind("id") Long id);

    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM LOCACAO WHERE id_cliente=:id AND devolvido = 0), 1, 0)")
    boolean verificaAlocacaoCliente(@Bind("id") Long id);
}
