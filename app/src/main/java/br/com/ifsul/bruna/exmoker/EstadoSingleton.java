package br.com.ifsul.bruna.exmoker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import br.com.ifsul.bruna.exmoker.colecoes.ColecoesChave;
import br.com.ifsul.bruna.exmoker.colecoes.ContatoDeApoio;
import br.com.ifsul.bruna.exmoker.colecoes.Tabagista;
import br.com.ifsul.bruna.exmoker.colecoes.TesteFargestrom;
import br.com.ifsul.bruna.exmoker.colecoes.Usuario;

public class EstadoSingleton {
    private static EstadoSingleton _instance;

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private boolean isLogged;
    private Usuario usuario;
    private Tabagista tabagista;


    private EstadoSingleton() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        auth.addAuthStateListener(firebaseAuth -> {
            user = firebaseAuth.getCurrentUser();
            isLogged = user != null;
            if (isLogged) {
                syncUsuario();
                syncTabagista();
            }
        });
    }

    public static EstadoSingleton getInstance() {
        if (_instance == null) {
            _instance = new EstadoSingleton();
        }
        return _instance;
    }

    private Usuario syncUsuario() {
        db.collection(ColecoesChave.USUARIO).document(user.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    usuario = documentSnapshot.toObject(Usuario.class);
                });
        return usuario;
    }

    private Tabagista syncTabagista() {
        db.collection(ColecoesChave.TABAGISTA).document(user.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    tabagista = documentSnapshot.toObject(Tabagista.class);
                });
        return tabagista;
    }

    public void signOut() {
        auth.signOut();
        usuario = null;
        tabagista = null;
    }

    public void getTabagistaAsync(OnSuccessListener<DocumentSnapshot> successListener) {
        db.collection(ColecoesChave.TABAGISTA).document(user.getUid()).get().addOnSuccessListener(successListener);
    }

    public FirebaseAuth getAuthInstance() {
        return auth;
    }

    public void setDataNascimento(String dataNascimento) {
        if (!isLogged) return;
        usuario = new Usuario(dataNascimento);
        db.collection(ColecoesChave.USUARIO).document(user.getUid()).set(usuario);
    }

    public void setContatoDeApoio(ContatoDeApoio contatoDeApoio) {
        if (!isLogged) return;
        Map<String, Object> data = new HashMap<>();
        data.put(ColecoesChave.CONTATODEAPOIO, contatoDeApoio);
        db.collection(ColecoesChave.TABAGISTA)
                .document(user.getUid())
                .set(data, SetOptions.merge());
    }

    public void setValorCigarro(Double valorCigarro) {
        if (!isLogged) return;
        Map<String, Object> data = new HashMap<>();
        data.put(ColecoesChave.VALORCIGARRO, valorCigarro);
        db.collection(ColecoesChave.TABAGISTA)
                .document(user.getUid())
                .set(data, SetOptions.merge());
    }

    public void addTesteFargestrom(TesteFargestrom testeFargestrom) {
        if (!isLogged) return;
        Map<String, Object> data = new HashMap<>();
        data.put(ColecoesChave.TESTESFARGESTROM, testeFargestrom);
        db.collection(ColecoesChave.TABAGISTA)
                .document(user.getUid())
                .update(ColecoesChave.TESTESFARGESTROM, FieldValue.arrayUnion(testeFargestrom));
    }
}
