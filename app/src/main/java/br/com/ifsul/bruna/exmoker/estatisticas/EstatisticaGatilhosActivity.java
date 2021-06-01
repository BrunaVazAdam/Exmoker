package br.com.ifsul.bruna.exmoker.estatisticas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ifsul.bruna.exmoker.EstadoSingleton;
import br.com.ifsul.bruna.exmoker.R;
import br.com.ifsul.bruna.exmoker.colecoes.EventoDeAjuda;

public class EstatisticaGatilhosActivity extends AppCompatActivity {

    private PieChart pieChartGatilhos;
    private PieChart pieChartLocais;
    private LineChart lineChartIntensidade;
    private EstadoSingleton estado;
    private DecimalFormat df;
    private SimpleDateFormat formatadorDeData;
    private Integer totalGatilhos;
    private Integer nervosismo;
    private Integer convFumantes;
    private Integer aumentoAlimentacao;
    private Integer sintomaAbstinencia;
    private Integer outroGatilho;
    private Integer totalLocais;
    private Integer escola;
    private Integer trabalho;
    private Integer casa;
    private Integer festa;
    private Integer outroLocal;
    private ArrayList<Entry> intensidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatistica_gatilhos);
        df = new DecimalFormat("#,###,##0.00");
        formatadorDeData = new SimpleDateFormat("dd/MM");
        estado = EstadoSingleton.getInstance();
        pieChartGatilhos = findViewById(R.id.pie_chart_gatilhos);
        pieChartLocais = findViewById(R.id.pie_chart_locais);
        lineChartIntensidade = findViewById(R.id.line_chart_intensidade);
        inicializaValores();
        inicializaChartGatilhos();
        inicializaChartLocais();
        inicializaChartIntensidade();
    }

    private void inicializaValores() {
        intensidades = new ArrayList<>();
        List<EventoDeAjuda> eventoDeAjudas = estado.getTabagista().getEventosDeAjuda();

        if (eventoDeAjudas == null) eventoDeAjudas = new ArrayList<>();
        totalGatilhos = 0;
        nervosismo = 0;
        convFumantes = 0;
        aumentoAlimentacao = 0;
        sintomaAbstinencia = 0;
        outroGatilho = 0;

        totalLocais = eventoDeAjudas.size();
        escola = 0;
        trabalho = 0;
        casa = 0;
        festa = 0;
        outroLocal = 0;
        for (EventoDeAjuda eventoDeAjuda : eventoDeAjudas) {
            intensidades.add(
                    new Entry(
                            eventoDeAjuda.getDataDoEvento().getTime(),
                            eventoDeAjuda.getIntensidadeDaAbstinencia()
                    )
            );
            if (eventoDeAjuda.getLocal()
                    .equals(getString(R.string.str_escola))) escola++;
            else if (eventoDeAjuda.getLocal()
                    .equals(getString(R.string.str_trabalho))) trabalho++;
            else if (eventoDeAjuda.getLocal()
                    .equals(getString(R.string.str_casa))) casa++;
            else if (eventoDeAjuda.getLocal()
                    .equals(getString(R.string.str_festa))) festa++;
            else if (eventoDeAjuda.getLocal()
                    .equals(getString(R.string.str_outro))) outroLocal++;

            List<String> gatilhos = eventoDeAjuda.getGatilhos();
            for (String gatilho : gatilhos) {
                totalGatilhos++;
                switch (getPosGatilho(gatilho)) {
                    case 1:
                        nervosismo++;
                        break;
                    case 2:
                        convFumantes++;
                        break;
                    case 3:
                        aumentoAlimentacao++;
                        break;
                    case 4:
                        sintomaAbstinencia++;
                        break;
                    case 5:
                        outroGatilho++;
                }
            }
        }
    }

    private void inicializaChartGatilhos() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.azul));
        colors.add(getResources().getColor(R.color.amarelo));
        colors.add(getResources().getColor(R.color.laranja));
        colors.add(getResources().getColor(R.color.dependencia_muito_baixa));
        colors.add(getResources().getColor(R.color.dependencia_elevada));

        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(nervosismo, getString(R.string.str_nervosismo)));
        entries.add(new PieEntry(convFumantes, getString(R.string.str_convivio_fumantes)));
        entries.add(new PieEntry(aumentoAlimentacao, getString(R.string.str_aumento_alimentacao)));
        entries.add(new PieEntry(sintomaAbstinencia, getString(R.string.str_sintomas_abstinencia)));
        entries.add(new PieEntry(outroGatilho, getString(R.string.str_outro)));

        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return df.format((value * 100) / totalGatilhos) + "%";
            }
        });
        pieDataSet.setValueTextSize(18);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);

        Legend legend = pieChartGatilhos.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextSize(14);
        legend.setTextColor(getResources().getColor(R.color.exmoker_darker));
        legend.setDrawInside(false);

        pieChartGatilhos.setDrawEntryLabels(false);
        pieChartGatilhos.setData(pieData);
        pieChartGatilhos.setCenterText(getString(R.string.str_frequencia_gatilhos));
        pieChartGatilhos.setCenterTextColor(getResources().getColor(R.color.exmoker_darker));
        pieChartGatilhos.setCenterTextSize(20);
        pieChartGatilhos.getDescription().setEnabled(false);
    }

    private void inicializaChartLocais() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.azul));
        colors.add(getResources().getColor(R.color.amarelo));
        colors.add(getResources().getColor(R.color.laranja));
        colors.add(getResources().getColor(R.color.dependencia_muito_baixa));
        colors.add(getResources().getColor(R.color.dependencia_elevada));

        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(escola, getString(R.string.str_escola)));
        entries.add(new PieEntry(trabalho, getString(R.string.str_trabalho)));
        entries.add(new PieEntry(casa, getString(R.string.str_casa)));
        entries.add(new PieEntry(festa, getString(R.string.str_festa)));
        entries.add(new PieEntry(outroLocal, getString(R.string.str_outro)));

        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return df.format((value * 100) / totalLocais) + "%";
            }
        });
        pieDataSet.setValueTextSize(18);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);

        Legend legend = pieChartLocais.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextSize(14);
        legend.setTextColor(getResources().getColor(R.color.exmoker_darker));
        legend.setDrawInside(false);

        pieChartLocais.setDrawEntryLabels(false);
        pieChartLocais.setData(pieData);
        pieChartLocais.setCenterText(getString(R.string.str_frequencia_local));
        pieChartLocais.setCenterTextColor(getResources().getColor(R.color.exmoker_darker));
        pieChartLocais.setCenterTextSize(20);
        pieChartLocais.getDescription().setEnabled(false);
    }

    private void inicializaChartIntensidade() {

        LineDataSet dataSet = new LineDataSet(intensidades, "");
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

        lineChartIntensidade.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return formatadorDeData.format(new Date((long) value));
            }
        });
        lineChartIntensidade.getAxisRight().setEnabled(false);
        lineChartIntensidade.getAxisLeft().setAxisMinimum(1);
        lineChartIntensidade.getAxisLeft().setAxisMaximum(5);
        lineChartIntensidade.getAxisLeft().setGranularity(1.0f);
        lineChartIntensidade.getAxisLeft().setGranularityEnabled(true);
        lineChartIntensidade.getLegend().setEnabled(false);
        lineChartIntensidade.setData(data);
        lineChartIntensidade.setPinchZoom(false);
        lineChartIntensidade.getDescription().setEnabled(false);
    }


    private Integer getPosGatilho(String gatilho) {
        String nervosimo = getString(R.string.str_nervosismo);
        String convFumantes = getString(R.string.str_convivio_fumantes);
        String aumentoAlimentacao = getString(R.string.str_aumento_alimentacao);
        String sintomaAbstinencia = getString(R.string.str_sintomas_abstinencia);
        if (gatilho.equals(nervosimo)) return 1;
        if (gatilho.equals(convFumantes)) return 2;
        if (gatilho.equals(aumentoAlimentacao)) return 3;
        if (gatilho.equals(sintomaAbstinencia)) return 4;
        return 5;
    }


}