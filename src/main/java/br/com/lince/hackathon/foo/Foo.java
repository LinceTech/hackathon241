package br.com.lince.hackathon.foo;

import java.util.Objects;

/**
 * A classe Foo é apenas um exemplo de 'Data class', nomes da classe a atributos não tem significado algum,
 * representam apenas uma estrutura para armazenar dados de forma combinada, sem lógica ou inteligencia.
 * <br/>
 * Obs.: Em versões mais recentes do Java, essa classe seria criada como um Record.
 */
public class Foo {
    /**
     * Construtor vazio, necessário para uso com JDBI mapper
     */
    public Foo() {
    }

    /**
     * Construir uma instância de Foo.
     *
     * @param bar valor para bar
     * @param bas valor para bas
     * @param boo valor para boo
     */
    public Foo(int bar, String bas, String boo) {
        this.bar = bar;
        this.bas = bas;
        this.boo = boo;
    }

    /**
     * Atributo bar, sem significado algum
     */
    public int bar;

    /**
     * Atributo bar, sem significado algum
     */
    public String bas;

    /**
     * Atributo bar, sem significado algum
     */
    public String boo;

    public int getBar() {
        return bar;
    }

    public void setBar(int bar) {
        this.bar = bar;
    }

    public String getBas() {
        return bas;
    }

    public void setBas(String bas) {
        this.bas = bas;
    }

    public String getBoo() {
        return boo;
    }

    public void setBoo(String boo) {
        this.boo = boo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Foo foo = (Foo) o;
        return bar == foo.bar && Objects.equals(bas, foo.bas) && Objects.equals(boo, foo.boo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bar, bas, boo);
    }
}
