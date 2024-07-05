package br.com.lince.hackathon.gerentes;

import org.jdbi.v3.freemarker.UseFreemarkerEngine;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface GerenteRepository {

    @RegisterBeanMapper(Gerentes.class)
    @UseFreemarkerEngine
    @SqlUpdate("INSERT INTO gerente(Nome, CPF, Telefone, Email, Cidade, Estado, PercentualDeComissao, DataDeContratacao) VALUES (:gerentes.nome, :gerentes.cpf, :gerentes.telefone, :gerentes.email, :gerentes.cidade, :gerentes.estado, :gerentes.percentual, :gerentes.dataContratacao)")
    void insert(@BindBean("gerentes") Gerentes gerentes);
}