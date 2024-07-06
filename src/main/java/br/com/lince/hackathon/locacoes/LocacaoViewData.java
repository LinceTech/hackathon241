package br.com.lince.hackathon.locacoes;

import java.util.HashMap;
import java.util.List;

public class LocacaoViewData {
    private HashMap<String, String> errors;
    private Locacao locacao;
    private List<Locacao> locacoes;

    public HashMap<String, String> getErrors() {
        return errors;
    }
    public Locacao getLocacao() {
        return locacao;
    }


    public LocacaoViewData() {
    }
    public LocacaoViewData(HashMap<String, String> errors, Locacao locacao) {
        this.errors = errors;
        this.locacao = locacao;
    }
    public LocacaoViewData(HashMap<String, String> errors, Locacao locacao,List<Locacao> locacoes) {
        this.errors = errors;
        this.locacao = locacao;
        this.locacoes = locacoes;
    }


}