package br.com.ifsul.bruna.exmoker.colecoes;

import java.util.Date;

public class InformacoesAdicionais {
    private Double precoCigarro;
    private Integer qtdCigarroNoMaco;
    private Integer qtdCigarrosPorDia;
    private Date dataInicioTabagismo;

    public InformacoesAdicionais() {
    }

    public InformacoesAdicionais(Integer qtdCigarrosPorDia) {
        this.qtdCigarrosPorDia = qtdCigarrosPorDia;
    }

    public InformacoesAdicionais(Double precoCigarro, Integer qtdCigarroNoMaco, Date dataInicioTabagismo) {
        this.precoCigarro = precoCigarro;
        this.qtdCigarroNoMaco = qtdCigarroNoMaco;
        this.dataInicioTabagismo = dataInicioTabagismo;
    }

    public Double getPrecoCigarro() {
        return precoCigarro;
    }

    public void setPrecoCigarro(Double precoCigarro) {
        this.precoCigarro = precoCigarro;
    }

    public Integer getQtdCigarroNoMaco() {
        return qtdCigarroNoMaco;
    }

    public void setQtdCigarroNoMaco(Integer qtdCigarroNoMaco) {
        this.qtdCigarroNoMaco = qtdCigarroNoMaco;
    }

    public Integer getQtdCigarrosPorDia() {
        return qtdCigarrosPorDia;
    }

    public void setQtdCigarrosPorDia(Integer qtdCigarrosPorDia) {
        this.qtdCigarrosPorDia = qtdCigarrosPorDia;
    }

    public Date getDataInicioTabagismo() {
        return dataInicioTabagismo;
    }

    public void setDataInicioTabagismo(Date dataInicioTabagismo) {
        this.dataInicioTabagismo = dataInicioTabagismo;
    }
}
