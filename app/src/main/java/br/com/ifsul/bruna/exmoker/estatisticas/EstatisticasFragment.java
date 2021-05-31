package br.com.ifsul.bruna.exmoker.estatisticas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

import br.com.ifsul.bruna.exmoker.R;


public class EstatisticasFragment extends Fragment {

    private MaterialCardView cardNivelDependencia;
    private MaterialCardView cardEconomiaFinanceira;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_estatisticas, container, false);
        inicializaComponentes();
        cardNivelDependencia.setOnClickListener(v -> {
            Intent itNivelDependencia = new Intent(getContext(), EstatisticaDependenciaNicotinaActivity.class);
            startActivity(itNivelDependencia);
        });
        cardEconomiaFinanceira.setOnClickListener(v -> {
            Intent itEconomiaFinanceira = new Intent(getContext(), EstatisticaEconomiaFinanceiraActivity.class);
            startActivity(itEconomiaFinanceira);
        });
        return v;
    }

    private void inicializaComponentes() {
        cardNivelDependencia = v.findViewById(R.id.est_card_nivel_dependencia);
        cardEconomiaFinanceira = v.findViewById(R.id.est_card_economia_financeira);
    }
}