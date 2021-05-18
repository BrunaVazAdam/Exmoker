package br.com.ifsul.bruna.exmoker;

public class OpcaoQuestao {

    private String resposta;
    private Integer valor;


    public OpcaoQuestao(String resposta, Integer valor) {
        this.resposta = resposta;
        this.valor = valor;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
}
