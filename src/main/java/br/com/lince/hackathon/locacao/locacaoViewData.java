package br.com.lince.hackathon.locacao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * Classe utilizada para agregar todos os dados necessários para alimentar o template fooView.hbs
 */
public class locacaoViewData {

    public locacaoViewData(
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

    /**
     * Construtor utilizado para renderizar a página com a lista de foos.
     *
     * @param errors   mapa contendo os errors que ocorreram no formulário
     * @param locacao      item a ser alimentado no formulário de cadastro/edição
     * @param locacaos     lista de foos
     * @param dateTime data e hora
     * @param page     número da pagina
     * @param pageSize número de itens da página atual
     * @param count    número total de foos
     */
    public locacaoViewData(
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
