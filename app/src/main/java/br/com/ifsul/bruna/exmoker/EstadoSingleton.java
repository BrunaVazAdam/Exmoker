package br.com.ifsul.bruna.exmoker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import br.com.ifsul.bruna.exmoker.colecoes.ColecoesChave;
import br.com.ifsul.bruna.exmoker.colecoes.ContatoDeApoio;
import br.com.ifsul.bruna.exmoker.colecoes.EventoDeAjuda;
import br.com.ifsul.bruna.exmoker.colecoes.InformacoesAdicionais;
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

    public Usuario syncUsuario() {
        db.collection(ColecoesChave.USUARIO).document(user.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    usuario = documentSnapshot.toObject(Usuario.class);
                });
        return usuario;
    }

    public Tabagista syncTabagista() {
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

    public Tabagista getTabagista() {
        return tabagista;
    }

    public void setTabagista(Tabagista tabagista) {
        this.tabagista = tabagista;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public void setMetodoParada(Boolean paradaGradual, Date dataParadaGradual) {
        if (!isLogged) return;
        Map<String, Object> data = new HashMap<>();
        data.put(ColecoesChave.PARADAGRADUAL, paradaGradual);
        data.put(ColecoesChave.DATAPARADAGRADUAL, dataParadaGradual);
        db.collection(ColecoesChave.TABAGISTA)
                .document(user.getUid())
                .set(data, SetOptions.merge());
    }

    public void setFinalizacaoCadastro() {
        if (!isLogged) return;
        Map<String, Object> data = new HashMap<>();
        data.put(ColecoesChave.DATAFINALIZACAOCADASTRO, new Date());
        db.collection(ColecoesChave.TABAGISTA)
                .document(user.getUid())
                .set(data, SetOptions.merge());
    }

    public void setQtdCigarrosDia(Integer qtdCigarrosDia) {
        if (!isLogged) return;
        db.collection(ColecoesChave.TABAGISTA)
                .document(user.getUid())
                .update(ColecoesChave.INFORMACOESADICIONAIS
                                + "." + ColecoesChave.QTDCIGARROSDIA,
                        qtdCigarrosDia);
    }

    public void addTesteFargestrom(TesteFargestrom testeFargestrom) {
        if (!isLogged) return;
        Map<String, Object> data = new HashMap<>();
        data.put(ColecoesChave.TESTESFARGESTROM, testeFargestrom);
        db.collection(ColecoesChave.TABAGISTA)
                .document(user.getUid())
                .update(ColecoesChave.TESTESFARGESTROM, FieldValue.arrayUnion(testeFargestrom));
    }

    public void addEventoDeAjuda(EventoDeAjuda eventoDeAjuda) {
        if (!isLogged) return;
        Map<String, Object> data = new HashMap<>();
        data.put(ColecoesChave.EVENTOSDEAJUDA, eventoDeAjuda);
        db.collection(ColecoesChave.TABAGISTA)
                .document(user.getUid())
                .update(ColecoesChave.EVENTOSDEAJUDA, FieldValue.arrayUnion(eventoDeAjuda));
    }

    public void setInformacoesAdicionais(InformacoesAdicionais informacoesAdicionais) {
        if (!isLogged) return;
        Map<String, Object> data = new HashMap<>();
        data.put(ColecoesChave.INFORMACOESADICIONAIS, informacoesAdicionais);
        db.collection(ColecoesChave.TABAGISTA)
                .document(user.getUid())
                .update(
                        ColecoesChave.INFORMACOESADICIONAIS + "." + ColecoesChave.PRECOCIGARRO,
                        informacoesAdicionais.getPrecoCigarro(),
                        ColecoesChave.INFORMACOESADICIONAIS + "." + ColecoesChave.QTDCIGARRONOMACO,
                        informacoesAdicionais.getQtdCigarroNoMaco(),
                        ColecoesChave.INFORMACOESADICIONAIS + "." + ColecoesChave.DATAINICIOTABAGISMO,
                        informacoesAdicionais.getDataInicioTabagismo()
                );
    }

    public List<TesteFargestrom> getTestesFargestrom() {
        return tabagista.getTestesFargestrom();
    }

    public Date getDataFinalizacaoCadastro() {
        return tabagista.getDataFinalizacaoCadastro();
    }


    public Double calculaGastoPorCigarro() {
        InformacoesAdicionais informacoesAdicionais = tabagista.getInformacoesAdicionais();
        return informacoesAdicionais.getPrecoCigarro() / informacoesAdicionais.getQtdCigarroNoMaco();
    }

    public Double calculaGastoDiario() {
        InformacoesAdicionais informacoesAdicionais = tabagista.getInformacoesAdicionais();
        Double gastoDiario = calculaGastoPorCigarro() * informacoesAdicionais.getQtdCigarrosPorDia();
        return gastoDiario;
    }

    public Double calculaSemanal() {
        return calculaGastoDiario() * 7;
    }

    public Double calculaMensal() {
        return calculaGastoDiario() * 30;
    }

    public Double calculaAnual() {
        return calculaGastoDiario() * 365;
    }

    public Double calculaTotal() {
        Date dataCadastro = tabagista.getDataFinalizacaoCadastro();
        InformacoesAdicionais informacoesAdicionais = tabagista.getInformacoesAdicionais();
        long difEmMili = Math.abs(dataCadastro.getTime() - informacoesAdicionais.getDataInicioTabagismo().getTime());
        long qtdDeDiasFumando = TimeUnit.DAYS.convert(difEmMili, TimeUnit.MILLISECONDS);
        return calculaGastoDiario() * qtdDeDiasFumando;
    }

    public Integer diasConsecutivosSemFumar() {
        Date dataUltimoCigarro = tabagista.getDataFinalizacaoCadastro();
        List<EventoDeAjuda> eventoDeAjudas = tabagista.getEventosDeAjuda();
        if (eventoDeAjudas != null) {
            for (EventoDeAjuda eventoDeAjuda : eventoDeAjudas) {
                if (eventoDeAjuda.getFoiRecaida()) {
                    dataUltimoCigarro = eventoDeAjuda.getDataDoEvento();
                }
            }
        }
        long difEmMili = Math.abs(new Date().getTime() - dataUltimoCigarro.getTime());
        long qtdDeDiasSemFumar = TimeUnit.DAYS.convert(difEmMili, TimeUnit.MILLISECONDS);
        return (int) qtdDeDiasSemFumar;
    }

    public Integer diasAteCadastro() {
        Date dataCadastro = tabagista.getDataFinalizacaoCadastro();
        long difEmMili = Math.abs(new Date().getTime() - dataCadastro.getTime());
        long qtdDeDiasCadastro = TimeUnit.DAYS.convert(difEmMili, TimeUnit.MILLISECONDS);
        return (int) qtdDeDiasCadastro;
    }

    public Integer calculaCigarrosEvitados() {
        InformacoesAdicionais informacoesAdicionais = tabagista.getInformacoesAdicionais();
        return informacoesAdicionais.getQtdCigarrosPorDia() * diasAteCadastro();
    }

    public Integer calculaCigarrosEvitadosTotal() {
        return calculaCigarrosEvitados() - calculaCigarrosFumados();
    }

    public Integer calculaCigarrosFumados() {
        List<EventoDeAjuda> eventoDeAjudas = tabagista.getEventosDeAjuda();
        if (eventoDeAjudas == null) return 0;
        Integer qtdCigarros = 0;
        for (EventoDeAjuda eventoDeAjuda : eventoDeAjudas) {
            if (eventoDeAjuda.getFoiRecaida()) {
                qtdCigarros += eventoDeAjuda.getQuantidadeDeCigarros();
            }
        }
        return qtdCigarros;
    }

    public Double calculaGastos() {
        return calculaCigarrosFumados() * calculaGastoPorCigarro();
    }

    public Double calculaEconomia() {
        Double economia = calculaGastoDiario() * diasAteCadastro();
        return economia - calculaGastos();
    }

    public String getPrimeiroNome(String nomeCompleto) {
        int primeiroEspaco = nomeCompleto.indexOf(' ');
        if (primeiroEspaco == -1) return nomeCompleto;
        return nomeCompleto.substring(0, primeiroEspaco);
    }

}
