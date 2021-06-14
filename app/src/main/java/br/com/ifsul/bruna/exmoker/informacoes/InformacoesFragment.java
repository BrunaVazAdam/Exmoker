package br.com.ifsul.bruna.exmoker.informacoes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.baoyachi.stepview.VerticalStepView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.ifsul.bruna.exmoker.R;

public class InformacoesFragment extends Fragment {

    private View v;
    private VerticalStepView vStepViewInfosMaleficios;
    private VerticalStepView vStepViewInfosBeneficios;
    private LinearLayout llSiteInca;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_informacoes, container, false);
        vStepViewInfosMaleficios = v.findViewById(R.id.info_sp_infos_maleficios);
        vStepViewInfosBeneficios = v.findViewById(R.id.info_sp_infos_beneficios);
        llSiteInca = v.findViewById(R.id.ll_site_inca);
        llSiteInca.setOnClickListener(v -> {
            Intent itSiteInca = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.inca.gov.br/tabagismo"));
            startActivity(itSiteInca);
        });

        List<String> informacoesMaleficios = new ArrayList<>();
        informacoesMaleficios.add("Aumenta o risco de desenvolver doença coronariana em 2 a 4 vezes.");
        informacoesMaleficios.add("Aumenta o risco de desenvolver acidente vascular cerebral em 2 a 4 vezes.");
        informacoesMaleficios.add("Aumenta o risco de o homem desenvolver câncer de pulmão em 23 vezes.");
        informacoesMaleficios.add("Aumenta o risco de a mulher desenvolver câncer de pulmão em 13 vezes.");
        informacoesMaleficios.add("Aumenta o risco de morrer de doenças pulmonares obstrutivas crônicas (como bronquite crônica e enfisema) em 12 a 13 vezes.");

        List<String> informacoesBeneficios = new ArrayList<>();
        informacoesBeneficios.add("Após 20 minutos, a pressão sanguínea e a pulsação voltam ao normal.");
        informacoesBeneficios.add("Após 2 horas, não há mais nicotina circulando no sangue.");
        informacoesBeneficios.add("Após 8 horas, o nível de oxigênio no sangue se normaliza.");
        informacoesBeneficios.add("Após 24 horas, os pulmões já funcionam melhor.");
        informacoesBeneficios.add("Após 2 dias, o olfato já sente melhor os cheiros e o paladar percebe melhor a comida.");
        informacoesBeneficios.add("Após 3 semanas, a respiração se torna mais fácil, e a circulação melhora.");
        informacoesBeneficios.add("Após 1 ano, o risco de morte por infarto é reduzido à metade.");
        informacoesBeneficios.add("Após 10 anos, o risco de sofrer infarto será igual ao das pessoas que nunca fumaram.");

        Collections.reverse(informacoesBeneficios);
        Collections.reverse(informacoesMaleficios);

        vStepViewInfosMaleficios.setStepViewTexts(informacoesMaleficios);
        vStepViewInfosMaleficios.setStepViewComplectedTextColor(getResources().getColor(R.color.exmoker_darker));
        vStepViewInfosMaleficios.setStepViewUnComplectedTextColor(getResources().getColor(R.color.exmoker_darker));
        vStepViewInfosMaleficios.setStepsViewIndicatorDefaultIcon(getResources().getDrawable(R.drawable.ic_circle));
        vStepViewInfosMaleficios.setStepsViewIndicatorAttentionIcon(getResources().getDrawable(R.drawable.ic_circle));
        vStepViewInfosMaleficios.setStepsViewIndicatorCompleteIcon(getResources().getDrawable(R.drawable.ic_circle));

        vStepViewInfosBeneficios.setStepViewTexts(informacoesBeneficios);
        vStepViewInfosBeneficios.setStepViewComplectedTextColor(getResources().getColor(R.color.exmoker_darker));
        vStepViewInfosBeneficios.setStepViewUnComplectedTextColor(getResources().getColor(R.color.exmoker_darker));
        vStepViewInfosBeneficios.setStepsViewIndicatorDefaultIcon(getResources().getDrawable(R.drawable.ic_circle));
        vStepViewInfosBeneficios.setStepsViewIndicatorAttentionIcon(getResources().getDrawable(R.drawable.ic_circle));
        vStepViewInfosBeneficios.setStepsViewIndicatorCompleteIcon(getResources().getDrawable(R.drawable.ic_circle));
        return v;
    }
}