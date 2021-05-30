package br.com.ifsul.bruna.exmoker.steps;

import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.slider.Slider;

import br.com.ifsul.bruna.exmoker.R;
import ernestoyaquello.com.verticalstepperform.Step;

public class IntensidadeStep extends Step<Integer> {

    private View vwIntensidade;
    private Slider srIntensidade;
    private Integer intensidade;

    public IntensidadeStep(String title) {
        super(title);
    }

    private String getDescricaoIntensidade(Integer valorIntensidade) {
        switch (valorIntensidade) {
            case 1:
                return vwIntensidade.getResources().getString(R.string.str_muito_baixa);
            case 2:
                return vwIntensidade.getResources().getString(R.string.str_baixa);
            case 3:
                return vwIntensidade.getResources().getString(R.string.str_media);
            case 4:
                return vwIntensidade.getResources().getString(R.string.str_alta);
            case 5:
                return vwIntensidade.getResources().getString(R.string.str_muito_alta);
            default:
                return "Intensidade";
        }
    }

    private void sliderListeners() {
        srIntensidade.addOnChangeListener((slider, value, fromUser) -> {
            intensidade = (int) value;
        });
        srIntensidade.setLabelFormatter(value -> getDescricaoIntensidade((int) value));
    }

    @Override
    public Integer getStepData() {
        return intensidade;
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        return getDescricaoIntensidade(getStepData());
    }

    @Override
    public void restoreStepData(Integer data) {
        intensidade = data;
        srIntensidade.setValue(intensidade);
    }

    @Override
    protected IsDataValid isStepDataValid(Integer stepData) {
        return null;
    }

    @Override
    protected View createStepContentLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        vwIntensidade = inflater.inflate(R.layout.intensidade_layout, null, false);
        srIntensidade = vwIntensidade.findViewById(R.id.sr_intensidade);
        intensidade = 1;
        sliderListeners();

        return vwIntensidade;
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
