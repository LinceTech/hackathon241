package br.com.lince.hackathon.veiculo;

import br.com.lince.hackathon.veiculo.Veiculo;

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

    private final Veiculo veiculo;
    private final List<Veiculo> veiculos;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getIndex() {
        return page;
    }

    public int getPage() {
        return page + 1;
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
}
