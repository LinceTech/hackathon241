package br.com.lince.hackathon.time7;

import br.com.lince.hackathon.cliente.Cliente;
import br.com.lince.hackathon.gerente.Gerente;
import br.com.lince.hackathon.locacao.Locacao;
import br.com.lince.hackathon.veiculo.Veiculo;
import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface Time7Repository {


    // CONTADOR GERAL
    @UseFreemarkerEngine
    @SqlQuery("SELECT COUNT(*) FROM ${tabela}")
    int count(@Define("tabela") String tabela);



    // GERENTE
    @RegisterBeanMapper(Gerente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * " +
              "FROM Gerente ORDER BY Id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Gerente> selectPageGerente(@Define("page") int page, @Define("count") int count);


    @RegisterBeanMapper(Gerente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * " +
              "FROM Gerente WHERE Id = :id")
    Gerente findByIdGerente(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM Gerente WHERE id = :id), 1, 0)")
    boolean existsGerente(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM Gerente WHERE id = :id")
    void deleteGerente(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO Gerente(Nome, CPF,DtNascimento Telefone, Email, Cidade, Estado, Comissao, DtContrata) " +
               "VALUES (:gerente.nome       , " +
                       ":gerente.cpf        , " +
                       ":gerente.dtNascimento , " +
                       ":gerente.telefone   , " +
                       ":gerente.email      , " +
                       ":gerente.cidade     , " +
                       ":gerente.estado     , " +
                       ":gerente.comissao   , " +
                       ":gerente.dtContrata  )")
    void insertGerente(@BindBean("gerente") Gerente gerente);


    @UseFreemarkerEngine
    @SqlUpdate("UPDATE Gerente SET " +
                                   "Nome       = :gerente.nome      ," +
                                   "CPF        = :gerente.cpf       ," +
                                   "DtNascimento = :gerente.dtNascimento       ," +
                                   "Telefone   = :gerente.telefone  ," +
                                   "Email      = :gerente.email     ," +
                                   "Cidade     = :gerente.cidade    ," +
                                   "Estado     = :gerente.estado    ," +
                                   "Comissao   = :gerente.comissao  ," +
                                   "DtContrata = :gerente.dtContrata " +
                                   "WHERE Id   = :gerente.id         ")
    void updateGerente(@BindBean("gerente") Gerente gerente);



    //CLIENTE
    @RegisterBeanMapper(Cliente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * " +
            "FROM Cliente ORDER BY Id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Cliente> selectPageCliente(@Define("page") int page, @Define("count") int count);


    @RegisterBeanMapper(Cliente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * " +
            "FROM Cliente WHERE Id = :id")
    Cliente findByIdCliente(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM Cliente WHERE id = :id), 1, 0)")
    boolean existsCliente(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM Cliente WHERE id = :id")
    void deleteCliente(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO Cliente(Nome, CPF, DtNascimento, Telefone, Email, CEP, Cidade, Estado, Bairro, Rua, Numero) " +
               "VALUES (:cliente.nome         , " +
                       ":cliente.cpf          , " +
                       ":cliente.dtNascimento , " +
                       ":cliente.telefone     , " +
                       ":cliente.email        , " +
                       ":cliente.cep          , " +
                       ":cliente.cidade       ," +
                       ":cliente.estado       , " +
                       ":cliente.bairro       , " +
                       ":cliente.rua          , " +
                       ":cliente.numero        )")
    void insertCliente(@BindBean("cliente") Cliente cliente);


    @UseFreemarkerEngine
    @SqlUpdate("UPDATE Cliente SET " +
                                   "Nome         = :cliente.nome         ," +
                                   "CPF          = :cliente.cpf          ," +
                                   "DtNascimento = :cliente.dtNascimento ," +
                                   "Telefone     = :cliente.telefone     ," +
                                   "Email        = :cliente.email        ," +
                                   "CEP          = :cliente.cep          ," +
                                   "Cidade       = :cliente.cidade       ," +
                                   "Estado       = :cliente.estado       ," +
                                   "Bairro       = :cliente.bairro       ," +
                                   "Rua          = :cliente.rua          ," +
                                   "Numero       = :cliente.numero        " +
                                   "WHERE Id     = :cliente.id      ")
    void updateCliente(@BindBean("cliente") Cliente cliente);



    //VEICULO
    @RegisterBeanMapper(Veiculo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * " +
            "FROM Veiculo ORDER BY Id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Veiculo> selectPageVeiculo(@Define("page") int page, @Define("count") int count);


    @RegisterBeanMapper(Veiculo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * " +
            "FROM Veiculo WHERE Id = :id")
    Veiculo findByIdVeiculo(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM Veiculo WHERE id = :id), 1, 0)")
    boolean existsVeiculo(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM Veiculo WHERE id = :id")
    void deleteVeiculo(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO Veiculo(Marca, Modelo, Placa, Cor, AnoFabrica, CustoDiaria, Descricao, TipoCombustivel) " +
            "VALUES (:veiculo.marca            , " +
                    ":veiculo.modelo           , " +
                    ":veiculo.placa            , " +
                    ":veiculo.cor              , " +
                    ":veiculo.anoFabrica       , " +
                    ":veiculo.custoDiaria      , " +
                    ":veiculo.descricao        , " +
                    ":veiculo.tipoCombustivel  )")
    void insertVeiculo(@BindBean("veiculo") Veiculo veiculo);


    @UseFreemarkerEngine
    @SqlUpdate("UPDATE Veiculo SET " +
                                   "Marca            = :veiculo.marca           ," +
                                   "Modelo           = :veiculo.modelo          ," +
                                   "Placa            = :veiculo.placa           ," +
                                   "Cor              = :veiculo.cor             ," +
                                   "AnoFabrica       = :veiculo.anoFabrica      ," +
                                   "CustoDiaria      = :veiculo.custoDiaria     ," +
                                   "Descricao        = :veiculo.descricao       ," +
                                   "TipoCombustivel  = :veiculo.tipoCombustivel  " +
                                   "WHERE Id         = :veiculo.id               ")
    void updateVeiculo(@BindBean("veiculo") Veiculo veiculo);



    // LOCACAO
    @RegisterBeanMapper(Locacao.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * " +
            "FROM Locacao ORDER BY Id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Locacao> selectPageLocacao(@Define("page") int page, @Define("count") int count);


    @RegisterBeanMapper(Locacao.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * " +
            "FROM Locacao WHERE Id = :id")
    Locacao findByIdLocacao(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM Locacao WHERE id = :id), 1, 0)")
    boolean existsLocacao(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM Locacao WHERE id = :id")
    void deleteLocacao(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO Locacao(Cliente, Gerente, Veiculo, DtInicio, DtFinal, CustoDiaria, ComissaoGerente, ValorTotalm DtPagamento) " +
               "VALUES (:locacao.cliente         , " +
                       ":locacao.gerente         , " +
                       ":locacao.veiculo         , " +
                       ":locacao.dtInicio        , " +
                       ":locacao.dtFinal         , " +
                       ":locacao.custoDiaria     , " +
                       ":locacao.comissaoGerente , " +
                       ":locacao.valorTotal      , " +
                       ":locacao.dtPagamento      )")
    void insertLocacao(@BindBean("locacao") Locacao locacao);


    @UseFreemarkerEngine
    @SqlUpdate("UPDATE Locacao SET " +
                                   "Cliente         = :locacao.cliente         ," +
                                   "Gerente         = :locacao.gerente         ," +
                                   "Veiculo         = :locacao.veiculo         ," +
                                   "DtInicio        = :locacao.dtInicio        ," +
                                   "DtFinal         = :locacao.dtFinal         ," +
                                   "CustoDiaria     = :locacao.custoDiaria     ," +
                                   "ComissaoGerente = :locacao.comissaoGerente ," +
                                   "ValorTotal      = :locacao.valorTotal      ," +
                                   "DtPagamento     = :locacao.dtPagamento      " +
                                   "WHERE Id        = :locacao.id               ")
    void updateLocacao(@BindBean("locacao") Locacao locacao);
}
