package br.com.ifsul.bruna.exmoker.steps;

import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.chip.ChipGroup;

import br.com.ifsul.bruna.exmoker.R;
import ernestoyaquello.com.verticalstepperform.Step;

public class LocalStep extends Step<String> {

    private View vwLocal;
    private ChipGroup cpGroup;
    private String local;


    public LocalStep(String title) {
        super(title);
    }

    private void configuraListeners() {
        cpGroup.setOnCheckedChangeListener((group, checkedId) -> {
            local = textoDoId(checkedId);
            markAsCompletedOrUncompleted(true);
        });
    }

    private void inicializaComponentes() {
        local = "";
        cpGroup = vwLocal.findViewById(R.id.lc_cg_locais);
    }

    @Override
    public String getStepData() {
        return local;
    }

    private String textoDoId(Integer idCp) {
        switch (idCp) {
            case R.id.lc_cp_escola:
                return vwLocal.getResources().getString(R.string.str_escola);
            case R.id.lc_cp_trabalho:
                return vwLocal.getResources().getString(R.string.str_trabalho);
            case R.id.lc_cp_casa:
                return vwLocal.getResources().getString(R.string.str_casa);
            case R.id.lc_cp_festa:
                return vwLocal.getResources().getString(R.string.str_festa);
            case R.id.lc_cp_outro:
                return vwLocal.getResources().getString(R.string.str_outro);
            default:
                return "Outro";
        }
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        return local;
    }

    @Override
    public void restoreStepData(String data) {

    }

    @Override
    protected IsDataValid isStepDataValid(String stepData) {
        if (stepData == "") {
            return new IsDataValid(false,
                    cpGroup.getResources().getString(R.string.str_local_selecionar_um));
        }
        return new IsDataValid(true);
    }

    @Override
    protected View createStepContentLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        vwLocal = inflater.inflate(R.layout.local_layout, null, false);
        inicializaComponentes();
        configuraListeners();
        return vwLocal;
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
