package br.com.ifsul.bruna.exmoker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

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
    @Email(message = "Deve ser um email válido!")
    private TextInputEditText etEmail;

    @NotEmpty(message = "Campo obrigatório!")
    private TextInputEditText etDataNasc;

    @NotEmpty(message = "Campo obrigatório!")
    @Password(message = "Senha deve ter no minímo 6 caracteres minúsculos e maiúsculos. ", scheme = Password.Scheme.ALPHA_MIXED_CASE)
    private TextInputEditText etSenha;

    @NotEmpty(message = "Campo obrigatório!")
    @ConfirmPassword(message = "As senhas não são iguais.")
    private TextInputEditText etConfirmarSenha;

    private Button btCadastrar;

    private DatePickerDialog datePicker;
    private Calendar dataSelecionada;
    private SimpleDateFormat formatadorDeData;

    private FirebaseAuth mAuth;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        this.inicializaComponentes();
        this.btCadastrar.setOnClickListener(view -> {
            limpaErros();
            validator.validate();
        });

        this.etDataNasc.setOnClickListener(v -> {
            datePicker = new DatePickerDialog(CadastroActivity.this, R.style.DialogTheme, (view, year, month, dayOfMonth) -> {
                etDataNasc.setText(formatadorDeData.format(dataSelecionada.getTime()));
                dataSelecionada.set(year, month, dayOfMonth);
            }, dataSelecionada.get(Calendar.YEAR), dataSelecionada.get(Calendar.MONTH), dataSelecionada.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });
    }


    private void inicializaComponentes() {
        mAuth = FirebaseAuth.getInstance();
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

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void onValidationSucceeded() {
        String password = etSenha.getText().toString();
        String email = etEmail.getText().toString();
        String name = etNome.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(CadastroActivity.this,
                                "Usuário cadastrado!",
                                Toast.LENGTH_LONG)
                                .show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        UserProfileChangeRequest profileChange = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build();
                        user.updateProfile(profileChange);
                        Intent itLogin = new Intent(CadastroActivity.this, LoginActivity.class);
                        startActivity(itLogin);
                        finish();
                    } else {
                        Toast.makeText(CadastroActivity.this,
                                "Usuário não foi cadastrado!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }

    public void limpaErros() {
        txtNome.setError(null);
        txtEmail.setError(null);
        txtDataNasc.setError(null);
        txtSenha.setError(null);
        txtConfirmar_Senha.setError(null);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError erro : errors) {
            View componente = erro.getView();
            String msgErro = erro.getCollatedErrorMessage(this);
            if (componente instanceof TextInputEditText) {
                switch (componente.getId()) {
                    case R.id.cad_et_nome:
                        txtNome.setError(msgErro);
                        break;
                    case R.id.cad_et_email:
                        txtEmail.setError(msgErro);
                        break;
                    case R.id.cad_et_data_nasc:
                        txtDataNasc.setError(msgErro);
                        break;
                    case R.id.cad_et_senha:
                        txtSenha.setError(msgErro);
                        break;
                    case R.id.cad_et_senha_confirmar:
                        txtConfirmar_Senha.setError(msgErro);
                        break;
                }
            }
        }
    }
}