package br.com.lince.hackathon.gerentes;

import java.time.LocalDate;

public class Gerentes {
    private Long id;
    private String nome;
    private String cpf;
    private int ddd;
    private long telefone;
    private String email;
    private String cidade;
    private String estado;
    private double percentualComissao;
    private LocalDate dataContratacao;

    public Gerentes() {
    }

    public Gerentes(
            Long id,
            String nome,
            String cpf,
            int ddd,
            long telefone,
            String email,
            String cidade,
            String estado,
            double percentualComissao,
            LocalDate dataContratacao
    ) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.ddd = ddd;
        this.telefone = telefone;
        this.email = email;
        this.cidade = cidade;
        this.estado = estado;
        this.percentualComissao = percentualComissao;
        this.dataContratacao = dataContratacao;
    }
}
