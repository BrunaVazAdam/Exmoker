package br.com.ifsul.bruna.exmoker.estatisticas;

public class ProgressoBeneficio {
    private String descricao;
    private String progresso;
    private Integer porcentagemProgresso;

    public ProgressoBeneficio() {
    }

    public ProgressoBeneficio(String descricao, String progresso, Integer porcentagemProgresso) {
        this.descricao = descricao;
        this.progresso = progresso;
        this.porcentagemProgresso = porcentagemProgresso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getProgresso() {
        return progresso;
    }

    public void setProgresso(String progresso) {
        this.progresso = progresso;
    }

    public Integer getPorcentagemProgresso() {
        return porcentagemProgresso;
    }

    public void setPorcentagemProgresso(Integer porcentagemProgresso) {
        this.porcentagemProgresso = porcentagemProgresso;
    }
}
