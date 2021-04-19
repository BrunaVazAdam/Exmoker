package br.com.ifsul.bruna.exmoker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {

    private TextInputLayout txtEmail;
    private TextInputLayout txtSenha;
    private TextInputEditText etEmail;
    private TextInputEditText etSenha;
    private Button btLogar;
    private Button btLogarGoogle;
    private Button btCadastrar;
    private Button btEsqueceuSenha;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        this.inicializaComponentes();
        this.btCadastrar.setOnClickListener(v -> {
            Intent itCadastrar = new Intent(LoginActivity.this, CadastroActivity.class);
            startActivity(itCadastrar);
        });

    }

    private void inicializaComponentes() {
        txtEmail = findViewById(R.id.login_txt_email);
        txtSenha = findViewById(R.id.cad_txt_senha);
        etEmail = findViewById(R.id.login_et_email);
        etSenha = findViewById(R.id.login_et_senha);
        btLogar = findViewById(R.id.login_bt_logar);
        btLogarGoogle = findViewById(R.id.login_bt_logar_google);
        btCadastrar = findViewById(R.id.login_bt_cadastrar);
        btEsqueceuSenha = findViewById(R.id.login_bt_esqueceu_senha);
        validator = new Validator(this);
        validator.setValidationListener(this);


    }

    @Override
    public void onValidationSucceeded() {


    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }
}