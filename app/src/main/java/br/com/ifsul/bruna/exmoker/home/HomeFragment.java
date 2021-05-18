package br.com.ifsul.bruna.exmoker.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import br.com.ifsul.bruna.exmoker.EstadoSingleton;
import br.com.ifsul.bruna.exmoker.LoginActivity;
import br.com.ifsul.bruna.exmoker.R;

public class HomeFragment extends Fragment {
    private CircularImageView profileAvatar;
    private Button btOptionsMenu;
    private TextView tvGreeting;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private EstadoSingleton estado;
    private View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, null);
        inicializaComponentes();
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
        carregaAvatar();
        carregaSaudacao();
        return v;
    }

    private void inicializaComponentes() {
        profileAvatar = v.findViewById(R.id.ho_profile_avatar);
        btOptionsMenu = v.findViewById(R.id.ho_bt_options_menu);
        tvGreeting = v.findViewById(R.id.ho_greeting);
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
        tvGreeting.setText("Olá, " + user.getDisplayName() + "!");
    }
}
