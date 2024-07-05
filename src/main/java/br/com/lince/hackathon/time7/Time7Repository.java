package br.com.lince.hackathon.time7;

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
    @SqlQuery("SELECT *, DtContrata " +
              "FROM Gerente ORDER BY Id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Gerente> selectPageGerente(@Define("page") int page, @Define("count") int count);


    @RegisterBeanMapper(Gerente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT *, DtContrata " +
              "FROM Gerente WHERE Id = :id")
    Gerente findByBarGerente(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM Gerente WHERE id = :id), 1, 0)")
    boolean existsGerente(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM Gerente WHERE id = :id")
    void deleteGerente(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO Gerente(Id, Nome, CPF, Telefone, Email, Cidade, Estado, Comissao, DtContrata) " +
               "VALUES (:gerente.nome       , " +
                       ":gerente.cpf        , " +
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
    @SqlQuery("SELECT *, DtNascimento " +
            "FROM Cliente ORDER BY Id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Cliente> selectPageCliente(@Define("page") int page, @Define("count") int count);


    @RegisterBeanMapper(Cliente.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT *, DtNascimento " +
            "FROM Cliente WHERE Id = :id")
    Cliente findByBarCliente(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM Cliente WHERE id = :id), 1, 0)")
    boolean existsCliente(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM Cliente WHERE id = :id")
    void deleteCliente(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO Cliente(Id, Nome, CPF, DtNascimento, Telefone, Email, CEP, Cidade, Estado, Bairro, Rua, Numero) " +
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
                                   "Nome      = :cliente.nome   ," +
                                   "CPF       = :cliente.cpf    ," +
                                   "DtNasc    = :cliente.dtNasc ," +
                                   "Telefo    = :cliente.telefo ," +
                                   "Email     = :cliente.email  ," +
                                   "CEP       = :cliente.cep    ," +
                                   "Cidade    = :cliente.cidade ," +
                                   "Estado    = :cliente.estado ," +
                                   "Bairro    = :cliente.bairro ," +
                                   "Rua       = :cliente.rua    ," +
                                   "Numero    = :cliente.numero  " +
                                   "WHERE Id  = :cliente.id      ")
    void updateCliente(@BindBean("cliente") Cliente cliente);



    //VEICULO
    @RegisterBeanMapper(Veiculo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT *, DtInicio, DtFinal, DtPagamento " +
            "FROM Veiculo ORDER BY Id OFFSET (${page} * ${count}) ROWS FETCH NEXT ${count} ROWS ONLY")
    List<Veiculo> selectPageVeiculo(@Define("page") int page, @Define("count") int count);


    @RegisterBeanMapper(Veiculo.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT *, DtInicio, DtFinal, DtPagamento " +
            "FROM Veiculo WHERE Id = :id")
    Veiculo findByBarVeiculo(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM Veiculo WHERE id = :id), 1, 0)")
    boolean existsVeiculo(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM Veiculo WHERE id = :id")
    void deleteVeiculo(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO Veiculo(Id, Cliente, Gerente, Veiculo, DtInicio, DtFinal, CustoDiaria, ComissaoGerente, ValorTotal, DtPagamento) " +
            "VALUES (:veiculo.cliente         , " +
            ":veiculo.gerente         , " +
            ":veiculo.veiculo         , " +
            ":veiculo.dtInicio        , " +
            ":veiculo.dtFinal         , " +
            ":veiculo.custoDiaria     , " +
            ":veiculo.comissaoGerente , " +
            ":veiculo.valorTotal      , " +
            ":veiculo.dtPagamento      )")
    void insertVeiculo(@BindBean("veiculo") Veiculo veiculo);


    @UseFreemarkerEngine
    @SqlUpdate("UPDATE Veiculo SET " +
                                   "Cliente         = :veiculo.cliente         ," +
                                   "Gerente         = :veiculo.gerente         ," +
                                   "Veiculo         = :veiculo.veiculo         ," +
                                   "DtInicio        = :veiculo.dtInicio        ," +
                                   "DtFinal         = :veiculo.dtFinal         ," +
                                   "CustoDiaria     = :veiculo.custoDiaria     ," +
                                   "ComissaoGerente = :veiculo.comissaoGerente ," +
                                   "ValorTotal      = :veiculo.valorTotal      ," +
                                   "DtPagamento     = :veiculo.dtPagamento      " +
                                   "WHERE Id        = :veiculo.id               ")
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
    Locacao findByBarLocacao(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlQuery("SELECT IIF(EXISTS(SELECT 1 FROM Locacao WHERE id = :id), 1, 0)")
    boolean existsLocacao(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("DELETE FROM Locacao WHERE id = :id")
    void deleteLocacao(@Bind("id") int id);


    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO Locacao(Marca, Modelo, Placa, Cor, AnoFabrica, CustoDiaria, Descricao, TipoCombustivel) " +
               "VALUES (:locacao.marca           , " +
                       ":locacao.modelo          , " +
                       ":locacao.placa           , " +
                       ":locacao.cor             , " +
                       ":locacao.anoFabrica      , " +
                       ":locacao.custoDiaria     , " +
                       ":locacao.descricao       , " +
                       ":locacao.tipoCombustivel  )")
    void insertLocacao(@BindBean("locacao") Locacao locacao);


    @UseFreemarkerEngine
    @SqlUpdate("UPDATE Locacao SET " +
                                   "Marca           = :locacao.marca           ," +
                                   "Modelo          = :locacao.modelo          ," +
                                   "Placa           = :locacao.placa           ," +
                                   "Cor             = :locacao.cor             ," +
                                   "AnoFabrica      = :locacao.anoFabrica      ," +
                                   "CustoDiaria     = :locacao.custoDiaria     ," +
                                   "Descricao       = :locacao.descricao       ," +
                                   "TipoCombustivel = :locacao.tipoCombustivel  " +
                                   "WHERE Id        = :locacao.id               ")
    void updateLocacao(@BindBean("locacao") Locacao locacao);
}
