package br.com.ifsul.bruna.exmoker.conquistas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import br.com.ifsul.bruna.exmoker.BuildConfig;
import br.com.ifsul.bruna.exmoker.EstadoSingleton;
import br.com.ifsul.bruna.exmoker.R;

public class ConquistasFragment extends Fragment {

    private View v;
    private View shareExmoker;
    private View shareSeteDias;
    private ProgressBar progressBarSemana;
    private TextView tvConquistaSemana;
    private EstadoSingleton estado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_conquistas, container, false);
        progressBarSemana = v.findViewById(R.id.conq_pg_semana);
        tvConquistaSemana = v.findViewById(R.id.conq_tv_semana);
        shareExmoker = v.findViewById(R.id.share_exmoker);
        shareSeteDias = v.findViewById(R.id.share_sete_dias);
        estado = EstadoSingleton.getInstance();
        Integer diasSemFumar = estado.diasConsecutivosSemFumar();
        Integer porcentagem = (int) ((diasSemFumar * 100) / 7);
        if (porcentagem > 100) porcentagem = 100;
        progressBarSemana.setProgress(porcentagem);
        if (diasSemFumar > 7) diasSemFumar = 7;
        tvConquistaSemana.setText(diasSemFumar + "/7");

        if (diasSemFumar == 7) {
            shareSeteDias.setVisibility(View.VISIBLE);
        }

        shareExmoker.setOnClickListener(v -> {
            String mensagem = estado.getPrimeiroNomeUser()
                    + " tornou-se um Exmoker! 🎉";
            shareImage(criarBitmapPorView(getResources().getDrawable(R.drawable.ic_star), mensagem));
        });

        shareSeteDias.setOnClickListener(v -> {
            String mensagem = estado.getPrimeiroNomeUser()
                    + " ficou sete dias sem fumar! 🎉";
            shareImage(criarBitmapPorView(getResources().getDrawable(R.drawable.ic_week), mensagem));
        });
        return v;
    }

    public Bitmap criarBitmapPorView(Drawable img, String texto) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View shareView = inflater.inflate(R.layout.share_layout, null, false);
        TextView tvMsgConquista = shareView.findViewById(R.id.tv_share);
        tvMsgConquista.setText(texto);
        ImageView imgConquista = shareView.findViewById(R.id.img_share);
        imgConquista.setImageDrawable(img);
        shareView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Bitmap b = Bitmap.createBitmap(shareView.getMeasuredWidth(), shareView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        shareView.layout(0, 0, shareView.getMeasuredWidth(), shareView.getMeasuredHeight());
        shareView.draw(c);
        return b;
    }

    private void shareImage(Bitmap bitmap) {
        try {
            File cachePath = new File(getContext().getCacheDir(), "images");
            cachePath.mkdirs();
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        File imagePath = new File(getContext().getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setDataAndType(contentUri, getContext().getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType("image/png");
            startActivity(shareIntent);
        }
    }
}