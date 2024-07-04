package br.com.lince.hackathon.foo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * Classe utilizada para agregar todos os dados necessários para alimentar o template fooView.hbs
 */
public class FooViewData {
    /**
     * Construtor utilizado para renderizar a página com a lista de foos.
     *
     * @param foos     lista de foos
     * @param dateTime data e hora
     * @param page     número da pagina
     * @param pageSize número de itens da página atual
     * @param count    número total de foos
     */
    public FooViewData(
            List<Foo> foos,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.foos = foos;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = null;
        this.foo = null;
    }

    /**
     * Construtor utilizado para renderizar a página com a lista de foos.
     *
     * @param errors   mapa contendo os errors que ocorreram no formulário
     * @param foo      item a ser alimentado no formulário de cadastro/edição
     * @param foos     lista de foos
     * @param dateTime data e hora
     * @param page     número da pagina
     * @param pageSize número de itens da página atual
     * @param count    número total de foos
     */
    public FooViewData(
            HashMap<String, String> errors,
            Foo foo, List<Foo> foos,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.foos = foos;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = errors;
        this.foo = foo;
    }

    private final Foo foo;
    private final List<Foo> foos;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public Foo getFoo() {
        return foo;
    }

    public List<Foo> getFoos() {
        return foos;
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
