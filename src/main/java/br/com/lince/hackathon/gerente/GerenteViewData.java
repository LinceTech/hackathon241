package br.com.lince.hackathon.gerente;

import br.com.lince.hackathon.gerente.Gerente;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class GerenteViewData {
    public GerenteViewData(
            List<Gerente> gerentes,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.gerentes = gerentes;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = null;
        this.gerente = null;
    }

    public GerenteViewData(
            HashMap<String, String> errors,
            Gerente gerente, List<Gerente> gerentes,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.gerentes = gerentes;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = errors;
        this.gerente = gerente;
    }

    private final Gerente gerente;
    private final List<Gerente> gerentes;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public Gerente getGerente() {
        return gerente;
    }

    public List<Gerente> getGerentes() {
        return gerentes;
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
