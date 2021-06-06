package br.com.ifsul.bruna.exmoker.conquistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import br.com.ifsul.bruna.exmoker.EstadoSingleton;
import br.com.ifsul.bruna.exmoker.R;

public class ConquistasFragment extends Fragment {

    private View v;
    private ProgressBar progressBarSemana;
    private TextView tvConquistaSemana;
    private EstadoSingleton estado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_conquistas, container, false);
        progressBarSemana = v.findViewById(R.id.conq_pg_semana);
        tvConquistaSemana = v.findViewById(R.id.conq_tv_semana);
        estado = EstadoSingleton.getInstance();
        Integer diasSemFumar = estado.diasConsecutivosSemFumar();
        Integer porcentagem = (int) ((diasSemFumar * 100) / 7);
        if (porcentagem > 100) porcentagem = 100;
        progressBarSemana.setProgress(porcentagem);
        if (diasSemFumar > 7) diasSemFumar = 7;
        tvConquistaSemana.setText(diasSemFumar + "/7");
        return v;
    }
}