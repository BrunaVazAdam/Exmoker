package br.com.ifsul.bruna.exmoker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.com.ifsul.bruna.exmoker.colecoes.Tabagista;

public class PreMainActivity extends AppCompatActivity {
    private EstadoSingleton estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_main);
        estado = EstadoSingleton.getInstance();
        estado.getTabagistaAsync(documentSnapshot -> {
            Tabagista tabagista = documentSnapshot.toObject(Tabagista.class);
            Intent proximaTela = new Intent(PreMainActivity.this, MainActivity.class);
            if (tabagista == null || tabagista.getContatoDeApoio() == null) {
                proximaTela = new Intent(PreMainActivity.this, CadastroContatoApoioActivity.class);

            } else if (tabagista.getTestesFargestrom() == null) {
                proximaTela = new Intent(PreMainActivity.this, TesteFargestromActivity.class);
            } else if (tabagista.getPrecoCigarro() == null) {
                proximaTela = new Intent(PreMainActivity.this, ValorCigarroActivity.class);
            }
            startActivity(proximaTela);
            finish();
        });
    }
}