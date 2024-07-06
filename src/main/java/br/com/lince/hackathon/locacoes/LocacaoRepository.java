package br.com.lince.hackathon.locacoes;

import br.com.lince.hackathon.clientes.Cliente;
import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface LocacaoRepository {
    @RegisterBeanMapper(Locacao.class)
    @UseFreemarkerEngine
    @SqlQuery("SELECT * FROM locacoes ORDER BY id")
    List<Locacao> getAll();
}
