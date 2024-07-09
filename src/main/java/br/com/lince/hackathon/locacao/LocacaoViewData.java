package br.com.lince.hackathon.locacao;

import br.com.lince.hackathon.locacao.Locacao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class LocacaoViewData {
    public LocacaoViewData(
            List<Locacao> locacoes,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.locacoes = locacoes;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = null;
        this.locacao = null;
    }


    public LocacaoViewData(
            HashMap<String, String> errors,
            Locacao locacao, List<Locacao> locacoes,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.locacoes = locacoes;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = errors;
        this.locacao = locacao;
    }

    private final Locacao locacao;
    private final List<Locacao> locacoes;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public Locacao getLocacao() {
        return locacao;
    }

    public List<Locacao> getLocacoes() {
        return locacoes;
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