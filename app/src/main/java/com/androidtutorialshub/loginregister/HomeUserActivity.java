package com.androidtutorialshub.loginregister;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class HomeUserActivity extends AppCompatActivity {
    ImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);
        running();
    }
    public void running(){
        //menggunakan layout yang dicustom sendiri
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        //inisialisasi view
        profile = (ImageView) findViewById(R.id.actionbar_setting);
        //jika sudah memiliki session login maka akan langsung masuk UserMBVPFragment
        if(SessionManager.with(getApplicationContext()).isuserlogin()){
            this.changefragment(new UserMBVPFragment());
        }
        //jika klik profile maka akan masuk ke InfoUserActivity
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile=new Intent(getApplicationContext(),InfoUserActivity.class);
                startActivity(profile);
            }
        });
    }
    //untuk mengganti fragment yang satu dengan fragment yang lain
    public void changefragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_home, fragment).commit();
    }
    public void onBackPressed() {
        SessionManager.with(getApplicationContext()).clearsession();
        Intent keluar=new Intent(getApplicationContext(),LoginActivity.class);
        keluar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(keluar);
    }
    /*
    //untuk mencegah aplikasi keluar saat pengguna tidak sengaja mengklik backpress dengan memberikan alert dialog apakah user
    //logout atau tidak

    public void onBackPressed() {
        new AlertDialog.Builder(this)
        .setMessage("Apakah Anda ingin Logout?")
        .setCancelable(false)
        .setNegativeButton("No", null)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SessionManager.with(getApplicationContext()).clearsession();
                Intent keluar=new Intent(getApplicationContext(),LoginActivity.class);
                keluar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(keluar);
            }
        })
        .show();
    }
    */
}
