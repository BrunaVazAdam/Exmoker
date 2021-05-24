package br.com.ifsul.bruna.exmoker;

import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ernestoyaquello.com.verticalstepperform.Step;

public class MesAnoStep extends Step<Calendar> {

    private SeletorMesAnoDialog mesAnoDialog;
    private TextView tvMesAno;
    private Calendar dataSelecionada;
    private SimpleDateFormat formatadorDeData;
    private AppCompatActivity activityPai;

    protected MesAnoStep(String title, AppCompatActivity activity) {
        super(title);
        this.activityPai = activity;
        dataSelecionada = Calendar.getInstance();
        dataSelecionada.add(Calendar.MONTH, -1);
        dataSelecionada.set(Calendar.DAY_OF_MONTH, 1);
        formatadorDeData = new SimpleDateFormat("MM/yyyy");
    }

    @Override
    public Calendar getStepData() {
        return dataSelecionada;
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        return dataSelecionada.get(Calendar.MONTH)
                + "/" + dataSelecionada.get(Calendar.YEAR);
    }

    @Override
    public void restoreStepData(Calendar data) {

    }

    @Override
    protected IsDataValid isStepDataValid(Calendar stepData) {
        if (stepData.compareTo(Calendar.getInstance()) >= 0) {
            return new IsDataValid(false,
                    tvMesAno
                            .getResources()
                            .getString(R.string.str_data_invalida)
            );
        }
        return null;
    }

    @Override
    protected View createStepContentLayout() {
        mesAnoDialog = new SeletorMesAnoDialog(dataSelecionada);
        tvMesAno = new TextView(new ContextThemeWrapper(getContext(), R.style.Title));
        tvMesAno.setText(formatadorDeData.format(dataSelecionada.getTime()));
        tvMesAno.setOnClickListener(v -> {
            mesAnoDialog.setListener((view, year, month, dayOfMonth) -> {
                dataSelecionada.set(year, month, dayOfMonth);
                markAsCompletedOrUncompleted(true);
                tvMesAno.setText(formatadorDeData.format(dataSelecionada.getTime()));
            });
            mesAnoDialog.show(activityPai.getSupportFragmentManager(), "mesAno");
        });
        return tvMesAno;
    }

    @Override
    protected void onStepOpened(boolean animated) {

    }

    @Override
    protected void onStepClosed(boolean animated) {

    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated) {

    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated) {

    }
}
