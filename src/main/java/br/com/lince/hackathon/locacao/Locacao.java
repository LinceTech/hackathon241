package br.com.lince.hackathon.locacao;

import java.util.Objects;

public class Locacao {
    private int   id              ;
    private int   cliente         ;
    private int   gerente         ;
    private int   veiculo         ;
    private int   dtInicio        ;
    private int   dtFinal         ;
    private float custoDiaria     ;
    private float comissaoGerente ;
    private float valorTotal      ;
    private int   dtPagamento     ;

    public Locacao() {
    }

    public Locacao(int   id              ,
                   int   cliente         ,
                   int   gerente         ,
                   int   veiculo         ,
                   int   dtInicio        ,
                   int   dtFinal         ,
                   float custoDiaria     ,
                   float comissaoGerente ,
                   float valorTotal      ,
                   int   dtPagamento      ) {

        this.id              = id              ;
        this.cliente         = cliente         ;
        this.gerente         = gerente         ;
        this.veiculo         = veiculo         ;
        this.dtInicio        = dtInicio        ;
        this.dtFinal         = dtFinal         ;
        this.custoDiaria     = custoDiaria     ;
        this.comissaoGerente = comissaoGerente ;
        this.valorTotal      = valorTotal      ;
        this.dtPagamento     = dtPagamento     ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getGerente() {
        return gerente;
    }

    public void setGerente(int gerente) {
        this.gerente = gerente;
    }

    public int getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(int veiculo) {
        this.veiculo = veiculo;
    }

    public int getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(int dtInicio) {
        this.dtInicio = dtInicio;
    }

    public int getDtFinal() {
        return dtFinal;
    }

    public void setDtFinal(int dtFinal) {
        this.dtFinal = dtFinal;
    }

    public float getCustoDiaria() {
        return custoDiaria;
    }

    public void setCustoDiaria(float custoDiaria) {
        this.custoDiaria = custoDiaria;
    }

    public float getComissaoGerente() {
        return comissaoGerente;
    }

    public void setComissaoGerente(float comissaoGerente) {
        this.comissaoGerente = comissaoGerente;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getDtPagamento() {
        return dtPagamento;
    }

    public void setDtPagamento(int dtPagamento) {
        this.dtPagamento = dtPagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Locacao locacao = (Locacao) o;
        return id == locacao.id && Objects.equals(cliente         , locacao.cliente        )
                                && Objects.equals(gerente         , locacao.gerente        )
                                && Objects.equals(veiculo         , locacao.veiculo        )
                                && Objects.equals(dtInicio        , locacao.dtInicio       )
                                && Objects.equals(dtFinal         , locacao.dtFinal        )
                                && Objects.equals(custoDiaria     , locacao.custoDiaria    )
                                && Objects.equals(comissaoGerente , locacao.comissaoGerente)
                                && Objects.equals(valorTotal      , locacao.valorTotal     )
                                && Objects.equals(dtPagamento     , locacao.dtPagamento    );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cliente, gerente, veiculo, dtInicio, dtFinal, custoDiaria, comissaoGerente, valorTotal,dtPagamento);
    }

    @Override
    public String toString() {
        return "Locacao {" +
                "  id              = "  + id              +
                ", cliente         = "  + cliente         +
                ", gerente         = "  + gerente         +
                ", veiculo         = "  + veiculo         +
                ", dtInicio        = "  + dtInicio        +
                ", dtFinal         = "  + dtFinal         +
                ", custoDiaria     = "  + custoDiaria     +
                ", comissaoGerente = "  + comissaoGerente +
                ", valorTotal      = "  + valorTotal      +
                '}';
    }
}
