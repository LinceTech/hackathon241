package br.com.lince.hackathon.locacoes;

import br.com.lince.hackathon.gerentes.Gerentes;

import java.util.HashMap;
import java.util.List;

public class LocacaoViewData {
    private List<Locacao> locacoes;
    private int page;
    private int pageSize;
    private int count;
    private LocacaoFiltro locaoFiltro;

    public LocacaoViewData(List<Locacao> locacoes, int page, int pageSize, int count, LocacaoFiltro locacaoFiltro) {
        this.locacoes = locacoes;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.locaoFiltro = locacaoFiltro;
    }

    public List<Locacao> getLocacoes() {
        return locacoes;
    }

    public void setLocacoes(List<Locacao> locacoes) {
        this.locacoes = locacoes;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
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
}