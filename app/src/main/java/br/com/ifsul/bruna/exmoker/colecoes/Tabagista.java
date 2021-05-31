package br.com.ifsul.bruna.exmoker.colecoes;

import java.util.Date;
import java.util.List;

public class Tabagista {
    private Boolean paradaGradual;
    private Date dataParadaGradual;
    private Date dataFinalizacaoCadastro;
    private ContatoDeApoio contatoDeApoio;
    private InformacoesAdicionais informacoesAdicionais;
    private List<TesteFargestrom> testesFargestrom;
    private List<EventoDeAjuda> eventosDeAjuda;

    public Tabagista() {
    }

    public Tabagista(ContatoDeApoio contatoDeApoio, List<TesteFargestrom> testesFargestrom) {
        this.contatoDeApoio = contatoDeApoio;
        this.testesFargestrom = testesFargestrom;
    }

    public Tabagista(ContatoDeApoio contatoDeApoio) {
        this.contatoDeApoio = contatoDeApoio;
    }

    public Tabagista(List<TesteFargestrom> testesFargestrom) {
        this.testesFargestrom = testesFargestrom;
    }

    public Date getDataFinalizacaoCadastro() {
        return dataFinalizacaoCadastro;
    }

    public void setDataFinalizacaoCadastro(Date dataFinalizacaoCadastro) {
        this.dataFinalizacaoCadastro = dataFinalizacaoCadastro;
    }

    public List<EventoDeAjuda> getEventosDeAjuda() {
        return eventosDeAjuda;
    }

    public ContatoDeApoio getContatoDeApoio() {
        return contatoDeApoio;
    }

    public List<TesteFargestrom> getTestesFargestrom() {
        return testesFargestrom;
    }

    public InformacoesAdicionais getInformacoesAdicionais() {
        return informacoesAdicionais;
    }

    public Boolean getParadaGradual() {
        return paradaGradual;
    }

    public void setParadaGradual(Boolean paradaGradual) {
        this.paradaGradual = paradaGradual;
    }

    public Date getDataParadaGradual() {
        return dataParadaGradual;
    }

    public void setDataParadaGradual(Date dataParadaGradual) {
        this.dataParadaGradual = dataParadaGradual;
    }

    @Override
    public String toString() {
        return "Tabagista{" +
                "contatoDeApoio=" + contatoDeApoio +
                ", testesFargestrom=" + testesFargestrom +
                '}';
    }
}
