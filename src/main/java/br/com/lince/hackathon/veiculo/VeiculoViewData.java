package br.com.lince.hackathon.veiculo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * Classe utilizada para agregar todos os dados necessários para alimentar o template veiculo/page
 */
public class VeiculoViewData {

    /**
     * Construtor utilizado para renderizar a página com a lista de veiculos.
     *
     * @param veiculos lista de veiculos
     * @param dateTime data e hora
     * @param page     número da pagina
     * @param pageSize número de itens da página atual
     * @param count    número total de foos
     */
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

    /**
     * Construtor utilizado para renderizar a página com a lista de veiculos.
     *
     * @param errors   mapa contendo os errors que ocorreram no formulário
     * @param veiculo  item a ser alimentado no formulário de cadastro/edição
     * @param veiculos lista de veiculos
     * @param dateTime data e hora
     * @param page     número da pagina
     * @param pageSize número de itens da página atual
     * @param count    número total de veiculos
     */
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
        return Double.valueOf(Math.ceil(Integer.valueOf(count).doubleValue() / pageSize)).intValue();
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
