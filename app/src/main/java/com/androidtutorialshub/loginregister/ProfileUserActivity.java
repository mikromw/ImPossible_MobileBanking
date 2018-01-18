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
import android.view.View;

public class ProfileUserActivity extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = ProfileUserActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutNama;
    private TextInputLayout textInputLayoutNoHP;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextNama;
    private TextInputEditText textInputEditTextNoHP;

    public String namaAnda1,emailAnda1,norekAnda1,nohpAnda1;
    private AppCompatButton appCompatButtonSave;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
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

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutNama = (TextInputLayout) findViewById(R.id.textInputLayoutNama);
        textInputLayoutNoHP = (TextInputLayout) findViewById(R.id.textInputLayoutNoHp);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextNama = (TextInputEditText) findViewById(R.id.textInputEditTextNama);
        textInputEditTextNoHP = (TextInputEditText) findViewById(R.id.textInputEditTextNoHP);

        appCompatButtonSave = (AppCompatButton) findViewById(R.id.appCompatButtonSave);

    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonSave.setOnClickListener(this);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
        progress = new ProgressDialog(activity);
        user=new User();
        progress = new ProgressDialog(this);
    }
    //mengambil data nama,email,norekening dan nohp pada Session dan menampilkannya
    public void setUsers(Context context) {
        namaAnda1=SessionManager.with(context).getuserloggedin().getName();
        emailAnda1=SessionManager.with(context).getuserloggedin().getEmail();
        norekAnda1=SessionManager.with(context).getuserloggedin().getNoRek();
        nohpAnda1=SessionManager.with(context).getuserloggedin().getNohp();
        textInputEditTextEmail.setText(emailAnda1);
        textInputEditTextNama.setText(namaAnda1);
        textInputEditTextNoHP.setText(nohpAnda1);
    }
    /**
     * This implemented method is to listen the click on view
     *
     */
    public void onClick(View v){
        if(v.getId()==R.id.appCompatButtonSave){
            String email=textInputEditTextEmail.getText().toString().trim();
            String nama=textInputEditTextNama.getText().toString().trim();
            String nohp=textInputEditTextNoHP.getText().toString().trim();
            if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(textInputEditTextNama, textInputLayoutNama,getString(R.string.error_message_name))) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(textInputEditTextNoHP, textInputLayoutNoHP,getString(R.string.error_message_nohp))) {
                return;
            }
            user.setNoRek(norekAnda1);
            user.setEmail(email);
            user.setName(nama);
            user.setNohp(nohp);
            databaseHelper.updateProfile(user);
            for (int i=0;i<User.users.size();i++) {
                if (User.users.get(i).norek.equals(norekAnda1)) {
                    user = User.users.get(i);
                    break;
                }
            }
            SessionManager sessionManager = SessionManager.with(this);
            sessionManager.updatesession(user);
            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_message_editprofile), Snackbar.LENGTH_LONG).show();
            progress.setMessage("Loading...");
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
    }
    private void masuk() {
        Intent accountsIntent = new Intent(this, InfoUserActivity.class);
        emptyInputEditText();
        startActivity(accountsIntent);
    }
    //jika user klik backpress maka akan pindah ke layout InfoUserActivity
    public void onBackPressed() {
        Intent accountsIntent = new Intent(this, InfoUserActivity.class);
        emptyInputEditText();
        startActivity(accountsIntent);
    }
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextNama.setText(null);
        textInputEditTextNoHP.setText(null);
    }
}
