package br.com.ifsul.bruna.exmoker.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

import br.com.ifsul.bruna.exmoker.EstadoSingleton;
import br.com.ifsul.bruna.exmoker.LoginActivity;
import br.com.ifsul.bruna.exmoker.R;

public class HomeFragment extends Fragment {

    private NumberFormat valorFormatter;
    private MaterialCardView cardLocalizarUbs;
    private CircularImageView profileAvatar;
    private Button btOptionsMenu;
    private TextView tvGreeting;
    private TextView tvDinheiroPoupado;
    private TextView tvCigarrosEvitados;
    private TextView tvDiasSemFumar;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private EstadoSingleton estado;
    private View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, null);
        inicializaComponentes();
        tvDinheiroPoupado.setText(valorFormatter.format(estado.calculaEconomia()));
        tvCigarrosEvitados.setText(estado.calculaCigarrosEvitadosTotal() + " " + getString(R.string.str_cigarros));
        String textoDiasSemFumar = getString(R.string.str_voce_esta)
                + "  <b>" + estado.diasConsecutivosSemFumar()
                + " </b>" + getString(R.string.str_dias_sem_fumar);
        tvDiasSemFumar.setText(Html.fromHtml(textoDiasSemFumar));
        btOptionsMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), btOptionsMenu);
            popupMenu.getMenuInflater().inflate(R.menu.options_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.ho_menu_option_logout:
                        estado.signOut();
                        Intent itLogin = new Intent(getContext(), LoginActivity.class);
                        startActivity(itLogin);
                        getActivity().finish();
                        break;
                }
                return true;
            });
            popupMenu.show();
        });
        cardLocalizarUbs.setOnClickListener(v -> {
            Intent itBuscaMaps = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=Unidade Básica de Saúde"));
            startActivity(itBuscaMaps);
        });
        carregaAvatar();
        carregaSaudacao();
        return v;
    }

    private void inicializaComponentes() {
        valorFormatter = NumberFormat.getCurrencyInstance();
        cardLocalizarUbs = v.findViewById(R.id.ho_card_localizar_ubs);
        profileAvatar = v.findViewById(R.id.ho_profile_avatar);
        btOptionsMenu = v.findViewById(R.id.ho_bt_options_menu);
        tvGreeting = v.findViewById(R.id.ho_greeting);
        tvDinheiroPoupado = v.findViewById(R.id.ho_tv_dinheiro_poupado);
        tvCigarrosEvitados = v.findViewById(R.id.ho_tv_cigarros_evitados);
        tvDiasSemFumar = v.findViewById(R.id.ho_tv_dias_sem_fumar);
        estado = EstadoSingleton.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    private void carregaAvatar() {
        String userName = user.getDisplayName();
        String avatarUrl = "https://ui-avatars.com/api/?name=" +
                userName + "&background=70C0A5&color=fff&bold=true&size=128";
        if (user.getPhotoUrl() != null) {
            avatarUrl = user.getPhotoUrl().toString();
        }
        Picasso.with(getContext())
                .load(avatarUrl)
                .placeholder(R.drawable.ic_default_avatar)
                .into(profileAvatar);
    }

    private void carregaSaudacao() {
        tvGreeting.setText("Olá, " + estado.getPrimeiroNome(user.getDisplayName()) + "!");
    }
}
