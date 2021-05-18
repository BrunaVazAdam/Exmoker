package br.com.ifsul.bruna.exmoker;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import ernestoyaquello.com.verticalstepperform.Step;

public class QuestaoFargestromStep extends Step<Integer> {
    private RadioGroup radioGroup;
    private List<RadioButton> radioButtons;
    private List<OpcaoQuestao> opcoes;

    protected QuestaoFargestromStep(String title, List<OpcaoQuestao> opcoes) {
        super(title);
        this.opcoes = opcoes;
        this.radioButtons = new ArrayList<>();
    }

    @Override
    public Integer getStepData() {
        Integer data = 0;
        int id = radioGroup.getCheckedRadioButtonId();
        for (int i = 0; i < radioButtons.size(); i++) {
            if (radioButtons.get(i).getId() == id) {
                return opcoes.get(i).getValor();
            }
        }
        return data;
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        String resposta = "";
        int id = radioGroup.getCheckedRadioButtonId();
        for (int i = 0; i < radioButtons.size(); i++) {
            if (radioButtons.get(i).getId() == id) {
                return opcoes.get(i).getResposta();
            }
        }
        return resposta;
    }

    @Override
    public void restoreStepData(Integer data) {
        for (int i = 0; i < radioButtons.size(); i++) {
            if (opcoes.get(i).getValor() == data) {
                radioButtons.get(i).setChecked(true);
            }
        }
    }

    @Override
    protected IsDataValid isStepDataValid(Integer stepData) {
        return new IsDataValid(true);
    }

    @Override
    protected View createStepContentLayout() {
        radioGroup = new RadioGroup(getContext());
        Log.d("DEBUG", opcoes.get(0).getResposta() + "teste");
        for (int i = 0; i < opcoes.size(); i++) {
            OpcaoQuestao opcao = opcoes.get(i);
            RadioButton radioButton = new RadioButton(getContext());
            if (i == 0) radioButton.setChecked(true);
            radioButton.setText(opcao.getResposta());
            radioButton.setButtonTintList(new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_checked},
                            new int[]{android.R.attr.state_checked}
                    },
                    new int[]{
                            Color.WHITE,
                            Color.WHITE
                    }
            ));
            radioButton.setTextColor(Color.WHITE);
            radioButton.setId(View.generateViewId());
            radioGroup.addView(radioButton);
            radioButtons.add(radioButton);
        }
        return radioGroup;
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
