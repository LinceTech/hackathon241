package br.com.lince.hackathon.locacao;

import br.com.lince.hackathon.cliente.Cliente;
import br.com.lince.hackathon.gerente.Gerente;
import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.time7.Time7Repository;
import br.com.lince.hackathon.veiculo.Veiculo;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe utilizada para agregar todos os dados necessários para alimentar o template cliente/page
 */
public class LocacaoViewData {

    public LocacaoViewData(Locacao locacao) {
        this.locacoes = null;
        this.dateTime = null;
        this.page = 0;
        this.pageSize = 0;
        this.count = 0;
        this.errors = null;
        this.locacao = locacao;
    }

    /**
     * Construtor utilizado para renderizar a página com a lista de foos.
     *
     * @param locacoes lista de clientes
     * @param dateTime data e hora
     * @param page     número da pagina
     * @param pageSize número de itens da página atual
     * @param count    número total de foos
     */
    public LocacaoViewData (
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
     * @param locacao  item a ser alimentado no formulário de cadastro/edição
     * @param locacoes lista de clientes
     * @param dateTime data e hora
     * @param page     número da pagina
     * @param pageSize número de itens da página atual
     * @param count    número total de foos
     */
    public LocacaoViewData(
            HashMap<String, String> errors,
            Locacao locacao,
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

    public List<Cliente> getClientes() {
        return JDBIConnection.instance().withExtension(Time7Repository.class, dao -> {
            final var clientes = dao.selectClientes();
            return clientes;
        });
    }

    public List<Gerente> getGerentes() {
        return JDBIConnection.instance().withExtension(Time7Repository.class, dao -> {
            final var gerentes = dao.selectGerentes();
            return gerentes;
        });
    }

    public List<Veiculo> getVeiculos() {
        return JDBIConnection.instance().withExtension(Time7Repository.class, dao -> {
            final var veiculos = dao.selectVeiculos();
            return veiculos;
        });
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
        return Double.valueOf(Math.ceil(Integer.valueOf(count).doubleValue() / pageSize)).intValue();
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
