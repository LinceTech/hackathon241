package br.com.lince.hackathon.manager;

import br.com.lince.hackathon.util.State;

import java.util.List;

public class ManegerEdit {

    private final Manager manager;
    private final List<State> states;

    public ManegerEdit(Manager manager, List<State> states) {
        this.manager = manager;
        this.states = states;
    }

    public Manager getManager() {
        return manager;
    }

    public List<State> getStates() {
        return states;
    }
}
