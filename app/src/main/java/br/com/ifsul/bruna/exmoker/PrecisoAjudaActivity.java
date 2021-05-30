package br.com.ifsul.bruna.exmoker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import br.com.ifsul.bruna.exmoker.colecoes.ContatoDeApoio;
import br.com.ifsul.bruna.exmoker.colecoes.Tabagista;

public class PrecisoAjudaActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final int LIGACAO_REQUEST_CODE = 2;
    private LinearLayout llContatoApoio;
    private LinearLayout llIdentificarGatilhos;
    private TextView tvContatoApoio;
    private EstadoSingleton estado;
    private Tabagista tabagista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preciso_ajuda);
        inicializaComponentes();
        tabagista = estado.getTabagista();
        trocaNomeContatoApoio(tabagista.getContatoDeApoio());
        llContatoApoio.setOnClickListener(v -> {
            fazerLigacao();
        });
        llIdentificarGatilhos.setOnClickListener(v -> {
            abreQuestionarioGatilhos();
        });
    }

    private void trocaNomeContatoApoio(ContatoDeApoio contatoDeApoio) {
        tvContatoApoio.setText(
                getText(R.string.str_preciso_ajuda_ligar_para)
                        + " " + contatoDeApoio.getNome()
        );
    }

    private void fazerLigacao() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(
                    String.format("tel: %s", tabagista.getContatoDeApoio().getTelefone())
            ));
            startActivityForResult(callIntent, LIGACAO_REQUEST_CODE);
        } else {
            final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissaoGarantida = false;
        switch (requestCode) {
            case 9:
                permissaoGarantida = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissaoGarantida) {
            fazerLigacao();
        } else {
            Toast.makeText(this,
                    getText(R.string.str_preciso_ajuda_permissao_ligacao),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LIGACAO_REQUEST_CODE) {
            abreQuestionarioGatilhos();
        }
    }

    private void verfificaPermissaoLigar() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            fazerLigacao();
        }
    }

    private void abreQuestionarioGatilhos() {
        Intent itQuestionarioGatilhos = new Intent(PrecisoAjudaActivity.this, QuestionarioGatilhosActivity.class);
        startActivity(itQuestionarioGatilhos);
    }

    private void inicializaComponentes() {
        estado = EstadoSingleton.getInstance();
        llContatoApoio = findViewById(R.id.pa_ll_contato_apoio);
        llIdentificarGatilhos = findViewById(R.id.pa_ll_identificar_gatilhos);
        tvContatoApoio = findViewById(R.id.pa_tv_contato_apoio);
    }
}