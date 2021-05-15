package br.com.ifsul.bruna.exmoker;

public enum OpcoesMenu {
    HOME(1),
    CONQUISTAS(2),
    ESTATISTICAS(3),
    INFORMACOES(4);

    private final int opcao;

    OpcoesMenu(int opcao) {
        this.opcao = opcao;
    }

    public int getOpcao() {
        return opcao;
    }
}
