package br.com.ifsul.bruna.exmoker;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ernestoyaquello.com.verticalstepperform.Step;

public class QuantidadeStep extends Step<Integer> {

    private static int DELAY = 50;
    private Handler handler;
    private Runnable runnable;
    private TextView tvQuantidadeCounter;
    private Button btIncrementa;
    private Button btDecrementa;
    private View vwQuantidadeCounter;
    private Integer quantidade;
    private String complementoDescricaoLegivel;

    protected QuantidadeStep(String title) {
        super(title);
    }

    protected QuantidadeStep(String title, String descricao) {
        super(title);
        complementoDescricaoLegivel = descricao;
    }

    public Integer getValorQuestao() {
        if (quantidade <= 10) {
            return 0;
        } else if (quantidade >= 11 && quantidade <= 20) {
            return 1;
        } else if (quantidade >= 21 && quantidade <= 30) {
            return 2;
        } else if (quantidade >= 31) {
            return 3;
        }
        return 0;
    }

    private void inicializaComponentes() {
        handler = new Handler();
        quantidade = 0;
        tvQuantidadeCounter = vwQuantidadeCounter.findViewById(R.id.ct_tv_quantidade);
        btIncrementa = vwQuantidadeCounter.findViewById(R.id.ct_bt_incrementa);
        btDecrementa = vwQuantidadeCounter.findViewById(R.id.ct_bt_decrementa);
    }

    private void incrementa() {
        quantidade++;
        if (quantidade == 100) quantidade = 0;
        markAsCompletedOrUncompleted(true);
        tvQuantidadeCounter.setText(String.format("%02d", quantidade));
    }

    private void decrementa() {
        quantidade--;
        if (quantidade == -1) quantidade = 99;
        markAsCompletedOrUncompleted(true);
        tvQuantidadeCounter.setText(String.format("%02d", quantidade));
    }

    private void configuraListeners() {
        btIncrementa.setOnClickListener(v -> {
            incrementa();
        });
        btIncrementa.setOnLongClickListener(v -> {
            runnable = () -> {
                if (!btIncrementa.isPressed()) return;
                incrementa();
                handler.postDelayed(runnable, DELAY);
            };
            handler.postDelayed(runnable, DELAY);
            return true;
        });
        btDecrementa.setOnClickListener(v -> {
            decrementa();
        });
        btDecrementa.setOnLongClickListener(v -> {
            runnable = () -> {
                if (!btDecrementa.isPressed()) return;
                decrementa();
                handler.postDelayed(runnable, DELAY);
            };
            handler.postDelayed(runnable, DELAY);
            return true;
        });
    }

    @Override
    public Integer getStepData() {
        return quantidade;
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        return quantidade + " " + complementoDescricaoLegivel;
    }

    @Override
    public void restoreStepData(Integer data) {
        quantidade = data;
        tvQuantidadeCounter.setText(String.format("%02d", quantidade));
    }

    @Override
    protected IsDataValid isStepDataValid(Integer stepData) {
        if (stepData == 0) {
            return new IsDataValid(false,
                    vwQuantidadeCounter
                            .getResources()
                            .getString(R.string.str_qtd_cigarros_no_maco_invalido)
            );
        }
        return new IsDataValid(true);
    }

    @Override
    protected View createStepContentLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        vwQuantidadeCounter = inflater.inflate(R.layout.counter_layout, null, false);
        inicializaComponentes();
        configuraListeners();
        return vwQuantidadeCounter;
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
