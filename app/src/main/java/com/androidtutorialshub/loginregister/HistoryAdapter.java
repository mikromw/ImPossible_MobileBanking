package com.androidtutorialshub.loginregister;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.User;
import com.androidtutorialshub.loginregister.SessionManager;


public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private History history;

    public HistoryAdapter() {
        this.history = new History();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View _view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ItemUserViewHolder(_view);
    }
    //menampilkan item View Holder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final HistoryAdapter.ItemUserViewHolder _holder = (HistoryAdapter.ItemUserViewHolder) holder;
        final History _history = this.history.historyfilter.get(position);
        _holder.kode.setText(_history.getKode());
        _holder.norekasal.setText("From : "+_history.getNoRekAsal());
        _holder.norektujuan.setText("To : "+_history.getNoRekTujuan());
        _holder.jlhdana.setText(_history.getJumlah());
        _holder.tglwkt.setText(_history.getDateTime()+"\nJenis : "+_history.getJenis());
    }
    //mengembalikan panjang dari array historyfilter
    @Override
    public int getItemCount() {
        return history.historyfilter.size();
    }

    private class ItemUserViewHolder extends RecyclerView.ViewHolder {
        private TextView kode, norekasal, norektujuan, jlhdana,tglwkt;
        public ItemUserViewHolder(View itemView) {
            super(itemView);
            //inisialisasi views
            kode = (TextView) itemView.findViewById(R.id.item_kode);
            norekasal = (TextView) itemView.findViewById(R.id.item_norekasal);
            norektujuan = (TextView) itemView.findViewById(R.id.item_norektujuan);
            jlhdana = (TextView) itemView.findViewById(R.id.item_jlhdana);
            tglwkt = (TextView) itemView.findViewById(R.id.item_tglwkt);
        }
    }

}