package br.com.lince.hackathon.time7;

import java.util.Objects;

public class Gerente {
    private int    id         ;
    private String nome       ;
    private float  cpf        ;
    private float  telefone   ;
    private String email      ;
    private String cidade     ;
    private String estado     ;
    private float  comissao   ;
    private int    dtContrata ;

    public Gerente() {
    }

    public Gerente(int    id        ,
                   String nome      ,
                   float  cpf       ,
                   float  telefone  ,
                   String email     ,
                   String cidade    ,
                   String estado    ,
                   float  comissao  ,
                   int    dtContrata ) {

        this.id         = id         ;
        this.nome       = nome       ;
        this.cpf        = cpf        ;
        this.telefone   = telefone   ;
        this.email      = email      ;
        this.cidade     = cidade     ;
        this.estado     = estado     ;
        this.comissao   = comissao   ;
        this.dtContrata = dtContrata ;
    }

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

    public float getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public float getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getComissao() {
        return comissao;
    }

    public void setComissao(float comissao) {
        this.comissao = comissao;
    }

    public int getDtContrata() {
        return dtContrata;
    }

    public void setDtContrata(int dtContrata) {
        this.dtContrata = dtContrata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gerente gerente = (Gerente) o;
        return id == gerente.id && Objects.equals(nome      , gerente.nome      )
                                && Objects.equals(cpf       , gerente.cpf       )
                                && Objects.equals(telefone  , gerente.telefone  )
                                && Objects.equals(email     , gerente.email     )
                                && Objects.equals(cidade    , gerente.cidade    )
                                && Objects.equals(estado    , gerente.estado    )
                                && Objects.equals(comissao  , gerente.comissao  )
                                && Objects.equals(dtContrata, gerente.dtContrata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpf, telefone, email, cidade, estado, comissao, dtContrata);
    }

    @Override
    public String toString() {
        return "Gerente {" +
                "  id        = "  + id         +
                ", nome      = '" + nome       + '\'' +
                ", cpf       = "  + cpf        +
                ", telefone  = "  + telefone   +
                ", email     = '" + email      + '\'' +
                ", cidade    = '" + cidade     + '\'' +
                ", estado    = '" + estado     + '\'' +
                ", comissao  = "  + comissao   +
                ", dtContrat = "  + dtContrata +
                '}';
    }
}
