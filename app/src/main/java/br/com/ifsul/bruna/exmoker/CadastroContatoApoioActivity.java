package br.com.ifsul.bruna.exmoker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import br.com.ifsul.bruna.exmoker.colecoes.ContatoDeApoio;

public class CadastroContatoApoioActivity extends AppCompatActivity implements Validator.ValidationListener {
    private TextInputLayout txtNome;
    private TextInputLayout txtTelefone;

    @NotEmpty(message = "Campo obrigatório!")
    @Length(min = 3, max = 60, message = "O nome deve ter no mínimo 3 caracteres!")
    private TextInputEditText etNome;

    @NotEmpty(message = "Campo obrigatório!")
    @Length(min = 13, max = 13, message = "O telefone deve ter 13 caracteres!")
    private TextInputEditText etTelefone;

    private Button btCadastrar;
    private Validator validator;
    private EstadoSingleton estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contato_apoio);
        inicializaComponentes();

        this.btCadastrar.setOnClickListener(view -> {
            limpaErros();
            validator.validate();
        });
    }

    private void inicializaComponentes() {
        estado = EstadoSingleton.getInstance();
        txtNome = findViewById(R.id.cad_ca_txt_nome);
        etNome = findViewById(R.id.cad_ca_et_nome);
        txtTelefone = findViewById(R.id.cad_ca_telefone);
        etTelefone = findViewById(R.id.cad_ca_et_telefone);
        btCadastrar = findViewById(R.id.cad_ca_bt_cadastrar);

        validator = new Validator(this);
        validator.setValidationListener(this);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNNNNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(etTelefone, smf);
        etTelefone.addTextChangedListener(mtw);
    }

    @Override
    public void onValidationSucceeded() {
        btCadastrar.setEnabled(false);
        ContatoDeApoio contatoDeApoio = new ContatoDeApoio(
                etNome.getText().toString(),
                etTelefone.getText().toString());
        estado.setContatoDeApoio(contatoDeApoio);
        Intent itPreMain = new Intent(CadastroContatoApoioActivity.this, PreMainActivity.class);
        startActivity(itPreMain);
        finish();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError erro : errors) {
            View componente = erro.getView();
            String msgErro = erro.getCollatedErrorMessage(this);
            if (componente instanceof TextInputEditText) {
                switch (componente.getId()) {
                    case R.id.cad_ca_et_nome:
                        txtNome.setError(msgErro);
                        break;
                    case R.id.cad_ca_et_telefone:
                        txtTelefone.setError(msgErro);
                        break;
                }
            }
        }
    }

    public void limpaErros() {
        txtNome.setError(null);
        txtTelefone.setError(null);
    }

}