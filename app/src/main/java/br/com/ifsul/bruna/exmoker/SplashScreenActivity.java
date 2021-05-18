package br.com.ifsul.bruna.exmoker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EstadoSingleton estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        estado = EstadoSingleton.getInstance();
        mAuth = estado.getAuthInstance();
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user == null) {
                    abreLogin();
                } else {
                    abrePreMain();
                }
            }
        }, 2000);


    }

    private void abreLogin() {
        Intent itLogin = new Intent(SplashScreenActivity.this, LoginActivity.class);
        startActivity(itLogin);
        finish();
    }

    private void abrePreMain() {
        Intent itPreMain = new Intent(SplashScreenActivity.this, PreMainActivity.class);
        startActivity(itPreMain);
        finish();
    }

}