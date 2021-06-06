package br.com.ifsul.bruna.exmoker.estatisticas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

import br.com.ifsul.bruna.exmoker.R;

public class ProgressoTimeLineAdapter extends RecyclerView.Adapter<ProgressoTimeLineAdapter.ProgressoViewHolder> {

    private List<ProgressoBeneficio> progressos;

    public ProgressoTimeLineAdapter(List<ProgressoBeneficio> progressos) {
        this.progressos = progressos;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @NonNull
    @Override
    public ProgressoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_progresso, parent, false);
        return new ProgressoViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressoViewHolder holder, int position) {
        ProgressoBeneficio pgBeneficio = progressos.get(position);
        holder.getTvDescricao().setText(pgBeneficio.getDescricao());
        holder.getTvProgresso().setText(pgBeneficio.getProgresso());
        holder.getPgProgresso().setProgress(pgBeneficio.getPorcentagemProgresso());
        if (pgBeneficio.getPorcentagemProgresso() == 100) {
            holder.getTlProgresso().setMarker(
                    holder.itemView.getResources().getDrawable(R.drawable.ic_circle),
                    holder.itemView.getResources().getColor(R.color.exmoker_darker));
        } else {
            holder.getTlProgresso().setMarker(
                    holder.itemView.getResources().getDrawable(R.drawable.ic_circle),
                    holder.itemView.getResources().getColor(R.color.cinza));
        }
    }

    @Override
    public int getItemCount() {
        return progressos.size();
    }

    public static class ProgressoViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDescricao;
        private final TextView tvProgresso;
        private final ProgressBar pgProgresso;
        private final TimelineView tlProgresso;


        public ProgressoViewHolder(View v, int viewType) {
            super(v);
            // Define click listener for the ViewHolder's View.
            tvDescricao = v.findViewById(R.id.tv_descricao);
            tvProgresso = v.findViewById(R.id.tv_progresso);
            pgProgresso = v.findViewById(R.id.pg_progresso);
            tlProgresso = v.findViewById(R.id.tl_progresso);
            tlProgresso.initLine(viewType);
        }

        public TimelineView getTlProgresso() {
            return tlProgresso;
        }

        public TextView getTvDescricao() {
            return tvDescricao;
        }

        public TextView getTvProgresso() {
            return tvProgresso;
        }

        public ProgressBar getPgProgresso() {
            return pgProgresso;
        }
    }
}
