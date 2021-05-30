package br.com.ifsul.bruna.exmoker.steps;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import br.com.ifsul.bruna.exmoker.R;
import ernestoyaquello.com.verticalstepperform.Step;

public class RecaidaStep extends Step<Integer> {

    private static int DELAY = 50;
    private Handler handler;
    private Runnable runnable;
    private TextView tvQuantidadeCounter;
    private Boolean teveRecaida;
    private RadioGroup rgSimNao;
    private RadioButton rbSim;
    private RadioButton rbNao;
    private Button btIncrementa;
    private Button btDecrementa;
    private View vwRecaida;
    private LinearLayout llCounter;
    private TextView tvPerguntaComp;

    private Integer quantidade;
    private String complementoDescricaoLegivel;

    public RecaidaStep(String title) {
        super(title);
    }

    public RecaidaStep(String title, String descricao) {
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
        teveRecaida = false;
        tvQuantidadeCounter = vwRecaida.findViewById(R.id.ra_tv_quantidade);
        rgSimNao = vwRecaida.findViewById(R.id.ra_rg_sim_nao);
        rbSim = vwRecaida.findViewById(R.id.ra_rb_sim);
        rbNao = vwRecaida.findViewById(R.id.ra_rb_nao);
        btIncrementa = vwRecaida.findViewById(R.id.ra_bt_incrementa);
        btDecrementa = vwRecaida.findViewById(R.id.ra_bt_decrementa);
        llCounter = vwRecaida.findViewById(R.id.ra_ll_counter);
        tvPerguntaComp = vwRecaida.findViewById(R.id.ra_tv_pergunta_comp);
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
        rgSimNao.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == rbSim.getId()) {
                teveRecaida = true;
                llCounter.setVisibility(View.VISIBLE);
                tvPerguntaComp.setVisibility(View.VISIBLE);
            } else {
                teveRecaida = false;
                quantidade = 0;
                tvQuantidadeCounter.setText(String.format("%02d", quantidade));
                llCounter.setVisibility(View.GONE);
                tvPerguntaComp.setVisibility(View.GONE);
            }
            markAsCompletedOrUncompleted(true);
        });
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
        if (teveRecaida) {
            return quantidade;
        } else {
            return 0;
        }
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        if (teveRecaida) {
            return vwRecaida.getResources().getString(R.string.str_sim)
                    + ", "
                    + quantidade
                    + " "
                    + complementoDescricaoLegivel;
        } else {
            return vwRecaida.getResources().getString(R.string.str_nao);
        }
    }

    @Override
    public void restoreStepData(Integer data) {
        quantidade = data;
        tvQuantidadeCounter.setText(String.format("%02d", quantidade));
    }

    @Override
    protected IsDataValid isStepDataValid(Integer stepData) {
        if (stepData == 0 && teveRecaida) {
            return new IsDataValid(false,
                    vwRecaida
                            .getResources()
                            .getString(R.string.str_qtd_cigarros_no_maco_invalido)
            );
        }
        return new IsDataValid(true);
    }

    @Override
    protected View createStepContentLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        vwRecaida = inflater.inflate(R.layout.recaida_layout, null, false);
        inicializaComponentes();
        configuraListeners();
        return vwRecaida;
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
