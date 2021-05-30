package br.com.ifsul.bruna.exmoker.steps;

import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsul.bruna.exmoker.R;
import ernestoyaquello.com.verticalstepperform.Step;

public class GatilhosStep extends Step<List<String>> {

    private View vwGatilhos;
    private ChipGroup cpGroup;
    private List<String> gatilhos;


    public GatilhosStep(String title) {
        super(title);
    }

    private void configuraListeners() {
        for (int i = 0; i < cpGroup.getChildCount(); i++) {
            Chip currentChip = (Chip) cpGroup.getChildAt(i);

            currentChip.setOnCheckedChangeListener((view, isChecked) -> {
                markAsCompletedOrUncompleted(true);
                if (isChecked) {
                    gatilhos.add(textoDoId(view.getId()));
                } else {
                    gatilhos.remove(textoDoId(view.getId()));
                }
            });
        }
    }

    private void inicializaComponentes() {
        gatilhos = new ArrayList<>();
        cpGroup = vwGatilhos.findViewById(R.id.gs_cg_gatilhos);
    }

    @Override
    public List<String> getStepData() {
        return gatilhos;
    }

    private String textoDoId(Integer idCp) {
        switch (idCp) {
            case R.id.gs_cp_nervosismo:
                return vwGatilhos.getResources().getString(R.string.str_nervosismo);
            case R.id.gs_cp_convivio_fumantes:
                return vwGatilhos.getResources().getString(R.string.str_convivio_fumantes);
            case R.id.gs_cp_aumento_alimentacao:
                return vwGatilhos.getResources().getString(R.string.str_aumento_alimentacao);
            case R.id.gs_cp_sintomas_abstinencia:
                return vwGatilhos.getResources().getString(R.string.str_sintomas_abstinencia);
            case R.id.gs_cp_outro:
                return vwGatilhos.getResources().getString(R.string.str_outro);
            default:
                return "Outro";
        }
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        String descricao = "";
        for (int i = 0; i < gatilhos.size(); i++) {
            descricao = descricao + gatilhos.get(i) +
                    (i == gatilhos.size() - 1 ?
                            "." : ", ");
        }
        return descricao;
    }

    @Override
    public void restoreStepData(List<String> data) {

    }

    @Override
    protected IsDataValid isStepDataValid(List<String> stepData) {
        if (cpGroup.getCheckedChipIds().size() == 0) {
            return new IsDataValid(false,
                    cpGroup.getResources().getString(R.string.str_gatilhos_maior_zero));
        }
        return new IsDataValid(true);
    }

    @Override
    protected View createStepContentLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        vwGatilhos = inflater.inflate(R.layout.gatilhos_layout, null, false);
        inicializaComponentes();
        configuraListeners();
        return vwGatilhos;
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
