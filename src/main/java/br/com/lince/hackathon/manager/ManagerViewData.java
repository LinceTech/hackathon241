package br.com.lince.hackathon.manager;

import br.com.lince.hackathon.client.Client;
import br.com.lince.hackathon.foo.Foo;
import br.com.lince.hackathon.util.State;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class ManagerViewData {

    private final Manager manager;
    private final List<Manager> managers;
    private final LocalDateTime dateTime;
    private final List<State> states;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;

    public ManagerViewData(
            List<Manager> managers,
            LocalDateTime dateTime,
            List<State> states,
            int page,
            int pageSize,
            int count
    ) {
        this.managers = managers;
        this.dateTime = dateTime;
        this.page = page;
        this.states = states;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = null;
        this.manager = null;
    }

    public ManagerViewData(
            HashMap<String, String> errors,
            Manager manager,
            List<Manager> managers,
            LocalDateTime dateTime,
            List<State> states,
            int page,
            int pageSize,
            int count
    ) {
        this.managers = managers;
        this.dateTime = dateTime;
        this.page = page;
        this.states = states;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = errors;
        this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }

    public List<Manager> getManagers() {
        return managers;
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

    public List<State> getStates() {
        return states;
    }

    public int getPrevious() {
        return page - 1;
    }

    public int getNext() {
        return getPage() < getTotalPages() ? page + 1 : 0;
    }
}
