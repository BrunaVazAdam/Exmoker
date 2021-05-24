package br.com.ifsul.bruna.exmoker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MetodoParadaActivity extends AppCompatActivity {

    private Boolean paradaGradual;
    private RadioButton rbImediata;
    private RadioButton rbGradual;
    private TextView tvGradual;
    private LinearLayout llImediata;
    private LinearLayout llGradual;
    private Button btConfirmar;

    private DatePickerDialog datePickerDialog;
    private DatePicker datePicker;
    private Calendar dataSelecionada;
    private SimpleDateFormat formatadorDeData;

    private EstadoSingleton estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_parada);
        inicializaComponentes();
        llImediata.setOnClickListener(v -> {
            rbImediata.setChecked(true);
            rbGradual.setChecked(false);
            paradaGradual = false;
        });
        llGradual.setOnClickListener(v -> {
            rbImediata.setChecked(false);
            rbGradual.setChecked(true);
            paradaGradual = true;
            datePickerDialog = new DatePickerDialog(MetodoParadaActivity.this, R.style.DialogTheme, (view, year, month, dayOfMonth) -> {
                dataSelecionada.set(year, month, dayOfMonth);
                tvGradual.setText(getString(R.string.str_parada_gradual_descricao) + " "
                        + getString(R.string.str_parada_gradual_descricao_em) + " "
                        + formatadorDeData.format(dataSelecionada.getTime()) + ".");
            }, dataSelecionada.get(Calendar.YEAR), dataSelecionada.get(Calendar.MONTH), dataSelecionada.get(Calendar.DAY_OF_MONTH));
            datePicker = datePickerDialog.getDatePicker();
            datePicker.setMinDate(new Date().getTime());
            Calendar max = Calendar.getInstance();
            max.add(Calendar.DAY_OF_MONTH, 30);
            datePicker.setMaxDate(max.getTimeInMillis());
            datePickerDialog.show();
        });

        btConfirmar.setOnClickListener(v -> {
            Date dataParada = null;
            if (paradaGradual) dataParada = dataSelecionada.getTime();
            estado.setMetodoParada(paradaGradual, dataParada);
            Intent itPreMain = new Intent(MetodoParadaActivity.this, PreMainActivity.class);
            startActivity(itPreMain);
            finish();
        });
    }

    private void inicializaComponentes() {
        paradaGradual = false;
        estado = EstadoSingleton.getInstance();
        rbImediata = findViewById(R.id.mp_rb_imediata);
        rbGradual = findViewById(R.id.mp_rb_gradual);
        tvGradual = findViewById(R.id.mp_tv_gradual);
        tvGradual.setText(getString(R.string.str_parada_gradual_descricao) + ".");
        llImediata = findViewById(R.id.mp_ll_imediata);
        llGradual = findViewById(R.id.mp_ll_gradual);
        btConfirmar = findViewById(R.id.mp_bt_confirmar);
        dataSelecionada = Calendar.getInstance();
        formatadorDeData = new SimpleDateFormat("dd/MM/yyyy");
    }
}