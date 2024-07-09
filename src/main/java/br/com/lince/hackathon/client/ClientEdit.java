package br.com.lince.hackathon.client;

import br.com.lince.hackathon.manager.Manager;
import br.com.lince.hackathon.util.State;

import java.util.List;

public class ClientEdit {

    private final Client client;
    private final List<State> states;

    public ClientEdit(Client client, List<State> states) {
        this.client = client;
        this.states = states;
    }

    public Client getClient() {
        return client;
    }

    public List<State> getStates() {
        return states;
    }
}
