package br.com.ifsul.bruna.exmoker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.ifsul.bruna.exmoker.conquistas.ConquistasFragment;
import br.com.ifsul.bruna.exmoker.estatisticas.EstatisticasFragment;
import br.com.ifsul.bruna.exmoker.home.HomeFragment;
import br.com.ifsul.bruna.exmoker.informacoes.InformacoesFragment;

public class MainActivity extends AppCompatActivity {


    private FloatingActionButton floatingActionButtonAjuda;
    private BottomNavigationView bottomNav;

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializaComponentes();
        abreHome();


        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.btb_home:
                    abreHome();
                    break;
                case R.id.btb_achievement:
                    abreConquistas();
                    break;
                case R.id.btb_chart:
                    abreEstatisticas();
                    break;
                case R.id.btb_info:
                    abreInformacoes();
                    break;
            }
            return true;
        });


        floatingActionButtonAjuda.setOnClickListener(v -> {
            // TODO: Tela de botão de ajuda
        });
    }

    private void inicializaComponentes() {
        fm = getSupportFragmentManager();
        floatingActionButtonAjuda = findViewById(R.id.main_float_bt_ajuda);
        bottomNav = findViewById(R.id.main_bottom_nav);
    }

    private void abreHome() {
        Fragment homeFrag = new HomeFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_ll_container, homeFrag);
        ft.commit();
    }

    private void abreConquistas() {
        Fragment conquistasFrag = new ConquistasFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_ll_container, conquistasFrag);
        ft.commit();
    }

    private void abreEstatisticas() {
        Fragment estatisticasFrag = new EstatisticasFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_ll_container, estatisticasFrag);
        ft.commit();
    }

    private void abreInformacoes() {
        Fragment informacoesFrag = new InformacoesFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_ll_container, informacoesFrag);
        ft.commit();
    }

}