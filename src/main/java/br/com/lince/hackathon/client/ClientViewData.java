package br.com.lince.hackathon.client;

import br.com.lince.hackathon.util.State;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class ClientViewData {

    private final Client client;
    private final List<Client> clients;
    private final List<State> states;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;
    private final ClientFilter filter;

    public ClientViewData(
            List<Client> clients,
            LocalDateTime dateTime,
            List<State> states,
            int page,
            int pageSize,
            int count,
            ClientFilter filter
    ) {
        this.clients = clients;
        this.dateTime = dateTime;
        this.page = page;
        this.states = states;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = null;
        this.client = null;
        this.filter = filter;
    }

    public ClientViewData(
            HashMap<String, String> errors,
            Client client, List<Client> clients,
            LocalDateTime dateTime,
            List<State> states,
            int page,
            int pageSize,
            int count,
            ClientFilter filter
    ) {
        this.clients = clients;
        this.dateTime = dateTime;
        this.page = page;
        this.states = states;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = errors;
        this.client = client;
        this.filter = filter;
    }

    public List<State> getStates() {
        return states;
    }

    public Client getClient() {
        return client;
    }

    public List<Client> getClients() {
        return clients;
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

    public int getPageSize() {
        return pageSize;
    }

    public int getCount() {
        return count;
    }

    public HashMap<String, String> getErrors() {
        return errors;
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

    public ClientFilter getFilter() {
        return filter;
    }
}
