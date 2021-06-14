package br.com.ifsul.bruna.exmoker.estatisticas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ifsul.bruna.exmoker.EstadoSingleton;
import br.com.ifsul.bruna.exmoker.R;
import br.com.ifsul.bruna.exmoker.TesteFargestromActivity;
import br.com.ifsul.bruna.exmoker.colecoes.TesteFargestrom;

public class EstatisticaDependenciaNicotinaActivity extends AppCompatActivity {

    private Button btTesteFargestrom;
    private LineChart lineChartNivelDependencia;
    private SimpleDateFormat formatadorDeData;
    private EstadoSingleton estado;
    private ArrayList<Entry> testesFargestrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatistica_dependencia_nicotina);
        formatadorDeData = new SimpleDateFormat("dd/MM");
        testesFargestrom = new ArrayList<>();
        lineChartNivelDependencia = findViewById(R.id.line_chart_nivel_dependencia);
        btTesteFargestrom = findViewById(R.id.bt_novo_teste_fargestrom);
        estado = EstadoSingleton.getInstance();
        inicializaChartNivelDependencia();

        btTesteFargestrom.setOnClickListener(v -> {
            Intent itTesteFargestrom = new Intent(EstatisticaDependenciaNicotinaActivity.this, TesteFargestromActivity.class);
            startActivity(itTesteFargestrom);
        });
    }

    private void inicializaChartNivelDependencia() {
        List<TesteFargestrom> testes = estado.getTestesFargestrom();
        for (TesteFargestrom teste : testes) {
            testesFargestrom.add(new Entry(
                    teste.getDataDoTeste().getTime(),
                    resultadoFargestromParaIntensidade(teste.getResultado())
            ));
        }
        LineDataSet dataSet = new LineDataSet(testesFargestrom, "");
        dataSet.setDrawCircles(true);
        dataSet.setDrawCircleHole(false);
        dataSet.setDrawValues(false);
        dataSet.setLineWidth(2);
        dataSet.setColor(getResources().getColor(R.color.exmoker_darker));
        dataSet.setCircleColor(getResources().getColor(R.color.exmoker_darker));
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawFilled(true);
        dataSet.setFillDrawable(getDrawable(R.drawable.exmoker_gradient));
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return formatadorDeData.format(new Date((long) value));
            }
        });
        LineData data = new LineData(dataSet);

        lineChartNivelDependencia.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return formatadorDeData.format(new Date((long) value));
            }
        });
        lineChartNivelDependencia.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return getIntensidadeDescricao((int) value);
            }
        });
        lineChartNivelDependencia.getAxisRight().setEnabled(false);
        lineChartNivelDependencia.getAxisLeft().setAxisMinimum(1);
        lineChartNivelDependencia.getAxisLeft().setAxisMaximum(5);
        lineChartNivelDependencia.getAxisLeft().setGranularity(1.0f);
        lineChartNivelDependencia.getAxisLeft().setGranularityEnabled(true);
        lineChartNivelDependencia.getLegend().setEnabled(false);
        lineChartNivelDependencia.setData(data);
        lineChartNivelDependencia.setPinchZoom(false);
        lineChartNivelDependencia.getDescription().setEnabled(false);
    }



    private Integer resultadoFargestromParaIntensidade(Integer resultado) {
        Integer intensidade = 0;
        if (resultado >= 0 && resultado <= 2) {
            intensidade = 1;
        } else if (resultado >= 3 && resultado <= 4) {
            intensidade = 2;
        } else if (resultado == 5) {
            intensidade = 3;
        } else if (resultado >= 6 && resultado <= 7) {
            intensidade = 4;
        } else if (resultado >= 8 && resultado <= 10) {
            intensidade = 5;
        }
        return intensidade;
    }

    private String getIntensidadeDescricao(Integer intensidade) {
        switch (intensidade) {
            case 1:
                return getResources().getString(R.string.str_estatisticas_nivel_dependencia_mbaixa);
            case 2:
                return getResources().getString(R.string.str_estatisticas_nivel_dependencia_baixa);
            case 3:
                return getResources().getString(R.string.str_estatisticas_nivel_dependencia_mod);
            case 4:
                return getResources().getString(R.string.str_estatisticas_nivel_dependencia_elev);
            case 5:
                return getResources().getString(R.string.str_estatisticas_nivel_dependencia_melev);
            default:
                return "Intensidade";
        }
    }
}