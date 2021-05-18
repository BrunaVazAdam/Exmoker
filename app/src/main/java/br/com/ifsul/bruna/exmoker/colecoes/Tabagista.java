package br.com.ifsul.bruna.exmoker.colecoes;

import java.util.List;

public class Tabagista {
    private ContatoDeApoio contatoDeApoio;
    private List<TesteFargestrom> testesFargestrom;

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

    public ContatoDeApoio getContatoDeApoio() {
        return contatoDeApoio;
    }

    public List<TesteFargestrom> getTestesFargestrom() {
        return testesFargestrom;
    }

    @Override
    public String toString() {
        return "Tabagista{" +
                "contatoDeApoio=" + contatoDeApoio +
                ", testesFargestrom=" + testesFargestrom +
                '}';
    }
}
