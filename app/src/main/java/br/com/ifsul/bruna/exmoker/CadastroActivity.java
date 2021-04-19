package br.com.ifsul.bruna.exmoker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CadastroActivity extends AppCompatActivity implements Validator.ValidationListener {

    private TextInputLayout txtNome;
    private TextInputLayout txtEmail;
    private TextInputLayout txtSenha;
    private TextInputLayout txtConfirmar_Senha;
    private TextInputLayout txtDataNasc;

    @NotEmpty(message = "Campo obrigatório!")
    @Length(min = 3, max = 60, message = "O nome deve ter no mínimo 3 caracteres!")
    private TextInputEditText etNome;

    @NotEmpty(message = "Campo obrigatório!")
    private TextInputEditText etEmail;

    @NotEmpty(message = "Campo obrigatório!")
    private TextInputEditText etDataNasc;

    @NotEmpty(message = "Campo obrigatório!")
    private TextInputEditText etSenha;

    @NotEmpty(message = "Campo obrigatório!")
    private TextInputEditText etConfirmarSenha;

    private Button btCadastrar;
    private Button btCadastrarGoogle;

    private DatePickerDialog datePicker;
    private Calendar dataSelecionada;
    private SimpleDateFormat formatadorDeData;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        this.inicializaComponentes();
        this.btCadastrar.setOnClickListener(view -> validator.validate());

        this.etDataNasc.setOnClickListener(v -> {
            datePicker = new DatePickerDialog(CadastroActivity.this, R.style.DialogTheme, (view, year, month, dayOfMonth) -> {
                etDataNasc.setText(formatadorDeData.format(dataSelecionada.getTime()));
                dataSelecionada.set(year, month, dayOfMonth);
            }, dataSelecionada.get(Calendar.YEAR), dataSelecionada.get(Calendar.MONTH), dataSelecionada.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });
    }


    private void inicializaComponentes() {
        dataSelecionada = Calendar.getInstance();
        formatadorDeData = new SimpleDateFormat("dd/MM/yyyy");
        txtNome = findViewById(R.id.cad_txt_nome);
        txtEmail = findViewById(R.id.cad_txt_email);
        txtDataNasc = findViewById(R.id.cad_txt_data_nasc);
        txtSenha = findViewById(R.id.cad_txt_senha);
        txtConfirmar_Senha = findViewById(R.id.cad_txt_senha_confirmar);
        etNome = findViewById(R.id.cad_et_nome);
        etEmail = findViewById(R.id.cad_et_email);
        etDataNasc = findViewById(R.id.cad_et_data_nasc);
        etSenha = findViewById(R.id.cad_et_senha);
        etConfirmarSenha = findViewById(R.id.cad_et_senha_confirmar);
        btCadastrar = findViewById(R.id.cad_bt_cadastrar);
        btCadastrarGoogle = findViewById(R.id.cad_bt_cadastrar_google);
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }
}