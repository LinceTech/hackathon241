package br.com.lince.hackathon.locacao;

import java.util.List;
import java.util.ArrayList;

public class LocacaoRepository {
    private List<Locacao> locacoes = new ArrayList<>();

    // Methods to add, update, delete, and retrieve locacoes
    public void addLocacao(Locacao locacao) {
        locacoes.add(locacao);
    }

    public void updateLocacao(Locacao locacao) {
        for (int i = 0; i < locacoes.size(); i++) {
            if (locacoes.get(i).getId() == locacao.getId()) {
                locacoes.set(i, locacao);
                break;
            }
        }
    }

    public void deleteLocacao(int id) {
        locacoes.removeIf(locacao -> locacao.getId() == id);
    }

    public Locacao getLocacao(int id) {
        for (Locacao locacao : locacoes) {
            if (locacao.getId() == id) {
                return locacao;
            }
        }
        return null;
    }

    public List<Locacao> getAllLocacoes() {
        return locacoes;
    }
}