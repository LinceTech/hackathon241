package br.com.lince.hackathon.veiculos;

import java.util.List;

public class VeiculosViewData {
    List <Veiculos> gerentes;
    int page;
    int pageSize;
    int count;
    String campo = "nome";
    String sentido = "ASC";
    VeiculoFiltro gerenteFiltro;

    public VeiculosViewData(List<Veiculos> gerentes, int page, int pageSize, int count) {
        this.gerentes = gerentes;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
    }

    public VeiculosViewData(List<Veiculos> gerentes, int page, int pageSize, int count, VeiculoFiltro gerenteFiltro) {
        this.gerentes = gerentes;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.gerenteFiltro = gerenteFiltro;
    }

    public VeiculosViewData(List<Veiculos> gerentes, int page, int pageSize, int count, VeiculoFiltro gerenteFiltro, String campo, String sentido) {
        this.gerentes = gerentes;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.gerenteFiltro = gerenteFiltro;
        this.campo = campo;
        this.sentido = sentido;
    }

    public List<Veiculos> getGerentes() {
        return gerentes;
    }

    public int getPageSize() {
        return pageSize;
    }

    public VeiculosViewData() {
    }

    public int getPage() {
        return page+1;
    }

    public int getCount() {
        return count;
    }

    public int getTotalPages() {
        return Double.valueOf(Math.ceil(((Integer.valueOf(count).doubleValue()) / pageSize))).intValue();
    }

    public boolean getHasPrevious() {
        return page > 0;
    }

    public int getPrevious() {
        return page - 1;
    }

    public int getNext() {
        return getPage() < getTotalPages() ? page + 1 : 0;
    }

    public VeiculoFiltro getGerenteFiltro() {
        return gerenteFiltro;
    }

    public void setGerenteFiltro(VeiculoFiltro gerenteFiltro) {
        this.gerenteFiltro = gerenteFiltro;
    }

    public String getCampo() {
        return campo;
    }

    public String getSentido() {
        return sentido.equalsIgnoreCase("ASC") ? "DESC" : "ASC";
    }
}
