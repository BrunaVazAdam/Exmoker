package br.com.ifsul.bruna.exmoker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.com.ifsul.bruna.exmoker.colecoes.InformacoesAdicionais;
import br.com.ifsul.bruna.exmoker.steps.MesAnoStep;
import br.com.ifsul.bruna.exmoker.steps.QuantidadeStep;
import br.com.ifsul.bruna.exmoker.steps.ValorCigarroStep;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView;
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener;

public class InformacoesAdicionaisActivity extends AppCompatActivity implements StepperFormListener {

    private ValorCigarroStep valorCigarroStep;
    private QuantidadeStep cigarrosNoMacoStep;
    private MesAnoStep mesAnoInicioTabagismoStep;
    private VerticalStepperFormView verticalStepperForm;

    private EstadoSingleton estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_adicionais);
        inicializaComponentes();
        verticalStepperForm = findViewById(R.id.stepper_form_informacoes_adicionais);
        verticalStepperForm
                .setup(this,
                        valorCigarroStep,
                        cigarrosNoMacoStep,
                        mesAnoInicioTabagismoStep)
                .displayBottomNavigation(false)
                .stepNextButtonText(getString(R.string.str_proxima_questao))
                .includeConfirmationStep(false)
                .lastStepNextButtonText(getString(R.string.str_ver_resultado_teste))
                .init();
    }

    private void inicializaComponentes() {
        estado = EstadoSingleton.getInstance();
        valorCigarroStep = new ValorCigarroStep(getString(R.string.str_valor_maco_cigarro));
        cigarrosNoMacoStep = new QuantidadeStep(getString(R.string.str_qtd_cigarros_no_maco),
                getString(R.string.str_cigarros_no_maco));
        mesAnoInicioTabagismoStep = new MesAnoStep(getString(R.string.str_data_inicio_tabagismo), this);
    }


    @Override
    public void onCompletedForm() {
        estado.setInformacoesAdicionais(new InformacoesAdicionais(
                valorCigarroStep.getStepData(),
                cigarrosNoMacoStep.getStepData(),
                mesAnoInicioTabagismoStep.getStepData().getTime()
        ));
        Intent itResultadoInformacoes = new Intent(InformacoesAdicionaisActivity.this, ResultadoInformacoesActivity.class);
        startActivity(itResultadoInformacoes);
        finish();
    }

    @Override
    public void onCancelledForm() {

    }
}