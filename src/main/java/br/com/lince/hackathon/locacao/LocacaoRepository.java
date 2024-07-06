package br.com.lince.hackathon.locacao;

import br.com.lince.hackathon.clientes.ClienteOpcao;
import br.com.lince.hackathon.gerentes.GerenteOpcao;
import br.com.lince.hackathon.gerentes.Gerentes;
import br.com.lince.hackathon.veiculos.VeiculoOpcao;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;

public interface LocacaoRepository {
//    @RegisterBeanMapper(Locacao.class)
//    @SqlQuery(
//            "SELECT * FROM LOCACAO C (NOLOCK) \n" +
//                    "WHERE C.id_veiculo  LIKE CONCAT('%', :locacaoFiltros.id_veiculo, '%') \n" +
//                    "AND C.id_cliente    LIKE CONCAT('%', :locacaoFiltros.id_cliente, '%') \n" +
//                    "AND C.id LIKE CONCAT('%', :locacaoFiltros.cidade, '%') \n" +
//                    "AND C.estado LIKE CONCAT('%', :locacaoFiltros.estado, '%') \n" +
//                    "ORDER BY C.nome ASC \n" +
//                    "OFFSET (${pagina} * ${qtRegistros}) \n" +
//                    "ROWS FETCH NEXT ${qtRegistros} ROWS ONLY"
//    )

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

    @RegisterBeanMapper(VeiculoOpcao.class)
    @SqlQuery(
            "SELECT V.id, V.marca, V.modelo FROM VEICULOS V (NOLOCK) \n" +
                    "WHERE V.id NOT IN (SELECT L.id FROM LOCACAO L (NOLOCK))"
    )
    List<VeiculoOpcao> listaOpcoesVeiculos();
}
