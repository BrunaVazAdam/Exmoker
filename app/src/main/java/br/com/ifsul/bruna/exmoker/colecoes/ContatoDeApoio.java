package br.com.ifsul.bruna.exmoker.colecoes;

public class ContatoDeApoio {
    private String nome;
    private String telefone;

    public ContatoDeApoio() {
    }

    public ContatoDeApoio(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }
}
