package br.com.lince.hackathon.manager;

import br.com.lince.hackathon.client.Client;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class ManagerViewData {

    private final Manager manager;
    private final List<Manager> clients;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;

    public ManagerViewData(Manager manager, List<Manager> clients, LocalDateTime dateTime, int page, int pageSize, int count, HashMap<String, String> errors) {
        this.manager = manager;
        this.clients = clients;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = errors;
    }

    public ManagerViewData(List<Manager> clients, LocalDateTime dateTime, int page, int pageSize, int count) {
        this.clients = clients;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.manager = null;
        this.errors = null;
    }

    public Manager getManager() {
        return manager;
    }

    public List<Manager> getClients() {
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
