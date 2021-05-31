package br.com.ifsul.bruna.exmoker.estatisticas;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

import br.com.ifsul.bruna.exmoker.EstadoSingleton;
import br.com.ifsul.bruna.exmoker.R;
import br.com.ifsul.bruna.exmoker.colecoes.InformacoesAdicionais;
import br.com.ifsul.bruna.exmoker.colecoes.Tabagista;

public class EstatisticaEconomiaFinanceiraActivity extends AppCompatActivity {

    private NumberFormat valorFormatter;
    private EstadoSingleton estado;
    private InformacoesAdicionais informacoesAdicionais;
    private TextView tvEstimativaGastoTotal;
    private TextView tvEstimativaDinheiroPoupado;
    private TextView tvEstimativaGastoDiario;
    private TextView tvEstimativaGastoSemanal;
    private TextView tvEstimativaGastoMensal;
    private TextView tvEstimativaGastoAnual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatistica_economia_financeira);
        inicializaComponentes();
        estado.getTabagistaAsync(documentSnapshot -> {
            Tabagista tabagista = documentSnapshot.toObject(Tabagista.class);
            informacoesAdicionais = tabagista.getInformacoesAdicionais();
            tvEstimativaGastoTotal.setText(valorFormatter.format(estado.calculaTotal()));
            tvEstimativaDinheiroPoupado
                    .setText(valorFormatter.format(estado.calculaEconomia()));
            tvEstimativaGastoDiario.setText(valorFormatter.format(estado.calculaGastoDiario()));
            tvEstimativaGastoSemanal.setText(valorFormatter.format(estado.calculaSemanal()));
            tvEstimativaGastoMensal.setText(valorFormatter.format(estado.calculaMensal()));
            tvEstimativaGastoAnual.setText(valorFormatter.format(estado.calculaAnual()));
        });
    }

    private void inicializaComponentes() {
        estado = EstadoSingleton.getInstance();
        valorFormatter = NumberFormat.getCurrencyInstance();
        tvEstimativaGastoTotal = findViewById(R.id.ri_tv_estimativa_gasto_total);
        tvEstimativaDinheiroPoupado = findViewById(R.id.ri_tv_estimativa_dinheiro_poupado);
        tvEstimativaGastoDiario = findViewById(R.id.ri_tv_estimativa_gasto_diario);
        tvEstimativaGastoSemanal = findViewById(R.id.ri_tv_estimativa_gasto_semanal);
        tvEstimativaGastoMensal = findViewById(R.id.ri_tv_estimativa_gasto_mensal);
        tvEstimativaGastoAnual = findViewById(R.id.ri_tv_estimativa_gasto_anual);
    }


}