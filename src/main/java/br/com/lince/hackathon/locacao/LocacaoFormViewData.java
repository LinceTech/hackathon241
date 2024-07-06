package br.com.lince.hackathon.locacao;

import br.com.lince.hackathon.clientes.ClienteOpcao;
import br.com.lince.hackathon.gerentes.GerenteOpcao;
//import br.com.lince.hackathon.gerentes.VeiculosOpcao;

import java.util.List;

public class LocacaoFormViewData {
    public final Locacao locacao;
    public final List<ClienteOpcao> clientes;
    public final List<GerenteOpcao> gerentes;
//    public final List<VeiculosOpcao> veiculos;

    public LocacaoFormViewData(
            Locacao locacao,
            List<ClienteOpcao> clientes,
            List<GerenteOpcao> gerentes
//            List<VeiculosOpcao> veiculos
    ) {
        this.locacao = locacao;
        this.clientes = clientes;
        this.gerentes = gerentes;
//        this.veiculos = veiculos;
    }

    public Locacao getLocacao() {
        return locacao;
    }

    public List<ClienteOpcao> getClientes() {
        return clientes;
    }

    public List<GerenteOpcao> getGerentes() {
        return gerentes;
    }
//    public List<VeiculosOpcao> getVeiculos() {
//        return veiculos;
//    }
}
