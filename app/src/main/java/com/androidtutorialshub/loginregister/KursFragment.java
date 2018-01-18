package com.androidtutorialshub.loginregister;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class KursFragment extends Fragment {
    private TextView saatini;
    public KursFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_kurs, container, false);
        String tanggal_sekarang = getCurrentDate();
        //inisialisasi view
        saatini=(TextView)_view.findViewById(R.id.datetime);
        saatini.setText("Time : "+tanggal_sekarang);
        return _view;
    }
    //mengambil waktu dan tanggal sekarang
    public String getCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int year, month, date, day, hour, minute, second;
        String hari="",jam="",menit="",detik="",tgl="",bln="";
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date = c.get(Calendar.DATE);
        day=c.get(Calendar.DAY_OF_WEEK);
        hour=c.get(Calendar.HOUR_OF_DAY);
        minute=c.get(Calendar.MINUTE);
        second=c.get(Calendar.SECOND);
        switch (day){
            case 1:
                hari="Minggu";
                break;
            case 2:
                hari="Senin";
                break;
            case 3:
                hari="Selasa";
                break;
            case 4:
                hari="Rabu";
                break;
            case 5:
                hari="Kamis";
                break;
            case 6:
                hari="Jumat";
                break;
            case 7:
                hari="Sabtu";
                break;
        }
        switch (month){
            case 0:
                bln="Jan";
                break;
            case 1:
                bln="Feb";
                break;
            case 2:
                bln="Mar";
                break;
            case 3:
                bln="Apr";
                break;
            case 4:
                bln="May";
                break;
            case 5:
                bln="Jun";
                break;
            case 6:
                bln="Jul";
                break;
            case 7:
                bln="Aug";
                break;
            case 8:
                bln="Sep";
                break;
            case 9:
                bln="Oct";
                break;
            case 10:
                bln="Nov";
                break;
            case 11:
                bln="Dec";
                break;
        }
        if(hour<10){
            jam="0"+hour;
        }
        else{
            jam=""+hour;
        }
        if(minute<10){
            menit="0"+minute;
        }
        else{
            menit=""+minute;
        }
        if(second<10){
            detik="0"+second;
        }
        else{
            detik=""+second;
        }
        if(date<10){
            tgl="0"+date;
        }
        else{
            tgl=""+date;
        }
        return jam+":"+menit+":"+detik+" "+hari+", "+tgl+" "+bln+" "+year;
    }

}
