package com.androidtutorialshub.loginregister;

import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;


public class SecurityActivity extends AppCompatActivity implements View.OnClickListener{
    private TextInputEditText edittextnopinlama,edittextnopinbaru;
    private TextInputLayout layoutnopinlama,layoutnopinbaru;
    private AppCompatButton submit;
    private User user;
    public String norekAnda1,passAnda1;
    private NestedScrollView nestedScrollView;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    ProgressDialog progress;
    public SecurityActivity() {
        // Required empty public constructor
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
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
        nestedScrollView = (NestedScrollView)findViewById(R.id.nestedScrollView);
        edittextnopinlama=(TextInputEditText)findViewById(R.id.textInputEditTextNoPINlama);
        layoutnopinlama=(TextInputLayout)findViewById(R.id.textInputLayoutNoPINlama);
        edittextnopinbaru=(TextInputEditText)findViewById(R.id.textInputEditTextNoPINbaru);
        layoutnopinbaru=(TextInputLayout)findViewById(R.id.textInputLayoutNoPINbaru);
        submit=(AppCompatButton) findViewById(R.id.appCompatButtonSubmit);
    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        submit.setOnClickListener(this);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
        inputValidation = new InputValidation(this);
        progress = new ProgressDialog(this);
        user=new User();
    }
    //mengambil data norekening dan PIN pada Session
    public void setUsers(Context context) {
        norekAnda1=SessionManager.with(context).getuserloggedin().getNoRek();
        passAnda1=SessionManager.with(context).getuserloggedin().getPassword();
    }
    /**
     * This implemented method is to listen the click on view
     *
     */
    public void onClick(View v){
        if(v.getId()==R.id.appCompatButtonSubmit){
            String edittextnopinlama1=edittextnopinlama.getText().toString().trim();
            String edittextnopinbaru1=edittextnopinbaru.getText().toString().trim();
            if (!inputValidation.isInputEditTextFilled(edittextnopinlama, layoutnopinlama, getString(R.string.error_message_oldpasswordkosong))) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(edittextnopinbaru, layoutnopinbaru, getString(R.string.error_message_newpasswordkosong))) {
                return;
            }
            if (!inputValidation.isInputEditTextPIN(edittextnopinlama, layoutnopinlama, getString(R.string.error_message_digitpin))) {
                return;
            }
            if (!inputValidation.isInputEditTextPIN(edittextnopinbaru, layoutnopinbaru, getString(R.string.error_message_digitpin))) {
                return;
            }
            if (!inputValidation.isInputEditTextDifferentPIN(edittextnopinlama,edittextnopinbaru, layoutnopinbaru,getString(R.string.error_message_newpin_oldpin))) {
                return;
            }
            if (databaseHelper.checkNoRek(norekAnda1,edittextnopinlama1)) {
                user.setNoRek(norekAnda1);
                user.setPassword(edittextnopinbaru1);
                databaseHelper.updatePIN(user);
                for (int i=0;i<User.users.size();i++) {
                    if (User.users.get(i).norek.equals(norekAnda1)) {
                        user = User.users.get(i);
                        break;
                    }
                }
                SessionManager sessionManager = SessionManager.with(this);
                sessionManager.updatesession(user);

                Snackbar.make(nestedScrollView, getString(R.string.success_message_pin), Snackbar.LENGTH_LONG).show();
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
            else{
                Snackbar.make(nestedScrollView, getString(R.string.error_message_oldpinincorrect), Snackbar.LENGTH_LONG).show();
            }
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
        edittextnopinlama.setText(null);
        edittextnopinbaru.setText(null);
    }
}
