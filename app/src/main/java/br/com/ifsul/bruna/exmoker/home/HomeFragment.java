package br.com.ifsul.bruna.exmoker.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import br.com.ifsul.bruna.exmoker.EstadoSingleton;
import br.com.ifsul.bruna.exmoker.R;

public class HomeFragment extends Fragment {
    private CircularImageView profileAvatar;
    private TextView tvGreeting;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private EstadoSingleton estado;
    private View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, null);
        inicializaComponentes();
        carregaAvatar();
        carregaSaudacao();
        return v;
    }

    private void inicializaComponentes() {
        profileAvatar = v.findViewById(R.id.ho_profile_avatar);
        tvGreeting = v.findViewById(R.id.ho_greeting);
        estado = EstadoSingleton.getInstance();
        mAuth = estado.getAuthInstance();
        user = mAuth.getCurrentUser();
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
