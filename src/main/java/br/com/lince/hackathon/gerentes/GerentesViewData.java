package br.com.lince.hackathon.gerentes;

import br.com.lince.hackathon.foo.Foo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class GerentesViewData {
    /**
     * Construtor utilizado para renderizar a página com a lista de foos.
     *
     * @param gerentes     lista de foos
     * @param dateTime data e hora
     * @param page     número da pagina
     * @param pageSize número de itens da página atual
     * @param count    número total de foos
     */
    public GerentesViewData(
            List<Gerentes> gerentes,
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

    /**
     * Construtor utilizado para renderizar a página com a lista de foos.
     *
     * @param errors   mapa contendo os errors que ocorreram no formulário
     * @param gerente      item a ser alimentado no formulário de cadastro/edição
     * @param gerentes     lista de foos
     * @param dateTime data e hora
     * @param page     número da pagina
     * @param pageSize número de itens da página atual
     * @param count    número total de foos
     */
    public GerentesViewData(
            HashMap<String, String> errors,
            Gerentes gerente,
            List<Gerentes> gerentes,
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

    private final Gerentes gerente;
    private final List<Gerentes> gerentes;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;


    public HashMap<String, String> getErrors() {
        return errors;
    }

    public Gerentes getGerente() {
        return gerente;
    }

    public List<Gerentes> getGerentes() {
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