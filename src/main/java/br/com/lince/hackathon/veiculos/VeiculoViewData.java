package br.com.lince.hackathon.veiculos;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class VeiculoViewData {
    public VeiculoViewData(
            List<Veiculo> veiculos,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.veiculos = veiculos;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = null;
        this.veiculo = null;
    }

    public VeiculoViewData(
            HashMap<String, String> errors,
            Veiculo veiculo, List<Veiculo> veiculos,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.veiculos = veiculos;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = errors;
        this.veiculo = veiculo;
    }

    private final List<Veiculo> veiculos;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;
    private final Veiculo veiculo;

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCount() {
        return count;
    }

    public int getTotalPages() {
        return Double.valueOf(Math.ceil(((Integer.valueOf(count).doubleValue()) / pageSize))).intValue();
    }

    public HashMap<String, String> getErrors() {
        return errors;
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

}
