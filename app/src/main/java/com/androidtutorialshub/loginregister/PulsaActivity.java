package com.androidtutorialshub.loginregister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class PulsaActivity extends AppCompatActivity {
    private RecyclerView rv;
    private PulsaAdapter adapter;

    public PulsaActivity() {
        // Required empty public constructor
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulsa);
        //inisialisasi view
        rv = (RecyclerView)findViewById(R.id.user_asc_rv);
        //inisialisasi object
        adapter = new PulsaAdapter();
        rv.setHasFixedSize(true);
        //membagi layout menjadi 2 grid
        rv.setLayoutManager(new GridLayoutManager(this,2));
        rv.setAdapter(adapter);
    }

}
