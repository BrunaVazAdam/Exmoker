package br.com.ifsul.bruna.exmoker;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class CadastroContatoApoioActivity extends AppCompatActivity implements Validator.ValidationListener {
    private TextInputLayout txtNome;
    private TextInputLayout txtNumero;

    @NotEmpty(message = "Campo obrigatório!")
    @Length(min = 3, max = 60, message = "O nome deve ter no mínimo 3 caracteres!")
    private TextInputEditText etNome;

    @NotEmpty(message = "Campo obrigatório!")
    @Length(min = 13, max = 13, message = "O telefone deve ter 13 caracteres!")
    private TextInputEditText etTelefone;

    private Button btCadastrar;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contato_apoio);
    }

    private void inicializaComponentes() {

    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }

}