package com.androidtutorialshub.loginregister;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;


public class TransferActivity extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = TransferActivity.this;

    private TextInputEditText edittextnorektujuan,edittextjlhdana;
    private TextInputLayout layoutnorektujuan,layoutjlhdana;
    private AppCompatButton transfer;
    private User user,user1;
    private History history;
    public String norekAnda1,saldoAnda1;
    private NestedScrollView nestedScrollView;
    private AppCompatTextView textViewLinkHome;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private DBHelper dbHelper;
    private Transfer transferclass;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        //menyembunyikan action bar
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();
        setUsers(this);
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        edittextnorektujuan=(TextInputEditText)findViewById(R.id.textInputEditTextNoRekTujuan);
        layoutnorektujuan=(TextInputLayout)findViewById(R.id.textInputLayoutNoRekTujuan);
        edittextjlhdana=(TextInputEditText)findViewById(R.id.textInputEditTextJumlahDana);
        layoutjlhdana=(TextInputLayout)findViewById(R.id.textInputLayoutJumlahDana);
        textViewLinkHome=(AppCompatTextView) findViewById(R.id.textViewLinkHome);
        transfer=(AppCompatButton) findViewById(R.id.appCompatButtonTransfer);
    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        textViewLinkHome.setOnClickListener(this);
        transfer.setOnClickListener(this);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        dbHelper = new DBHelper(activity);
        inputValidation = new InputValidation(activity);
        user=new User();
        transferclass=new Transfer();
        history=new History();
        progress = new ProgressDialog(this);
    }
    //mengambil data norekening dan saldo pada Session
    public void setUsers(Context context) {
        norekAnda1=SessionManager.with(context).getuserloggedin().getNoRek();
        saldoAnda1=SessionManager.with(context).getuserloggedin().getSaldo();
    }
    /**
     * This implemented method is to listen the click on view
     *
     */
    public void onClick(View v){
        if(v.getId()==R.id.appCompatButtonTransfer) {
            String jlhdana=edittextjlhdana.getText().toString().trim();
            if(transferclass.Saldomin_bebastransfer(saldoAnda1,jlhdana)){
                transaksi();
            }
            else if(!transferclass.Saldomin_bebastransfer(saldoAnda1,jlhdana)){
                new AlertDialog.Builder(this)
                .setMessage("Apakah Anda ingin membayar biaya administrasi Rp 5000?")
                .setCancelable(false)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        transaksi();
                    }
                })
                .show();
            }
        }
        else{
            Intent accountsIntent = new Intent(this, HomeUserActivity.class);
            emptyInputEditText();
            startActivity(accountsIntent);
        }
    }
    //melakukan proses validasi dan transaksi
    public void transaksi(){
        String norektujuan=edittextnorektujuan.getText().toString().trim();
        String jlhdana=edittextjlhdana.getText().toString().trim();
        String saldopenerima="";
        boolean other=true;
        boolean _isnorekexist = false;
        for (int i=0;i<User.users.size();i++) {
            if (User.users.get(i).norek.equals(norektujuan)) {
                _isnorekexist = true;
                user = User.users.get(i);
                saldopenerima=User.users.get(i).saldo;
                break;
            }
        }
        history.getCurrentDate();
        String kodeasli="TR";
        Integer batas = transferclass.saldoakhirpengirim(saldoAnda1, jlhdana);
        if (norekAnda1.equals(norektujuan)) {
            other = false;
        }
        if (!inputValidation.isInputEditTextNoRekExist(edittextnorektujuan, layoutnorektujuan, _isnorekexist, getString(R.string.error_norek_not_exists))) {
            return;
        }
        if (!inputValidation.isInputEditTextOtherNoRek(edittextnorektujuan, layoutnorektujuan, other, getString(R.string.error_valid_message_othernorektujuan))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(edittextnorektujuan, layoutnorektujuan, getString(R.string.error_message_norektujuan))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(edittextjlhdana, layoutjlhdana, getString(R.string.error_message_jlhdana))) {
            return;
        }
        if (!inputValidation.isInputEditTextDanaMin(edittextjlhdana, layoutjlhdana, getString(R.string.error_message_jlhdanamin))) {
            return;
        }
        if (!inputValidation.isInputEditTextDanaMinKelipatan(edittextjlhdana, layoutjlhdana, getString(R.string.error_message_jlhdanaminkelipatan))) {
            return;
        }
        if (!inputValidation.isInputEditTextDanaMax(batas, edittextjlhdana, layoutjlhdana, getString(R.string.error_message_saldokurang))) {
            return;
        }
        if (databaseHelper.checkNoRek(norektujuan)) {
            if (transferclass.BatasTransfer(norekAnda1, history.getDateTime(), jlhdana)) {
                String kodenya = dbHelper.KodeHistory();
                history.setKode(history.kodetransaksi(kodeasli, kodenya));
                history.setNoRekAsal(norekAnda1);
                history.setNoRekTujuan(norektujuan);
                history.setJumlah(transferclass.konversiuang(jlhdana));
                dbHelper.addHistory(history);

                user = transferclass.transaksi(norekAnda1, transferclass.saldoakhirpengirim(saldoAnda1, jlhdana));
                databaseHelper.updateSaldo(user);
                user1 = transferclass.transaksi(norektujuan, transferclass.saldoakhirpenerima(saldopenerima, jlhdana));
                databaseHelper.updateSaldo(user1);
                for (int i = 0; i < User.users.size(); i++) {
                    if (User.users.get(i).norek.equals(norekAnda1)) {
                        user = User.users.get(i);
                        break;
                    }
                }
                SessionManager sessionManager = SessionManager.with(this);
                sessionManager.updatesession(user);
                // Snack Bar to show success message that record saved successfully
                Snackbar.make(nestedScrollView, getString(R.string.success_message_transfer), Snackbar.LENGTH_LONG).show();
                progress.setMessage("Loading...");
                progress.show();
                Thread _thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(4000);
                        progress.dismiss();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            masuk();
                        }
                    });
                    }
                };
                _thread.start();
            }
            else {
                // Snack Bar to show success message that record is wrong
                Snackbar.make(nestedScrollView, getString(R.string.error_message_jlhdanamax), Snackbar.LENGTH_LONG).show();
            }
        }
        else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_message_norektujuan), Snackbar.LENGTH_LONG).show();
        }

    }
    private void masuk() {
        Intent accountsIntent = new Intent(this, HomeUserActivity.class);
        emptyInputEditText();
        startActivity(accountsIntent);
    }
    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        edittextnorektujuan.setText(null);
        edittextjlhdana.setText(null);
    }
}

