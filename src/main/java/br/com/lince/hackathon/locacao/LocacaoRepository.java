package br.com.lince.hackathon.locacao;

import br.com.lince.hackathon.clientes.ClienteOpcao;
import br.com.lince.hackathon.gerentes.GerenteOpcao;
import br.com.lince.hackathon.gerentes.Gerentes;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;

public interface LocacaoRepository {
    @RegisterBeanMapper(Locacao.class)
    @SqlUpdate(
            "INSERT INTO LOCACAO VALUES (" +
                    ":locacao.idCliente," +
                    ":locacao.idGerente," +
                    ":locacao.idVeiculo," +
                    ":locacao.dataInicio," +
                    ":locacao.dataEntrega," +
                    ":locacao.valorDiaria," +
                    ":locacao.percentualGerente," +
                    ":locacao.valorTotal," +
                    ":locacao.dataPagamento," +
                    ":locacao.devolvido" +
                    ")"
    )
    void insereLocacao(@BindBean("locacao") Locacao locacao);

    @SqlQuery("SELECT NOME FROM CLIENTES")
    List<String> listarNomesClientes();

    @RegisterBeanMapper(ClienteOpcao.class)
    @SqlQuery("SELECT C.id, C.nome FROM CLIENTES C (NOLOCK)")
    List<ClienteOpcao> listaOpcoesClientes();

    @RegisterBeanMapper(GerenteOpcao.class)
    @SqlQuery("SELECT G.id, G.nome FROM GERENTES G (NOLOCK)")
    List<GerenteOpcao> listaOpcoesGerentes();
}
