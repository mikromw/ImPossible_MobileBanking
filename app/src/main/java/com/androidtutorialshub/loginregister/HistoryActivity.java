package com.androidtutorialshub.loginregister;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener{

    private History history;
    private RecyclerView rv;
    private HistoryAdapter adapter;
    public String norekAnda1;
    public Button newest,oldest;
    public HistoryActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initViews();
        initListeners();
        initObjects();
        /* setting */
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        setUsers(this);
        rv.setAdapter(adapter);
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {
        rv = (RecyclerView) findViewById(R.id.user_asc_rv);
        newest=(Button)findViewById(R.id.newest);
        oldest=(Button)findViewById(R.id.oldest);
    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        newest.setOnClickListener(this);
        oldest.setOnClickListener(this);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        adapter = new HistoryAdapter();
        history=new History();
    }
    /**
     * This implemented method is to listen the click on view
     *
     */
    public void onClick(View v){
        if(v.getId()==R.id.newest){
            newest.setEnabled(false);
            oldest.setEnabled(true);
            oldest.setTextColor(Color.WHITE);
            oldest.setBackgroundColor(Color.parseColor("#002866"));
            newest.setTextColor(Color.parseColor("#002866"));
            newest.setBackgroundColor(Color.parseColor("#e5efff"));
            history.filternew(norekAnda1);
            rv.setAdapter(adapter);
        }
        else if(v.getId()==R.id.oldest){
            oldest.setEnabled(false);
            newest.setEnabled(true);
            newest.setTextColor(Color.WHITE);
            newest.setBackgroundColor(Color.parseColor("#002866"));
            oldest.setTextColor(Color.parseColor("#002866"));
            oldest.setBackgroundColor(Color.parseColor("#e5efff"));
            history.filterold(norekAnda1);
            rv.setAdapter(adapter);
        }
    }
    //mengambil data norekening pada Session dan melakukan filter terbaru pada history
    public void setUsers(Context context) {
        norekAnda1=SessionManager.with(context).getuserloggedin().getNoRek();
        history.filternew(norekAnda1);
    }
}
