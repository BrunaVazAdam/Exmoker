package br.com.ifsul.bruna.exmoker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class EsqueceuSenhaActivity extends AppCompatActivity implements Validator.ValidationListener {

    private TextInputLayout txtEmail;

    @NotEmpty(message = "Campo obrigatório!")
    @Email(message = "Deve ser um email válido!")
    private TextInputEditText etEmail;

    private Button btRedefinirSenha;

    private FirebaseAuth auth;
    private EstadoSingleton estado;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_senha);
        inicializaComponentes();

        btRedefinirSenha.setOnClickListener(v -> {
            limpaErros();
            validator.validate();
        });
    }

    private void inicializaComponentes() {
        estado = EstadoSingleton.getInstance();
        auth = estado.getAuthInstance();
        txtEmail = findViewById(R.id.txt_email_esqueceu_senha);
        etEmail = findViewById(R.id.et_email_esqueceu_senha);
        btRedefinirSenha = findViewById(R.id.bt_redefinir_senha);

        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    @Override
    public void onValidationSucceeded() {
        btRedefinirSenha.setEnabled(false);
        String email = etEmail.getText().toString();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(EsqueceuSenhaActivity.this,
                                getString(R.string.str_enviouEmail),
                                Toast.LENGTH_LONG)
                                .show();
                        finish();
                    } else {
                        btRedefinirSenha.setEnabled(true);
                        Toast.makeText(EsqueceuSenhaActivity.this,
                                getString(R.string.str_naoEnviouEmail),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError erro : errors) {
            View componente = erro.getView();
            String msgErro = erro.getCollatedErrorMessage(this);
            if (componente instanceof TextInputEditText) {
                switch (componente.getId()) {
                    case R.id.et_email_esqueceu_senha:
                        txtEmail.setError(msgErro);
                        break;
                }
            }
        }
    }

    public void limpaErros() {
        txtEmail.setError(null);
    }
}