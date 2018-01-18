package com.androidtutorialshub.loginregister;

/**
 * Created by hp on 11/29/2017.
 */

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.User;
import com.androidtutorialshub.loginregister.SessionManager;

public class PulsaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private User user;
    public PulsaAdapter() {
        user = new User();
    }
    private class ItemUserViewHolder extends RecyclerView.ViewHolder {
        private TextView paketpulsa;
        public ItemUserViewHolder(View itemView) {
            super(itemView);
            //inisialisasi view
            paketpulsa = (TextView) itemView.findViewById(R.id.item_paketpulsa);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View _view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        return new ItemUserViewHolder(_view);
    }
    //menampilkan item View Holder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PulsaAdapter.ItemUserViewHolder _holder = (PulsaAdapter.ItemUserViewHolder) holder;
        final String _user = this.user.paket.get(position);
        _holder.paketpulsa.setText(_user);
        _holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _intent = new Intent(v.getContext(), NoHpActivity.class);
                _intent.putExtra(NoHpActivity.PAKET, _user);
                v.getContext().startActivity(_intent);
            }
        });
    }
    //mengembalikan panjang dari array paket
    @Override
    public int getItemCount() {
        return user.paket.size();
    }
}