package br.com.lince.hackathon.time7;

import br.com.lince.hackathon.foo.Foo;

import java.util.Objects;

public class Cliente {
    public Cliente() {
    }

    /**
     * Construir uma instância de Cliente.
     *
     * @param id valor para id
     * @param nome valor para nome
     */
    public Cliente(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    /**
     * Atributo id
     */
    private int id;

    /**
     * Atributo nome
     */
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cliente cliente = (Cliente) o;
        return id == cliente.id && Objects.equals(nome, cliente.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
