package br.com.ifsul.bruna.exmoker.colecoes;

import java.util.Date;
import java.util.List;

public class EventoDeAjuda {

    private Date dataDoEvento;
    private Boolean foiRecaida;
    private Integer quantidadeDeCigarros;
    private Integer intensidadeDaAbstinencia;
    private List<String> gatilhos;
    private String local;

    public EventoDeAjuda() {
    }

    public EventoDeAjuda(Boolean foiRecaida, Integer quantidadeDeCigarros, Integer intensidadeDaAbstinencia, List<String> gatilhos, String local) {
        this.foiRecaida = foiRecaida;
        this.quantidadeDeCigarros = quantidadeDeCigarros;
        this.intensidadeDaAbstinencia = intensidadeDaAbstinencia;
        this.gatilhos = gatilhos;
        this.local = local;
        this.dataDoEvento = new Date();
    }

    public Date getDataDoEvento() {
        return dataDoEvento;
    }

    public Boolean getFoiRecaida() {
        return foiRecaida;
    }

    public void setFoiRecaida(Boolean foiRecaida) {
        this.foiRecaida = foiRecaida;
    }

    public Integer getQuantidadeDeCigarros() {
        return quantidadeDeCigarros;
    }

    public void setQuantidadeDeCigarros(Integer quantidadeDeCigarros) {
        this.quantidadeDeCigarros = quantidadeDeCigarros;
    }

    public Integer getIntensidadeDaAbstinencia() {
        return intensidadeDaAbstinencia;
    }

    public void setIntensidadeDaAbstinencia(Integer intensidadeDaAbstinencia) {
        this.intensidadeDaAbstinencia = intensidadeDaAbstinencia;
    }

    public List<String> getGatilhos() {
        return gatilhos;
    }

    public void setGatilhos(List<String> gatilhos) {
        this.gatilhos = gatilhos;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
