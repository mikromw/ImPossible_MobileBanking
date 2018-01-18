package com.androidtutorialshub.loginregister;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private TextView welcome;
    private String namaAnda1, username1;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_home, container, false);
        //inisialisasi view
        welcome=(TextView)_view.findViewById(R.id.welcome);
        setUsers(getContext());
        welcome.setText(namaAnda1+","+username1+"\nSelamat datang di");
        /* initiate & instantiate */
        return _view;
    }
    //mengambil data nama dan norekening pada Session
    public void setUsers(Context context) {
        namaAnda1=SessionManager.with(context).getuserloggedin().getName();
        username1=SessionManager.with(context).getuserloggedin().getUsername();
    }
}
