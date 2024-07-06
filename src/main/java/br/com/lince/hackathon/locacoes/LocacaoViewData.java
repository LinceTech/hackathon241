package br.com.lince.hackathon.locacoes;

import br.com.lince.hackathon.gerentes.Gerentes;

import java.util.HashMap;
import java.util.List;

public class LocacaoViewData {
    private List<Locacao> locacoes;
    private Locacao locacao;
    private LocacaoFiltro locaoFiltro;
    private HashMap<String, String> errors;
    private int page;
    private int pageSize;
    private int count;

    public LocacaoViewData() {
    }
    public LocacaoViewData(HashMap<String, String> errors, Locacao locacao) {
        this.errors = errors;
        this.locacao = locacao;
    }
    public LocacaoViewData(HashMap<String, String> errors, Locacao locacao,List<Locacao> locacoes) {
        this.errors = errors;
        this.locacao = locacao;
        this.locacoes = locacoes;
    }

    public LocacaoViewData(List<Locacao> locacoes, int page, int pageSize, int count, LocacaoFiltro locacaoFiltro) {
        this.locacoes = locacoes;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.locaoFiltro = locacaoFiltro;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocacaoFiltro getLocaoFiltro() {
        return locaoFiltro;
    }

    public void setLocaoFiltro(LocacaoFiltro locaoFiltro) {
        this.locaoFiltro = locaoFiltro;
    }
    public HashMap<String, String> getErrors() {
        return errors;
    }
    public Locacao getLocacao() {
        return locacao;
    }
}