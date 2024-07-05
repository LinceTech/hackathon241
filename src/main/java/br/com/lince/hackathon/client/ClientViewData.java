package br.com.lince.hackathon.client;

import br.com.lince.hackathon.foo.Foo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class ClientViewData {

    private final Client client;
    private final List<Client> clients;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;

    public ClientViewData(
            List<Client> clients,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.clients = clients;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = null;
        this.client = null;
    }

    public ClientViewData(
            HashMap<String, String> errors,
            Client client, List<Client> clients,
            LocalDateTime dateTime,
            int page,
            int pageSize,
            int count
    ) {
        this.clients = clients;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = errors;
        this.client = client;
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

    public int getPage() {
        return page;
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
}
