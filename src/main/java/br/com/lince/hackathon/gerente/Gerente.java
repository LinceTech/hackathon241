package br.com.lince.hackathon.gerente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Gerente {
    private int    id           ;
    private String nome         ;
    private long   cpf          ;
    private int    dtNascimento ;
    private long   telefone     ;
    private String email        ;
    private int    cep          ;
    private String cidade       ;
    private String estado       ;
    private float  comissao     ;
    private int    dtContrata   ;

    public Gerente() {
    }

    public Gerente(int    id           ,
                   String nome         ,
                   long   cpf          ,
                   int    dtNascimento ,
                   long   telefone     ,
                   String email        ,
                   int    cep          ,
                   String cidade       ,
                   String estado       ,
                   float  comissao     ,
                   int    dtContrata   ) {

        this.id           = id            ;
        this.nome         = nome          ;
        this.cpf          = cpf           ;
        this.dtNascimento = dtNascimento  ;
        this.telefone     = telefone      ;
        this.email        = email         ;
        this.cep          = cep           ;
        this.cidade       = cidade        ;
        this.estado       = estado        ;
        this.comissao     = comissao      ;
        this.dtContrata   = dtContrata    ;
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

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public long getTelefone() {
        return telefone;
    }

    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCep() {
        return cep;
    }

    public String getCepFormat() {
        String cepStr = cep + "";
        String cepFormat = cepStr.replaceFirst("(\\d{5})(\\d{3})", "$1-$2");
        return cepFormat;
    }

    public void setCep(int cep) {
        this.cep = cep;
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

    public String getDtContrataFormat() throws ParseException {
        if (dtContrata == 0) return "";
        Date data = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(dtContrata));
        return new SimpleDateFormat("yyyy-MM-dd").format(data);
    }

    public void setDtContrata(int dtContrata) {
        this.dtContrata = dtContrata;
    }

    public int getDtNascimento() {
        return dtNascimento;
    }

    public String getDtNascimentoFormat() throws ParseException {
        if (dtContrata == 0) return "";
        Date data = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(dtNascimento));
        return new SimpleDateFormat("yyyy-MM-dd").format(data);
    }

    public void setDtNascimento(int dtNascimento) {
        this.dtNascimento = dtNascimento;
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
        return id == gerente.id && Objects.equals(nome         , gerente.nome        )
                                && Objects.equals(cpf          , gerente.cpf         )
                                && Objects.equals(dtNascimento , gerente.dtNascimento)
                                && Objects.equals(telefone     , gerente.telefone    )
                                && Objects.equals(email        , gerente.email       )
                                && Objects.equals(cep          , gerente.cep         )
                                && Objects.equals(cidade       , gerente.cidade      )
                                && Objects.equals(estado       , gerente.estado      )
                                && Objects.equals(comissao     , gerente.comissao    )
                                && Objects.equals(dtContrata   , gerente.dtContrata  );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpf, dtNascimento, telefone, email, cep, cidade, estado, comissao, dtContrata);
    }

    @Override
    public String toString() {
        return "Gerente {" +
                "  id           = "  + id           +
                ", nome         = '" + nome         + '\'' +
                ", cpf          = "  + cpf          +
                ", dtNascimento = "  + dtNascimento +
                ", telefone     = "  + telefone     +
                ", email        = '" + email        + '\'' +
                ", cep          = "  + cep          +
                ", cidade       = '" + cidade       + '\'' +
                ", estado       = '" + estado       + '\'' +
                ", comissao     = "  + comissao     +
                ", dtContrat    = "  + dtContrata   +
                '}';
    }
}
