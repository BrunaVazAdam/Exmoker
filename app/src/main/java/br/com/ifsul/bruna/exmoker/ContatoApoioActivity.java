package br.com.ifsul.bruna.exmoker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class ContatoApoioActivity extends AppCompatActivity implements Validator.ValidationListener {

    private TextInputLayout txtNome;
    private TextInputLayout txtNumero;

    @NotEmpty(message = "Campo obrigatório!")
    @Length(min = 3, max = 60, message = "O nome deve ter no mínimo 3 caracteres!")
    private TextInputEditText etNome;

    @NotEmpty(message = "Campo obrigatório!")
    private TextInputEditText etNumero;

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }
}
