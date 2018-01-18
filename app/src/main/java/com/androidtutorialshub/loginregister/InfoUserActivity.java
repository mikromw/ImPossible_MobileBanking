package com.androidtutorialshub.loginregister;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoUserActivity extends AppCompatActivity implements View.OnClickListener{
    private String namaAnda1,emailAnda1,norekAnda1,nohpAnda1,saldoAnda1;
    private TextView namaAnda,emailAnda,norekAnda,nohpAnda,saldoAnda;
    private ImageView editprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
        initViews();
        initListeners();
        setUsers(this);
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {
        namaAnda=(TextView)findViewById(R.id.nama_Anda);
        emailAnda=(TextView)findViewById(R.id.email_Anda);
        norekAnda=(TextView)findViewById(R.id.norek_Anda);
        nohpAnda=(TextView)findViewById(R.id.nohp_Anda);
        saldoAnda=(TextView)findViewById(R.id.saldo_Anda);
        editprofile=(ImageView)findViewById(R.id.editprofile);
    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        editprofile.setOnClickListener(this);
    }
    //mengambil data nama,email,norekening,nohp dan saldo pada Session dan menampilkannya
    public void setUsers(Context context) {
        namaAnda1=SessionManager.with(context).getuserloggedin().getName();
        emailAnda1=SessionManager.with(context).getuserloggedin().getEmail();
        norekAnda1=SessionManager.with(context).getuserloggedin().getNoRek();
        nohpAnda1=SessionManager.with(context).getuserloggedin().getNohp();
        saldoAnda1=SessionManager.with(context).getuserloggedin().getSaldo();
        namaAnda.setText(namaAnda1);
        emailAnda.setText(emailAnda1);
        norekAnda.setText(norekAnda1);
        nohpAnda.setText(nohpAnda1);
        saldoAnda.setText(saldoAnda1);
    }
    /**
     * This implemented method is to listen the click on view
     *
     */
    public void onClick(View v){
        if(v.getId()==R.id.editprofile){
            Intent accountsIntent = new Intent(this, ProfileUserActivity.class);
            startActivity(accountsIntent);
        }
    }
    //jika user klik backpress maka akan pindah ke layout HomeUserActivity
    public void onBackPressed() {
        Intent accountsIntent = new Intent(this, HomeUserActivity.class);
        startActivity(accountsIntent);
    }
}
