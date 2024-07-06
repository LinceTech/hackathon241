package br.com.lince.hackathon.veiculo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe utilizada para agregar todos os dados necessários para alimentar o template fooView.hbs
 */
public class veiculoViewData {
//
//    public veiculoViewData(
//            List<Veiculo> veiculos,
//            LocalDateTime dateTime,
//            int page,
//            int pageSize,
//            int count
//    ) {
//        this.veiculos = veiculos;
//        this.dateTime = dateTime;
//        this.page = page;
//        this.pageSize = pageSize;
//        this.count = count;
//        this.errors = null;
//        this.veiculo = null;
//    }

    public veiculoViewData(
            List<Veiculo> veiculos,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count,
            List<Map> marcas
    ) {
        this.veiculos = veiculos;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = null;
        this.veiculo = null;
        this.marcas = marcas;
    }

    /**
     * Construtor utilizado para renderizar a página com a lista de foos.
     *
     * @param errors   mapa contendo os errors que ocorreram no formulário
     * @param veiculo      item a ser alimentado no formulário de cadastro/edição
     * @param veiculos     lista de foos
     * @param dateTime data e hora
     * @param page     número da pagina
     * @param pageSize número de itens da página atual
     * @param count    número total de foos
     */
    public veiculoViewData(
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
        this.marcas = null;
    }

    private final Veiculo veiculo;
    private final List<Veiculo> veiculos;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;
    private final List<Map> marcas;


    public HashMap<String, String> getErrors() {
        return errors;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public List<Map> getMarcas() {return marcas;}

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
