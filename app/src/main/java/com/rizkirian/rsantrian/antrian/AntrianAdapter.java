package com.rizkirian.rsantrian.antrian;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizkirian.rsantrian.DetailAntrian;
import com.rizkirian.rsantrian.R;

import java.util.List;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class AntrianAdapter extends RecyclerView.Adapter<AntrianAdapter.RecyclerViewHolder> {

    private List<Antrian> list;
    private Activity activity;

    public AntrianAdapter(List<Antrian> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tmp_list1, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.bind(list.get(position));
        holder.itemView.setOnClickListener(v -> {
            Antrian antrian = list.get(position);
            Intent intent = new Intent(activity, DetailAntrian.class);
            intent.putExtra("nomor_antrian", antrian.getNomor_antrian());
            intent.putExtra("di_layani", antrian.getDi_layani());
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNamaPasien, tvPerkiraan, tvAntrian;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNamaPasien = itemView.findViewById(R.id.tv1);
            tvPerkiraan = itemView.findViewById(R.id.tv3);
            tvAntrian = itemView.findViewById(R.id.tv2);
        }

        private void bind(Antrian antrian) {
            tvNamaPasien.setText(antrian.getNama_pasien());
            tvPerkiraan.setText(antrian.getDi_layani());
            tvAntrian.setText(antrian.getNomor_antrian());
        }
    }
}
