package br.com.ifsul.bruna.exmoker.estatisticas;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ifsul.bruna.exmoker.EstadoSingleton;
import br.com.ifsul.bruna.exmoker.R;

public class EstatisticaBeneficioSaude extends AppCompatActivity {

    private RecyclerView rvBeneficios;
    private RecyclerView.LayoutManager rvlManager;
    private ProgressoTimeLineAdapter pgAdapter;
    private List<ProgressoBeneficio> progressos;
    private EstadoSingleton estado;
    private Integer qtdAtingido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatistica_beneficio_saude);
        estado = EstadoSingleton.getInstance();

        inicializaProgressos();
        inicializaRecycleView();
    }

    private void inicializaProgressos() {
        qtdAtingido = 0;
        Date dataPrimeiroCigarro = estado.getDataUltimoCigarro();
        Date dataAtual = new Date();
        long diferenca = dataAtual.getTime() - dataPrimeiroCigarro.getTime();
        double segundos = diferenca / 1000;
        double minutos = segundos / 60;
        double horas = minutos / 60;
        double dias = horas / 24;
        double semanas = dias / 7;
        double anos = dias / 365;

        progressos = new ArrayList<>();
        int p = calculaPorcentagem(minutos, 20);
        if (p == 100) qtdAtingido++;
        progressos.add(new ProgressoBeneficio(
                "A pressão sanguínea e a pulsação voltam ao normal", p + "%", p));

        p = calculaPorcentagem(horas, 2);
        if (p == 100) qtdAtingido++;
        progressos.add(new ProgressoBeneficio(
                "Não há mais nicotina circulando no sangue", p + "%", p));

        p = calculaPorcentagem(horas, 8);
        if (p == 100) qtdAtingido++;
        progressos.add(new ProgressoBeneficio(
                "O nível de oxigênio no sangue se normaliza", p + "%", p));

        p = calculaPorcentagem(horas, 24);
        if (p == 100) qtdAtingido++;
        progressos.add(new ProgressoBeneficio(
                "Os pulmões já funcionam melhor", p + "%", p));

        p = calculaPorcentagem(dias, 2);
        if (p == 100) qtdAtingido++;
        progressos.add(new ProgressoBeneficio(
                "O olfato já sente melhor os cheiros e o paladar percebe melhor a comida", p + "%", p));

        p = calculaPorcentagem(semanas, 3);
        if (p == 100) qtdAtingido++;
        progressos.add(new ProgressoBeneficio(
                "A respiração se torna mais fácil, e a circulação melhora", p + "%", p));

        p = calculaPorcentagem(anos, 1);
        if (p == 100) qtdAtingido++;
        progressos.add(new ProgressoBeneficio(
                "O risco de morte por infarto é reduzido à metade", p + "%", p));

        p = calculaPorcentagem(anos, 10);
        if (p == 100) qtdAtingido++;
        progressos.add(new ProgressoBeneficio(
                "O risco de sofrer infarto será igual ao das pessoas que nunca fumaram.", p + "%", p));

        TextView tvProgressoQtd = findViewById(R.id.tv_progresso_qtd);
        tvProgressoQtd.setText(qtdAtingido + "/" + progressos.size() + " benefícios atingidos");
    }

    private Integer calculaPorcentagem(double passado, Integer necessario) {
        Integer porcentagem = (int) ((passado * 100) / necessario);
        if (porcentagem > 100) porcentagem = 100;
        return porcentagem;
    }

    private void inicializaRecycleView() {
        pgAdapter = new ProgressoTimeLineAdapter(progressos);
        rvlManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvBeneficios = findViewById(R.id.rv_beneficios);
        rvBeneficios.setAdapter(pgAdapter);
        rvBeneficios.setLayoutManager(rvlManager);
    }
}