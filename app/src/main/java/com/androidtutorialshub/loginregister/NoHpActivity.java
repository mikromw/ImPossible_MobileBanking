package com.androidtutorialshub.loginregister;

import android.app.ProgressDialog;
import android.content.Context;
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


public class NoHpActivity extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = NoHpActivity.this;
    public static final String PAKET="paket";
    private TextInputEditText edittextnohptujuan,edittextjlhpulsa;
    private TextInputLayout layoutnohptujuan,layoutjlhpulsa;
    private AppCompatButton transfer;
    private User user;
    private History history;
    public String norekAnda1,saldoAnda1;
    private NestedScrollView nestedScrollView;
    private AppCompatTextView textViewLinkHome,textViewLinkPulsa;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private DBHelper dbHelper;
    private Transfer transferclass;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_hp);
        //menyembunyikan action bar
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();
        String pulsa = getIntent().getStringExtra(PAKET);
        String pulsaasli=pulsa.substring(8,pulsa.length());
        edittextjlhpulsa.setText(pulsaasli);
        setUsers(this);
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        edittextnohptujuan=(TextInputEditText)findViewById(R.id.textInputEditTextNoHpTujuan);
        layoutnohptujuan=(TextInputLayout)findViewById(R.id.textInputLayoutNoHpTujuan);
        edittextjlhpulsa=(TextInputEditText)findViewById(R.id.textInputEditTextJumlahPulsa);
        layoutjlhpulsa=(TextInputLayout)findViewById(R.id.textInputLayoutJumlahPulsa);
        textViewLinkHome=(AppCompatTextView) findViewById(R.id.textViewLinkHome);
        textViewLinkPulsa=(AppCompatTextView) findViewById(R.id.textViewLinkPulsa);
        transfer=(AppCompatButton) findViewById(R.id.appCompatButtonTransfer);
    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        textViewLinkHome.setOnClickListener(this);
        textViewLinkPulsa.setOnClickListener(this);
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
        if(v.getId()==R.id.appCompatButtonTransfer){
            history.getCurrentDate();
            String jlhpulsa=edittextjlhpulsa.getText().toString().trim();
            String kodeasli="PS";
            Integer batas=transferclass.saldoakhirpengirim(saldoAnda1,jlhpulsa);
            if (!inputValidation.isInputEditTextFilled(edittextnohptujuan, layoutnohptujuan, getString(R.string.error_message_nohp))) {
                return;
            }
            if (!inputValidation.isInputEditTextDanaMax(batas, edittextjlhpulsa, layoutjlhpulsa, getString(R.string.error_message_saldokurang))) {
                return;
            }
            if (inputValidation.isInputEditTextNoHp(edittextnohptujuan)) {
                if(transferclass.BatasTransfer(norekAnda1,history.getDateTime(),jlhpulsa)){
                    String kodenya=dbHelper.KodeHistory();
                    history.setKode(history.kodetransaksi(kodeasli,kodenya));
                    history.setNoRekAsal(norekAnda1);
                    history.setNoRekTujuan("-");
                    history.setJumlah(transferclass.konversiuang(jlhpulsa));
                    dbHelper.addHistory(history);
                    user=transferclass.transaksi(norekAnda1,transferclass.saldoakhirpengirim(saldoAnda1,jlhpulsa));
                    databaseHelper.updateSaldo(user);
                    for (int i=0;i<User.users.size();i++) {
                        if (User.users.get(i).norek.equals(norekAnda1)) {
                            user = User.users.get(i);
                            break;
                        }
                    }
                    SessionManager sessionManager = SessionManager.with(this);
                    sessionManager.updatesession(user);
                    // Snack Bar to show success message that record saved successfully
                    Snackbar.make(nestedScrollView, getString(R.string.success_message_transfer), Snackbar.LENGTH_LONG).show();
                    progress = new ProgressDialog(this);
                    progress.setMessage("Loading ...");
                    progress.show();
                    Thread _thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                sleep(3000);
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
                else{
                    // Snack Bar to show success message that record is wrong
                    Snackbar.make(nestedScrollView, getString(R.string.error_message_jlhdanamax), Snackbar.LENGTH_LONG).show();
                }
            }
            else {
                // Snack Bar to show success message that record is wrong
                Snackbar.make(nestedScrollView, getString(R.string.error_valid_message_norektujuan), Snackbar.LENGTH_LONG).show();
            }
        }
        else if(v.getId()==R.id.textViewLinkPulsa){
            Intent accountsIntent = new Intent(this, PulsaActivity.class);
            emptyInputEditText();
            startActivity(accountsIntent);
        }
        else{
            Intent accountsIntent = new Intent(this, HomeUserActivity.class);
            emptyInputEditText();
            startActivity(accountsIntent);
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
        edittextnohptujuan.setText(null);
        edittextjlhpulsa.setText(null);
    }
}
