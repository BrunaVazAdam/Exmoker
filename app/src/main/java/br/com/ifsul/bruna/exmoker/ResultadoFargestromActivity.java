package br.com.ifsul.bruna.exmoker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultadoFargestromActivity extends AppCompatActivity {

    private TextView tvTitleDependencia;
    private TextView tvSubtitleDependencia;
    private TextView tvSubtitleNegrito;

    private Button btAvancar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        Integer resultado = i.getIntExtra("resultado", 5);
        defineTemaNivelDependencia(resultado); //Executo antes do onCreate para o setTheme ter efeito

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_fargestrom);
        inicializaComponentes();
        defineTextoNivelDependencia(resultado);

        btAvancar.setOnClickListener(v -> {
            Intent itPreMain = new Intent(ResultadoFargestromActivity.this, PreMainActivity.class);
            startActivity(itPreMain);
            finish();
        });
    }

    private void inicializaComponentes() {
        tvTitleDependencia = findViewById(R.id.tv_rf_title_dependencia);
        tvSubtitleDependencia = findViewById(R.id.tv_rf_subtitle_dependencia);
        tvSubtitleNegrito = findViewById(R.id.tv_rf_subtitle_negrito);
        btAvancar = findViewById(R.id.rf_bt_avancar);
    }

    private void defineTemaNivelDependencia(Integer resultado) {
        if (resultado >= 3 && resultado <= 4) {
            setTheme(R.style.Theme_Exmoker_DependenciaBaixa);
        } else if (resultado == 5) {
            setTheme(R.style.Theme_Exmoker_DependenciaMedia);
        } else if (resultado >= 6 && resultado <= 7) {
            setTheme(R.style.Theme_Exmoker_DependenciaElevada);
        } else if (resultado >= 8 && resultado <= 10) {
            setTheme(R.style.Theme_Exmoker_DependenciaMuitoElevada);
        }
    }

    private void defineTextoNivelDependencia(Integer resultado) {
        String title = "";
        String subtitle = "";
        String subtitleNegrito = "";
        if (resultado >= 0 && resultado <= 2) {
            title = getString(R.string.str_nivel_dependencia_muito_baixa);
            subtitle = getString(R.string.str_subtitle_baixa_dependencia);
        } else if (resultado >= 3 && resultado <= 4) {
            title = getString(R.string.str_nivel_dependencia_baixa);
            subtitle = getString(R.string.str_subtitle_baixa_dependencia);
        } else if (resultado == 5) {
            title = getString(R.string.str_nivel_dependencia_media);
            subtitle = getString(R.string.str_subtitle_media_dependencia);
        } else if (resultado >= 6 && resultado <= 7) {
            title = getString(R.string.str_nivel_dependencia_elevada);
            subtitle = getString(R.string.str_subtitle_alta_dependencia);
            subtitleNegrito = getString(R.string.str_recomendacao);
        } else if (resultado >= 8 && resultado <= 10) {
            title = getString(R.string.str_nivel_dependencia_muito_elevada);
            subtitle = getString(R.string.str_subtitle_alta_dependencia);
            subtitleNegrito = getString(R.string.str_recomendacao);
        }

        tvTitleDependencia.setText(title);
        tvSubtitleDependencia.setText(subtitle);
        tvSubtitleNegrito.setText(subtitleNegrito);
    }
}