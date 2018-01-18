package com.androidtutorialshub.loginregister;

/**
 * Created by hp on 11/24/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.androidtutorialshub.loginregister.R;

import java.util.ArrayList;
import java.util.List;

public class UserMBVPAdapter extends FragmentPagerAdapter {

    public UserMBVPAdapter(FragmentManager fm) {
        super(fm);
    }
    //deklarasi array list untuk fragment
    List<Fragment> fragments = new ArrayList<>();
    //deklarasi array list untuk title
    List<String> titles = new ArrayList<>();
    //menambahkan fragment dan title
    public void addfragment(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }
    //mengembalikan nilai posisi fragment
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    //mengembalikan banyak fragment
    @Override
    public int getCount() {
        return fragments.size();
    }
    //memberikan string title pada masing-masing fragment sesuai posisi
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}