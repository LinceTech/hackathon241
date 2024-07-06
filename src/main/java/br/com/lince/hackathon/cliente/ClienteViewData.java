package br.com.lince.hackathon.cliente;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Classe utilizada para agregar todos os dados necessários para alimentar o template cliente/page
 */
public class ClienteViewData {

    public ClienteViewData(Cliente cliente) {
        this.clientes = null;
        this.dateTime = null;
        this.page = 0;
        this.pageSize = 0;
        this.count = 0;
        this.errors = null;
        this.cliente = cliente;
    }

    /**
     * Construtor utilizado para renderizar a página com a lista de foos.
     *
     * @param clientes lista de clientes
     * @param dateTime data e hora
     * @param page     número da pagina
     * @param pageSize número de itens da página atual
     * @param count    número total de foos
     */
    public ClienteViewData(
            List<Cliente> clientes,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.clientes = clientes;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = null;
        this.cliente = null;
    }

    /**
     * Construtor utilizado para renderizar a página com a lista de foos.
     *
     * @param errors   mapa contendo os errors que ocorreram no formulário
     * @param cliente  item a ser alimentado no formulário de cadastro/edição
     * @param clientes lista de clientes
     * @param dateTime data e hora
     * @param page     número da pagina
     * @param pageSize número de itens da página atual
     * @param count    número total de foos
     */
    public ClienteViewData(
            HashMap<String, String> errors,
            Cliente cliente,
            List<Cliente> clientes,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.clientes = clientes;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = errors;
        this.cliente = cliente;
    }

    private final Cliente cliente;
    private final List<Cliente> clientes;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<String> getEstados() {
        return Arrays.asList(new String[]{"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG",
                "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE"});
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
