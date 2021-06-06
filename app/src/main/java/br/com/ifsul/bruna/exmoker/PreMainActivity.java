package br.com.ifsul.bruna.exmoker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import br.com.ifsul.bruna.exmoker.colecoes.Tabagista;

public class PreMainActivity extends AppCompatActivity {
    private EstadoSingleton estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_main);
        estado = EstadoSingleton.getInstance();
        estado.syncUsuario();
        estado.getTabagistaAsync(documentSnapshot -> {
            Tabagista tabagista = documentSnapshot.toObject(Tabagista.class);
            estado.setTabagista(tabagista);
            Intent proximaTela = new Intent(PreMainActivity.this, MainActivity.class);
            if (tabagista == null || tabagista.getContatoDeApoio() == null) {
                proximaTela = new Intent(PreMainActivity.this, CadastroContatoApoioActivity.class);
            } else if (tabagista.getTestesFargestrom() == null) {
                proximaTela = new Intent(PreMainActivity.this, TesteFargestromActivity.class);
            } else if (tabagista.getInformacoesAdicionais().getPrecoCigarro() == null) {
                proximaTela = new Intent(PreMainActivity.this, InformacoesAdicionaisActivity.class);
            } else if (tabagista.getParadaGradual() == null) {
                proximaTela = new Intent(PreMainActivity.this, MetodoParadaActivity.class);
            } else if (tabagista.getDataFinalizacaoCadastro() == null) {
                estado.setFinalizacaoCadastro();

                Intent intent = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

                NotificationCompat.Builder notificacao;
                NotificationManagerCompat nm = NotificationManagerCompat.from(this);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String idCanal = "WELCOME";
                    NotificationChannel canal = new NotificationChannel(
                            idCanal,
                            "Canal para o recebimento de mensagem de boas vindas.",
                            NotificationManager.IMPORTANCE_HIGH);
                    nm.createNotificationChannel(canal);
                    notificacao = new NotificationCompat.Builder(this, idCanal);
                } else {
                    notificacao = new NotificationCompat.Builder(this);
                }

                notificacao.setSmallIcon(R.drawable.ic_notificacao);
                notificacao.setColor(getResources().getColor(R.color.exmoker_darker));
                notificacao.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                notificacao.setContentTitle("Bem vindo!");
                notificacao.setContentText("Parabéns por tornar-se um Exmoker! \uD83C\uDF89");
                notificacao.setPriority(NotificationCompat.PRIORITY_MAX);
                notificacao.setAutoCancel(true);
                notificacao.setContentIntent(pendingIntent);
                notificacao.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

                nm.notify(1, notificacao.build());

                proximaTela = new Intent(PreMainActivity.this, PreMainActivity.class);
            }
            startActivity(proximaTela);
            finish();
        });
    }
}