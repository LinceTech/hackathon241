package br.com.lince.hackathon.locacao;

import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface LocacaoRepository {
    @SqlUpdate(
            "INSERT INTO LOCACAO VALUES (" +
                    ":idCliente," +
                    ":idGerente," +
                    ":idVeiculo," +
                    ":dataInicio," +
                    ":dataEntrega," +
                    ":valorDiaria," +
                    ":percentualGerente," +
                    ":valorTotal," +
                    ":dataPagamento," +
                    ":devolvido" +
                    ")"
    )
    void insereLocacao(@BindBean Locacao locacao);


    void alteraLocacao(@BindBean Locacao locacao);
}
