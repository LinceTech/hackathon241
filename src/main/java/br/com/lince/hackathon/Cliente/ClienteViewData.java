package br.com.lince.hackathon.Cliente;

import br.com.lince.hackathon.foo.Foo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class ClienteViewData {
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



    public ClienteViewData(
            HashMap<String, String> errors,
            Cliente cliente, List<Cliente> clientes,
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
