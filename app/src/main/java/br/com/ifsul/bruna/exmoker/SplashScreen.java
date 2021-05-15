package br.com.ifsul.bruna.exmoker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user == null) {
                    abreLogin();
                } else {
                    abreHome();
                }
            }
        }, 2000);


    }

    private void abreLogin() {
        Intent itLogin = new Intent(SplashScreen.this, LoginActivity.class);
        startActivity(itLogin);
        finish();
    }

    private void abreHome() {
        Intent itHome = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(itHome);
        finish();
    }

}