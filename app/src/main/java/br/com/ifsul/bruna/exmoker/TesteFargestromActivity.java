package br.com.ifsul.bruna.exmoker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

import br.com.ifsul.bruna.exmoker.colecoes.TesteFargestrom;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView;
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener;

public class TesteFargestromActivity extends AppCompatActivity implements StepperFormListener {

    private QuestaoFargestromStep primeiraQuestaoFargestromStep;
    private QuestaoFargestromStep segundaQuestaoFargestromStep;
    private QuestaoFargestromStep terceiraQuestaoFargestromStep;
    private QuestaoFargestromStep quartaQuestaoFargestromStep;
    private QuestaoFargestromStep quintaQuestaoFargestromStep;
    private QuestaoFargestromStep sextaQuestaoFargestromStep;

    private VerticalStepperFormView verticalStepperForm;
    private EstadoSingleton estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_fargestrom);
        estado = EstadoSingleton.getInstance();
        inicializaQuestoes();
        verticalStepperForm = findViewById(R.id.stepper_form);
        verticalStepperForm
                .setup(this,
                        primeiraQuestaoFargestromStep,
                        segundaQuestaoFargestromStep,
                        terceiraQuestaoFargestromStep,
                        quartaQuestaoFargestromStep,
                        quintaQuestaoFargestromStep,
                        sextaQuestaoFargestromStep)
                .displayBottomNavigation(false)
                .stepNextButtonText("Próxima")
                .includeConfirmationStep(false)
                .lastStepNextButtonText("Finalizar")
                .init();
    }

    public void inicializaQuestoes() {
        primeiraQuestaoFargestromStep =
                new QuestaoFargestromStep(getString(R.string.str_fargestrom_questao1),
                        Arrays.asList(
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao1_resposta1), 3),
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao1_resposta2), 2),
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao1_resposta3), 1),
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao1_resposta4), 0)
                        ));
        segundaQuestaoFargestromStep =
                new QuestaoFargestromStep(getString(R.string.str_fargestrom_questao2),
                        Arrays.asList(
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao2_resposta1), 1),
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao2_resposta2), 0)
                        ));
        terceiraQuestaoFargestromStep =
                new QuestaoFargestromStep(getString(R.string.str_fargestrom_questao3),
                        Arrays.asList(
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao3_resposta1), 1),
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao3_resposta2), 0)
                        ));
        quartaQuestaoFargestromStep =
                new QuestaoFargestromStep(getString(R.string.str_fargestrom_questao4),
                        Arrays.asList(
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao4_resposta1), 0),
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao4_resposta2), 1),
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao4_resposta3), 2),
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao4_resposta4), 3)
                        ));
        quintaQuestaoFargestromStep =
                new QuestaoFargestromStep(getString(R.string.str_fargestrom_questao5),
                        Arrays.asList(
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao5_resposta1), 1),
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao5_resposta2), 0)
                        ));
        sextaQuestaoFargestromStep =
                new QuestaoFargestromStep(getString(R.string.str_fargestrom_questao6),
                        Arrays.asList(
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao6_resposta1), 1),
                                new OpcaoQuestao(getString(R.string.str_fargestrom_questao6_resposta2), 0)
                        ));
    }

    @Override
    public void onCompletedForm() {
        TesteFargestrom testeFargestrom = new TesteFargestrom(
                primeiraQuestaoFargestromStep.getStepData(),
                segundaQuestaoFargestromStep.getStepData(),
                terceiraQuestaoFargestromStep.getStepData(),
                quartaQuestaoFargestromStep.getStepData(),
                quintaQuestaoFargestromStep.getStepData(),
                sextaQuestaoFargestromStep.getStepData()
        );

        estado.addTesteFargestrom(testeFargestrom);
        Intent itPreMain = new Intent(TesteFargestromActivity.this, PreMainActivity.class);
        startActivity(itPreMain);
        finish();
    }

    @Override
    public void onCancelledForm() {
    }
}