package br.com.ifsul.bruna.exmoker.colecoes;

import java.util.Date;

public class TesteFargestrom {

    private Date dataDoTeste;
    private Integer primeiraQuestao;
    private Integer segundaQuestao;
    private Integer terceiraQuestao;
    private Integer quartaQuestao;
    private Integer quintaQuestao;
    private Integer sextaQuestao;
    private Integer resultado;

    public TesteFargestrom() {
    }

    public TesteFargestrom(Integer primeiraQuestao,
                           Integer segundaQuestao,
                           Integer terceiraQuestao,
                           Integer quartaQuestao,
                           Integer quintaQuestao,
                           Integer sextaQuestao) {
        this.dataDoTeste = new Date();
        this.primeiraQuestao = primeiraQuestao;
        this.segundaQuestao = segundaQuestao;
        this.terceiraQuestao = terceiraQuestao;
        this.quartaQuestao = quartaQuestao;
        this.quintaQuestao = quintaQuestao;
        this.sextaQuestao = sextaQuestao;
        this.resultado = calculaResultado();
    }

    public Integer calculaResultado() {
        return primeiraQuestao +
                segundaQuestao +
                terceiraQuestao +
                quartaQuestao +
                quintaQuestao +
                sextaQuestao;
    }

    public Date getDataDoTeste() {
        return dataDoTeste;
    }

    public Integer getPrimeiraQuestao() {
        return primeiraQuestao;
    }

    public void setPrimeiraQuestao(Integer primeiraQuestao) {
        this.primeiraQuestao = primeiraQuestao;
    }

    public Integer getSegundaQuestao() {
        return segundaQuestao;
    }

    public void setSegundaQuestao(Integer segundaQuestao) {
        this.segundaQuestao = segundaQuestao;
    }

    public Integer getTerceiraQuestao() {
        return terceiraQuestao;
    }

    public void setTerceiraQuestao(Integer terceiraQuestao) {
        this.terceiraQuestao = terceiraQuestao;
    }

    public Integer getQuartaQuestao() {
        return quartaQuestao;
    }

    public void setQuartaQuestao(Integer quartaQuestao) {
        this.quartaQuestao = quartaQuestao;
    }

    public Integer getQuintaQuestao() {
        return quintaQuestao;
    }

    public void setQuintaQuestao(Integer quintaQuestao) {
        this.quintaQuestao = quintaQuestao;
    }

    public Integer getSextaQuestao() {
        return sextaQuestao;
    }

    public Integer getResultado() {
        return resultado;
    }

    public void setResultado(Integer resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "TesteFargestrom{" +
                "dataDoTeste=" + dataDoTeste +
                ", primeiraQuestao=" + primeiraQuestao +
                ", segundaQuestao=" + segundaQuestao +
                ", terceiraQuestao=" + terceiraQuestao +
                ", quartaQuestao=" + quartaQuestao +
                ", quintaQuestao=" + quintaQuestao +
                ", sextaQuestao=" + sextaQuestao +
                ", resultado=" + resultado +
                '}';
    }
}
