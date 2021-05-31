package br.com.ifsul.bruna.exmoker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import br.com.ifsul.bruna.exmoker.colecoes.EventoDeAjuda;
import br.com.ifsul.bruna.exmoker.steps.GatilhosStep;
import br.com.ifsul.bruna.exmoker.steps.IntensidadeStep;
import br.com.ifsul.bruna.exmoker.steps.LocalStep;
import br.com.ifsul.bruna.exmoker.steps.RecaidaStep;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView;
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener;

public class QuestionarioGatilhosActivity extends AppCompatActivity implements StepperFormListener {

    private RecaidaStep recaidaStep;
    private IntensidadeStep intensidadeStep;
    private GatilhosStep gatilhosStep;
    private LocalStep localStep;

    private VerticalStepperFormView verticalStepperForm;
    private EstadoSingleton estado;
    private FirebaseAuth auth;
    private String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario_gatilhos);
        estado = EstadoSingleton.getInstance();
        auth = FirebaseAuth.getInstance();
        nome = estado.getPrimeiroNome(auth.getCurrentUser().getDisplayName());
        inicializaQuestoes();
        verticalStepperForm = findViewById(R.id.stepper_form_questionario_gatilhos);
        verticalStepperForm
                .setup(this,
                        recaidaStep,
                        intensidadeStep,
                        gatilhosStep,
                        localStep)
                .displayBottomNavigation(false)
                .stepNextButtonText(getString(R.string.str_proxima_questao))
                .includeConfirmationStep(false)
                .lastStepNextButtonText(getString(R.string.str_finalizar))
                .init();
    }

    private void inicializaQuestoes() {
        recaidaStep = new RecaidaStep(
                nome + getString(R.string.str_questionario_gatilho_questao1),
                getString(R.string.str_cigarros)
        );
        intensidadeStep = new IntensidadeStep(getString(R.string.str_pergunta_intensidade));
        gatilhosStep = new GatilhosStep(getString(R.string.str_gatilhos_questao));
        localStep = new LocalStep(getString(R.string.str_local_gatilho));
    }


    @Override
    public void onCompletedForm() {
        EventoDeAjuda eventoDeAjuda = new EventoDeAjuda(
                recaidaStep.getStepData() > 0,
                recaidaStep.getStepData(),
                intensidadeStep.getStepData(),
                gatilhosStep.getStepData(),
                localStep.getStepData()
        );
        estado.addEventoDeAjuda(eventoDeAjuda);
        Intent itMain = new Intent(QuestionarioGatilhosActivity.this, MainActivity.class);
        startActivity(itMain);
        finish();

    }

    @Override
    public void onCancelledForm() {

    }
}