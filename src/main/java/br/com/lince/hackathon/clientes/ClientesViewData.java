package br.com.lince.hackathon.clientes;

import java.util.List;

public class ClientesViewData {
    private final List<Clientes> clientes;

    public ClientesViewData(List<Clientes> clientes) {
        this.clientes = clientes;
    }

    public List<Clientes> getClientes() {
        return clientes;
    }
}