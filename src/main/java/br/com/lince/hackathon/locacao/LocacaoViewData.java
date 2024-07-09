package br.com.lince.hackathon.locacao;

import java.util.List;

public class LocacaoViewData {
    private final List<Locacao> locacoes;
    private final String placaVeiculo;
    private final String nomeCliente;
    private final String nomeGerente;
    private final boolean proximaPafina;
    private final int numeroPagina;

    public LocacaoViewData(
            List<Locacao> locacoes,
            String placaVeiculo,
            String nomeCliente,
            String nomeGerente,
            boolean proximaPafina,
            int numeroPagina
    ) {
        this.locacoes = locacoes;
        this.placaVeiculo = placaVeiculo;
        this.nomeCliente = nomeCliente;
        this.nomeGerente = nomeGerente;
        this.proximaPafina = proximaPafina;
        this.numeroPagina = numeroPagina;
    }

    public List<Locacao> getLocacoes() {
        return locacoes;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getNomeGerente() {
        return nomeGerente;
    }

    public boolean isProximaPafina() {
        return proximaPafina;
    }

    public int getNumeroPagina() {
        return numeroPagina;
    }
}
