package br.com.lince.hackathon.gerente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe utilizada para agregar todos os dados necessários para alimentar o template fooView.hbs
 */
public class GerenteViewData {

    private final Gerente gerente;
    private final List<Gerente> gerentes;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final Map<String, String> errors;

    /**
     * Construtor utilizado para renderizar a página com a lista de foos.
     *
     * @param gerentes     lista de foos
     * @param dateTime data e hora
     * @param page     número da pagina
     * @param pageSize número de itens da página atual
     * @param count    número total de foos
     */
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

    public Map<String, String> getErrors() {
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

    public int getPageSize() {
        return pageSize;
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
