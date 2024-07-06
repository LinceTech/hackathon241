package br.com.lince.hackathon.clientes;

public class ClienteOpcao {
    private Long id;
    private String nome;

    public ClienteOpcao(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public ClienteOpcao() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
