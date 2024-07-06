package br.com.lince.hackathon.clientes;


import br.com.lince.hackathon.gerentes.Gerentes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientesViewData {

    /**
     * Construtor utilizado para renderizar a página com a lista de clientes.
     *
     * @param errors   mapa contendo os errors que ocorreram no formulário
     * @param cliente      item a ser alimentado no formulário de cadastro/edição
     * @param clientes      item a ser alimentado com lista de clientes

     */
    private  HashMap<String, String> errors;
    private  Cliente cliente;
    private  List<Cliente> clientes;
    private  int page;
    private  int count;
    private  int total;
    private  int pageSize;

    public ClientesViewData(
            List<Cliente> clientes,
            Cliente cliente,
            HashMap<String, String> errors
    ) {
        this.errors = errors;
        this.clientes = clientes;
        this.cliente = cliente;
    }

    public ClientesViewData(
            Cliente cliente, HashMap<String, String> errors,
            List<Cliente> clientes
    ) {
        this.cliente = cliente;
        this.errors = errors;
        this.clientes = clientes;
    }

    public ClientesViewData(
            HashMap<String, String> errors,
            Cliente cliente
            ) {
        this.errors = errors;
        this.cliente = cliente;
        this.clientes = null;
    }
    public ClientesViewData(List<Cliente> clientes, int page, int pageSize, int count) {
        this.clientes = clientes;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
    }

    public ClientesViewData(){}

    public HashMap<String, String> getErrors() {
        return errors;
    }
    public int getPage() {
        return page + 1;
    }


    public List<Cliente> getClientes() {
        return clientes;
    }

    public Cliente getCliente() {
        return cliente;
    }


}
