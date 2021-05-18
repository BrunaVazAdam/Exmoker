package br.com.ifsul.bruna.exmoker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ValorCigarroActivity extends AppCompatActivity {

    private static int DELAY = 50;
    private Handler handler;
    private Runnable runnable;
    private TextView tvValorDezena;
    private TextView tvValorCentavo;

    private Button btIncrementaDezena;
    private Button btDecrementaDezena;
    private Button btIncrementaCentavo;
    private Button btDecrementaCentavo;

    private Button btConfirmaValor;

    private Integer valorDezena;
    private Integer valorCentavo;
    private Double valorMacoCigarro;

    private EstadoSingleton estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valor_cigarro);
        inicializaComponentes();
        configuraListenersDeValores();
        btConfirmaValor.setOnClickListener(v -> {
            calculaValorMacoCigarro();
            if (valorMacoCigarro == 0) {
                Toast.makeText(ValorCigarroActivity.this,
                        getString(R.string.str_valor_maco_cigarro_invalido),
                        Toast.LENGTH_LONG)
                        .show();
                return;
            }
            estado.setValorCigarro(valorMacoCigarro);
            Intent itPreMain = new Intent(ValorCigarroActivity.this, PreMainActivity.class);
            startActivity(itPreMain);
            finish();
        });


    }

    private void inicializaComponentes() {
        estado = EstadoSingleton.getInstance();
        handler = new Handler();
        valorDezena = 0;
        valorCentavo = 0;
        tvValorDezena = findViewById(R.id.vc_tv_valor_dezena);
        tvValorCentavo = findViewById(R.id.vc_tv_valor_centavo);
        btIncrementaDezena = findViewById(R.id.vc_bt_incrementa_valor_dezena);
        btDecrementaDezena = findViewById(R.id.vc_bt_decrementa_valor_dezena);
        btIncrementaCentavo = findViewById(R.id.vc_bt_incrementa_valor_centavo);
        btDecrementaCentavo = findViewById(R.id.vc_bt_decrementa_valor_centavo);
        btConfirmaValor = findViewById(R.id.vc_bt_confirmar_valor);
    }

    private void calculaValorMacoCigarro() {
        valorMacoCigarro = valorDezena + (double) valorCentavo / 100;
    }

    private void incrementaDezena() {
        valorDezena++;
        if (valorDezena == 100) valorDezena = 0;
        tvValorDezena.setText(String.format("%02d", valorDezena));
    }

    private void decrementaDezena() {
        valorDezena--;
        if (valorDezena == -1) valorDezena = 99;
        tvValorDezena.setText(String.format("%02d", valorDezena));
    }

    private void incrementaCentavo() {
        valorCentavo++;
        if (valorCentavo == 100) valorCentavo = 0;
        tvValorCentavo.setText(String.format("%02d", valorCentavo));
    }

    private void decrementaCentavo() {
        valorCentavo--;
        if (valorCentavo == -1) valorCentavo = 99;
        tvValorCentavo.setText(String.format("%02d", valorCentavo));
    }

    private void configuraListenersDeValores() {
        btIncrementaDezena.setOnClickListener(v -> {
            incrementaDezena();
        });
        btIncrementaDezena.setOnLongClickListener(v -> {
            runnable = () -> {
                if (!btIncrementaDezena.isPressed()) return;
                incrementaDezena();
                handler.postDelayed(runnable, DELAY);
            };
            handler.postDelayed(runnable, DELAY);
            return true;
        });
        btDecrementaDezena.setOnClickListener(v -> {
            decrementaDezena();
        });
        btDecrementaDezena.setOnLongClickListener(v -> {
            runnable = () -> {
                if (!btDecrementaDezena.isPressed()) return;
                decrementaDezena();
                handler.postDelayed(runnable, DELAY);
            };
            handler.postDelayed(runnable, DELAY);
            return true;
        });

        btIncrementaCentavo.setOnClickListener(v -> {
            incrementaCentavo();
        });
        btIncrementaCentavo.setOnLongClickListener(v -> {
            runnable = () -> {
                if (!btIncrementaCentavo.isPressed()) return;
                incrementaCentavo();
                handler.postDelayed(runnable, DELAY);
            };
            handler.postDelayed(runnable, DELAY);
            return true;
        });
        btDecrementaCentavo.setOnClickListener(v -> {
            decrementaCentavo();
        });
        btDecrementaCentavo.setOnLongClickListener(v -> {
            runnable = () -> {
                if (!btDecrementaCentavo.isPressed()) return;
                decrementaCentavo();
                handler.postDelayed(runnable, DELAY);
            };
            handler.postDelayed(runnable, DELAY);
            return true;
        });
    }
}