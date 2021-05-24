package br.com.ifsul.bruna.exmoker;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;

import ernestoyaquello.com.verticalstepperform.Step;

public class ValorCigarroStep extends Step<Double> {

    private static int DELAY = 50;
    NumberFormat valorFormatter;
    private Handler handler;
    private Runnable runnable;
    private TextView tvValorDezena;
    private TextView tvValorCentavo;

    private Button btIncrementaDezena;
    private Button btDecrementaDezena;
    private Button btIncrementaCentavo;
    private Button btDecrementaCentavo;

    private Integer valorDezena;
    private Integer valorCentavo;
    private Double valorMacoCigarro;

    private View vwValorCigarro;

    protected ValorCigarroStep(String title) {
        super(title);
    }

    private void inicializaComponentes() {
        valorFormatter = NumberFormat.getCurrencyInstance();
        handler = new Handler();
        valorDezena = 0;
        valorCentavo = 0;
        valorMacoCigarro = 0.0;
        tvValorDezena = vwValorCigarro.findViewById(R.id.vl_tv_valor_dezena);
        tvValorCentavo = vwValorCigarro.findViewById(R.id.vl_tv_valor_centavo);
        btIncrementaDezena = vwValorCigarro.findViewById(R.id.ma_bt_incrementa_mes);
        btDecrementaDezena = vwValorCigarro.findViewById(R.id.vl_bt_decrementa_valor_dezena);
        btIncrementaCentavo = vwValorCigarro.findViewById(R.id.vl_bt_incrementa_valor_centavo);
        btDecrementaCentavo = vwValorCigarro.findViewById(R.id.vl_bt_decrementa_valor_centavo);
    }

    private void calculaValorMacoCigarro() {
        valorMacoCigarro = valorDezena + (double) valorCentavo / 100;
    }

    private void atualizaComOValor(Double valor) {
        valorDezena = valor.intValue();
        valorCentavo = (int) ((valor - valor.intValue()) * 10);
        valorMacoCigarro = valor;
        tvValorDezena.setText(String.format("%02d", valorDezena));
        tvValorCentavo.setText(String.format("%02d", valorCentavo));
    }

    private void incrementaDezena() {
        valorDezena++;
        if (valorDezena == 100) valorDezena = 0;
        markAsCompletedOrUncompleted(true);
        tvValorDezena.setText(String.format("%02d", valorDezena));
    }

    private void decrementaDezena() {
        valorDezena--;
        if (valorDezena == -1) valorDezena = 99;
        markAsCompletedOrUncompleted(true);
        tvValorDezena.setText(String.format("%02d", valorDezena));
    }

    private void incrementaCentavo() {
        valorCentavo++;
        if (valorCentavo == 100) valorCentavo = 0;
        markAsCompletedOrUncompleted(true);
        tvValorCentavo.setText(String.format("%02d", valorCentavo));
    }

    private void decrementaCentavo() {
        valorCentavo--;
        if (valorCentavo == -1) valorCentavo = 99;
        markAsCompletedOrUncompleted(true);
        tvValorCentavo.setText(String.format("%02d", valorCentavo));
    }

    private void configuraListenersDeValores() {
        btIncrementaDezena.setOnClickListener(v -> {
            incrementaDezena();
        });
        btIncrementaDezena.setOnLongClickListener(v -> {
            runnable = () -> {
                if (!btIncrementaDezena.isPressed()) return;
                incrementaDezena();
                handler.postDelayed(runnable, DELAY);
            };
            handler.postDelayed(runnable, DELAY);
            return true;
        });
        btDecrementaDezena.setOnClickListener(v -> {
            decrementaDezena();
        });
        btDecrementaDezena.setOnLongClickListener(v -> {
            runnable = () -> {
                if (!btDecrementaDezena.isPressed()) return;
                decrementaDezena();
                handler.postDelayed(runnable, DELAY);
            };
            handler.postDelayed(runnable, DELAY);
            return true;
        });

        btIncrementaCentavo.setOnClickListener(v -> {
            incrementaCentavo();
        });
        btIncrementaCentavo.setOnLongClickListener(v -> {
            runnable = () -> {
                if (!btIncrementaCentavo.isPressed()) return;
                incrementaCentavo();
                handler.postDelayed(runnable, DELAY);
            };
            handler.postDelayed(runnable, DELAY);
            return true;
        });
        btDecrementaCentavo.setOnClickListener(v -> {
            decrementaCentavo();
        });
        btDecrementaCentavo.setOnLongClickListener(v -> {
            runnable = () -> {
                if (!btDecrementaCentavo.isPressed()) return;
                decrementaCentavo();
                handler.postDelayed(runnable, DELAY);
            };
            handler.postDelayed(runnable, DELAY);
            return true;
        });
    }

    @Override
    public Double getStepData() {
        calculaValorMacoCigarro();
        return valorMacoCigarro;
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        return valorFormatter.format(getStepData());
    }

    @Override
    public void restoreStepData(Double data) {
        atualizaComOValor(data);
    }

    @Override
    protected IsDataValid isStepDataValid(Double stepData) {
        if (stepData == 0.0) {
            return new IsDataValid(false,
                    vwValorCigarro
                            .getResources()
                            .getString(R.string.str_valor_maco_cigarro_invalido)
            );
        }
        return new IsDataValid(true);
    }

    @Override
    protected View createStepContentLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        vwValorCigarro = inflater.inflate(R.layout.valor_layout, null, false);
        inicializaComponentes();
        configuraListenersDeValores();
        return vwValorCigarro;
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
