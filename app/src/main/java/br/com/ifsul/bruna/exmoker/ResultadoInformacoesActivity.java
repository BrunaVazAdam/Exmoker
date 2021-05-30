package br.com.ifsul.bruna.exmoker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import br.com.ifsul.bruna.exmoker.colecoes.InformacoesAdicionais;
import br.com.ifsul.bruna.exmoker.colecoes.Tabagista;

public class ResultadoInformacoesActivity extends AppCompatActivity {

    NumberFormat valorFormatter;
    private EstadoSingleton estado;
    private InformacoesAdicionais informacoesAdicionais;
    private Button btAvancar;
    private TextView tvEstimativaGastoTotal;
    private TextView tvEstimativaGastoDiario;
    private TextView tvEstimativaGastoSemanal;
    private TextView tvEstimativaGastoMensal;
    private TextView tvEstimativaGastoAnual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_informacoes);
        inicializaComponentes();
        estado.getTabagistaAsync(documentSnapshot -> {
            Tabagista tabagista = documentSnapshot.toObject(Tabagista.class);
            informacoesAdicionais = tabagista.getInformacoesAdicionais();
            tvEstimativaGastoTotal.setText(valorFormatter.format(calculaTotal()));
            tvEstimativaGastoDiario.setText(valorFormatter.format(calculaGastoDiario()));
            tvEstimativaGastoSemanal.setText(valorFormatter.format(calculaSemanal()));
            tvEstimativaGastoMensal.setText(valorFormatter.format(calculaMensal()));
            tvEstimativaGastoAnual.setText(valorFormatter.format(calculaAnual()));
        });
        btAvancar.setOnClickListener(v -> {
            Intent itPreMain = new Intent(ResultadoInformacoesActivity.this, PreMainActivity.class);
            startActivity(itPreMain);
            finish();
        });
    }

    private void inicializaComponentes() {
        estado = EstadoSingleton.getInstance();
        valorFormatter = NumberFormat.getCurrencyInstance();
        btAvancar = findViewById(R.id.ri_bt_avancar);
        tvEstimativaGastoTotal = findViewById(R.id.ri_tv_estimativa_gasto_total);
        tvEstimativaGastoDiario = findViewById(R.id.ri_tv_estimativa_gasto_diario);
        tvEstimativaGastoSemanal = findViewById(R.id.ri_tv_estimativa_gasto_semanal);
        tvEstimativaGastoMensal = findViewById(R.id.ri_tv_estimativa_gasto_mensal);
        tvEstimativaGastoAnual = findViewById(R.id.ri_tv_estimativa_gasto_anual);
    }

    private Double calculaGastoDiario() {
        Double gastoPorCigarro = informacoesAdicionais.getPrecoCigarro() / informacoesAdicionais.getQtdCigarroNoMaco();
        Double gastoDiario = gastoPorCigarro * informacoesAdicionais.getQtdCigarrosPorDia();
        return gastoDiario;
    }

    private Double calculaSemanal() {
        return calculaGastoDiario() * 7;
    }

    private Double calculaMensal() {
        return calculaGastoDiario() * 30;
    }

    private Double calculaAnual() {
        return calculaGastoDiario() * 365;
    }

    private Double calculaTotal() {
        long difEmMili = Math.abs(new Date().getTime() - informacoesAdicionais.getDataInicioTabagismo().getTime());
        long qtdDeDiasFumando = TimeUnit.DAYS.convert(difEmMili, TimeUnit.MILLISECONDS);
        return calculaGastoDiario() * qtdDeDiasFumando;
    }
}