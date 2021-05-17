package br.com.ifsul.bruna.exmoker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    private TextInputLayout txtEmail;
    private TextInputLayout txtSenha;

    @Email(message = "Deve ser um email válido.")
    @NotEmpty(message = "Campo obrigatório!")
    private TextInputEditText etEmail;

    @NotEmpty(message = "Campo obrigatório!")
    private TextInputEditText etSenha;

    private Button btLogar;
    private Button btLogarGoogle;
    private Button btCadastrar;
    private Button btEsqueceuSenha;

    private FirebaseAuth mAuth;
    private EstadoSingleton estado;
    private GoogleSignInClient mGoogleSignInClient;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.inicializaComponentes();


        this.btCadastrar.setOnClickListener(v -> {
            Intent itCadastrar = new Intent(LoginActivity.this, CadastroActivity.class);
            startActivity(itCadastrar);
        });

        this.btLogar.setOnClickListener(v -> {
            limpaErros();
            validator.validate();
        });

        this.btLogarGoogle.setOnClickListener(v -> logarComGoogle());

    }

    private void inicializaComponentes() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // TODO: Gerenciar manter login google
        mGoogleSignInClient.signOut();
        estado = EstadoSingleton.getInstance();
        mAuth = estado.getAuthInstance();
        txtEmail = findViewById(R.id.login_txt_email);
        txtSenha = findViewById(R.id.login_txt_senha);
        etEmail = findViewById(R.id.login_et_email);
        etSenha = findViewById(R.id.login_et_senha);
        btLogar = findViewById(R.id.login_bt_logar);
        btLogarGoogle = findViewById(R.id.login_bt_logar_google);
        btCadastrar = findViewById(R.id.login_bt_cadastrar);
        btEsqueceuSenha = findViewById(R.id.login_bt_esqueceu_senha);
        validator = new Validator(this);
        validator.setValidationListener(this);


    }

    private void logarComGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        loginComSucesso();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Não foi possível logar!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    @Override
    public void onValidationSucceeded() {
        btLogar.setEnabled(false);
        String password = etSenha.getText().toString();
        String email = etEmail.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        loginComSucesso();
                    } else {
                        btLogar.setEnabled(true);
                        Toast.makeText(LoginActivity.this,
                                "Email ou senha inválido!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }

    private void loginComSucesso() {
        Intent itLogin = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(itLogin);
    }

    public void limpaErros() {
        txtEmail.setError(null);
        txtSenha.setError(null);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError erro : errors) {
            View componente = erro.getView();
            String msgErro = erro.getCollatedErrorMessage(this);
            if (componente instanceof TextInputEditText) {
                switch (componente.getId()) {
                    case R.id.login_et_email:
                        txtEmail.setError(msgErro);
                        break;
                    case R.id.login_et_senha:
                        txtSenha.setError(msgErro);
                        break;
                }
            }
        }
    }
}