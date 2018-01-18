package com.androidtutorialshub.loginregister;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MoreFragment extends Fragment implements View.OnClickListener{
    private User user;
    private TextView saldoefektif;
    public String saldoAnda1;
    private LinearLayout security, history, about ,logout, copyright;
    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_more, container, false);
        initViews(_view);
        initListeners();
        /* setting */
        user=new User();
        setUsers(getContext());
        return _view;
    }
    /**
     * This method is to initialize views
     */
    private void initViews(View _view) {
        saldoefektif=(TextView)_view.findViewById(R.id.saldo_efektif);
        security=(LinearLayout)_view.findViewById(R.id.security);
        history=(LinearLayout)_view.findViewById(R.id.history);
        about=(LinearLayout)_view.findViewById(R.id.about);
        logout=(LinearLayout)_view.findViewById(R.id.logout);
        copyright=(LinearLayout)_view.findViewById(R.id.copyright);
    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        security.setOnClickListener(this);
        history.setOnClickListener(this);
        logout.setOnClickListener(this);
        about.setOnClickListener(this);
    }
    /**
     * This implemented method is to listen the click on view
     *
     */
    public void setUsers(Context context) {
        saldoAnda1=SessionManager.with(context).getuserloggedin().getSaldo();
        saldoefektif.setText(saldoAnda1);
    }
    /**
     * This implemented method is to listen the click on view
     *
     */
    public void onClick(View v){
        if(v.getId()==R.id.security){
            Intent accountsIntent = new Intent(getContext(), SecurityActivity.class);
            startActivity(accountsIntent);
        }
        else if(v.getId()==R.id.history){
            Intent accountsIntent = new Intent(getContext(), HistoryActivity.class);
            startActivity(accountsIntent);
        }
        else if(v.getId()==R.id.logout){
            new AlertDialog.Builder(getContext())
            .setMessage("Apakah Anda ingin Logout?")
            .setCancelable(false)
            .setNegativeButton("No", null)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SessionManager.with(getContext()).clearsession();
                    Intent keluar=new Intent(getContext(),LoginActivity.class);
                    keluar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(keluar);
                             }
            })
            .show();
        }
        else if(v.getId()==R.id.about){
            if(copyright.getVisibility()==View.INVISIBLE){
                copyright.setVisibility(View.VISIBLE);
            }
            else{
                copyright.setVisibility(View.INVISIBLE);
            }
        }
    }
}
