package com.androidtutorialshub.loginregister;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.UserMBVPAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserMBVPFragment extends Fragment {

    private ViewPager vp;
    private TabLayout tablayout;
    private UserMBVPAdapter adapter;

    public UserMBVPFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_user_mbvp, container, false);
        init(_view);
        setting();
        return _view;
    }
    //inisialisasi view
    private void init(View view) {
        vp = (ViewPager) view.findViewById(R.id.uservpcontainer_viewpager);
        tablayout = (TabLayout) view.findViewById(R.id.uservpcontainer_tablayout);
        adapter = new UserMBVPAdapter(getFragmentManager());
    }
    //menambah fragment
    private void setting() {
        adapter.addfragment(new HomeFragment(), "Home");
        adapter.addfragment(new KursFragment(), "Kurs");
        adapter.addfragment(new TransferFragment(), "Transaction");
        adapter.addfragment(new MoreFragment(), "More");
        vp.setAdapter(adapter);
        tablayout.setupWithViewPager(vp);
    }
}