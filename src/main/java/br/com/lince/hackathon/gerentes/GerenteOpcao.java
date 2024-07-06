package br.com.lince.hackathon.gerentes;

public class GerenteOpcao {
    private Long id;
    private String nome;

    public GerenteOpcao(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public GerenteOpcao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
