package br.com.lince.hackathon.veiculos;

import java.util.List;

public class VeiculosViewData {
    List<Veiculos> veiculos;
    int page;
    int pageSize;
    int count;
    String campo = "id";
    String sentido = "ASC";
    VeiculoFiltro veiculoFiltro;

    public VeiculosViewData(List<Veiculos> veiculos, int page, int pageSize, int count) {
        this.veiculos = veiculos;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
    }

    public VeiculosViewData(List<Veiculos> veiculos, int page, int pageSize, int count, VeiculoFiltro veiculoFiltro) {
        this.veiculos = veiculos;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.veiculoFiltro = veiculoFiltro;
    }

    public VeiculosViewData(List<Veiculos> veiculos, int page, int pageSize, int count, VeiculoFiltro veiculoFiltro, String campo, String sentido) {
        this.veiculos = veiculos;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.veiculoFiltro = veiculoFiltro;
        this.campo = campo;
        this.sentido = sentido;
    }

    public List<Veiculos> getVeiculos() {
        return veiculos;
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

    public VeiculoFiltro getVeiculoFiltro() {
        return veiculoFiltro;
    }

    public void setVeiculoFiltro(VeiculoFiltro veiculoFiltro) {
        this.veiculoFiltro = veiculoFiltro;
    }

    public String getCampo() {
        return campo;
    }

    public String getSentido() {
        return sentido.equalsIgnoreCase("ASC") ? "DESC" : "ASC";
    }
}
