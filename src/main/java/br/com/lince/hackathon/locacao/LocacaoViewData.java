package br.com.lince.hackathon.locacao;

import br.com.lince.hackathon.Cliente.Cliente;
import br.com.lince.hackathon.gerente.Gerente;
//import br.com.lince.hackathon.Veiculo.Veiculo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class LocacaoViewData {
    public LocacaoViewData(
            List<Cliente> clientes,
            List<Gerente> gerentes,
            //List<Veiculo> veiculos,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.clientes = clientes;
        this.gerentes = gerentes;
        //this.veiculos = veiculos;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = null;
        //this.locacao = null;
    }

    public LocacaoViewData(
            HashMap<String, String> errors,
            Cliente cliente,
            Gerente gerente,
            //Veiculo veiculo,
            List<Cliente> clientes,
            List<Gerente> gerentes,
            //List<Veiculo> veiculos,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.errors = errors;
        //this.cliente = cliente;
        //this.gerente = gerente;
        //this.veiculo = veiculo;
        this.clientes = clientes;
        this.gerentes = gerentes;
        //this.veiculos = veiculos;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        //this.locacao = locacao;
    }

    //private final Cliente cliente;
    //private final Gerente gerente;
    //private final Veiculo veiculo;
    private final List<Cliente> clientes;
    private final List<Gerente> gerentes;
    //private final List<Veiculo> veiculos;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;
    //private final Locacao locacao;
}