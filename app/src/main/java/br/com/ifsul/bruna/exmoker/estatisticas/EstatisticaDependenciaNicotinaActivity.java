package br.com.ifsul.bruna.exmoker.estatisticas;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.ifsul.bruna.exmoker.EstadoSingleton;
import br.com.ifsul.bruna.exmoker.R;
import br.com.ifsul.bruna.exmoker.colecoes.TesteFargestrom;

public class EstatisticaDependenciaNicotinaActivity extends AppCompatActivity {

    private GraphView graphNivelDependencia;
    private SimpleDateFormat formatadorDeData;
    private EstadoSingleton estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatistica_dependencia_nicotina);
        formatadorDeData = new SimpleDateFormat("dd/MM");
        estado = EstadoSingleton.getInstance();
        graphNivelDependencia = findViewById(R.id.graph_nivel_dependencia);
        inicializaGrafico();
    }

    private void inicializaGrafico() {
        List<TesteFargestrom> testes = estado.getTestesFargestrom();
        Log.d("DEBUG", "Testes6: " + testes.size());
//        DataPoint[] dataPoints = new DataPoint[testes.size()];
//        for (int i = 0; i < testes.size(); i++) {
//            TesteFargestrom teste = testes.get(i);
//            Log.d("DEBUG", "Testes3: " + teste.getDataDoTeste());
//            dataPoints[i] = new DataPoint(teste.getDataDoTeste().getTime(), resultadoFargestromParaIntensidade(teste.getResultado()));
//        }
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, 4);
        Date d4 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d5 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d6 = calendar.getTime();
        DataPoint[] dataPoints = new DataPoint[]{
                new DataPoint(d1.getTime(), 2),
                new DataPoint(d2.getTime(), 5),
                new DataPoint(d3.getTime(), 3),
                new DataPoint(d4.getTime(), 4)
        };
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        series.setColor(getResources().getColor(R.color.exmoker_darker));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(15f);
        graphNivelDependencia.addSeries(series);
        graphNivelDependencia.getGridLabelRenderer()
                .setLabelFormatter(new DefaultLabelFormatter() {
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if (isValueX) {
                            return formatadorDeData.format(new Date((long) value));
                        } else {
                            return getIntensidadeDescricao((int) value);
                        }
                    }
                });

        graphNivelDependencia.getGridLabelRenderer()
                .setNumHorizontalLabels(4);
        graphNivelDependencia.getGridLabelRenderer().setNumVerticalLabels(5);
        graphNivelDependencia.getGridLabelRenderer()
                .setHumanRounding(false);
        graphNivelDependencia.getViewport().setXAxisBoundsManual(true);
        calendar.setTime(d1);
        calendar.add(Calendar.DATE, -1);
        graphNivelDependencia.getViewport().setMinX(calendar.getTimeInMillis());
        calendar.setTime(d4);
        calendar.add(Calendar.DATE, 1);
        graphNivelDependencia.getViewport().setMaxX(calendar.getTimeInMillis());
        graphNivelDependencia.getViewport().setYAxisBoundsManual(true);
        graphNivelDependencia.getViewport().setMinY(1);
        graphNivelDependencia.getViewport().setMaxY(5);
        graphNivelDependencia.getViewport().setScrollable(true);

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