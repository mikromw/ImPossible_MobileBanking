package com.androidtutorialshub.loginregister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.InputValidation;
import com.androidtutorialshub.loginregister.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextUsername;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private DBHelper dbHelper;
    private User user;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //menyembunyikan action bar
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();
        //untuk mengisi data user pada database dan menyimpan data history pada array history
        databaseHelper.Userutama();
        databaseHelper.UserArray();
        dbHelper.HistoryArray();
        user.pulsa();
        //jika sudah memiliki session login maka akan langsung masuk HomeUserActivity
        if (SessionManager.with(this).isuserlogin()) {
            Intent masuk=new Intent(this,HomeUserActivity.class);
            startActivity(masuk);
        }
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        textInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputLayoutUsername);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputEditTextUsername = (TextInputEditText) findViewById(R.id.textInputEditTextUsername);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);
    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        dbHelper = new DBHelper(activity);
        inputValidation = new InputValidation(activity);
        progress = new ProgressDialog(activity);
        user=new User();
    }
    /**
     * This implemented method is to listen the click on view
     *
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //jika klik button login akan melakukan proses validasi
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
        }
    }
    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite(){
        textInputLayoutUsername.setErrorEnabled(false);
        textInputLayoutPassword.setErrorEnabled(false);
        User user = new User();
        boolean _isnorekexist = false;

        for (int i=0;i<User.users.size();i++) {
            if (User.users.get(i).username.equals(textInputEditTextUsername.getText().toString())) {
                _isnorekexist = true;
                user = User.users.get(i);
                break;
            }
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextUsername, textInputLayoutUsername, getString(R.string.error_message_username))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword,getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextNoRekExist(textInputEditTextUsername, textInputLayoutUsername,_isnorekexist,getString(R.string.error_username_not_exists))){
            return;
        }
        if (databaseHelper.checkUsername(textInputEditTextUsername.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {

            SessionManager sessionManager = SessionManager.with(this);
            sessionManager.createsession(user);
            progress = new ProgressDialog(this);
            progress.setMessage("Loading...");
            progress.show();
            Thread _thread = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
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
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_username_pin), Snackbar.LENGTH_LONG).show();
        }
    }
    /**
     * This method is to empty all input edit text and go to HomeUserActivity
     */
    public void masuk(){
        Intent accountsIntent = new Intent(this, HomeUserActivity.class);
        emptyInputEditText();
        startActivity(accountsIntent);
    }
    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextUsername.setText(null);
        textInputEditTextPassword.setText(null);
    }
}