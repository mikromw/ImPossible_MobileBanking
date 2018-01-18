package com.androidtutorialshub.loginregister;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by hp on 11/30/2017.
 */

public class History {
    public String kode;
    public String norekasal;
    public String norektujuan;
    public String jumlah;
    public String DateTime;
    public String jenis;
    public static ArrayList<History> historys = new ArrayList<>();
    public static ArrayList<History> historyfilter = new ArrayList<>();
    public History(){

    }
    //mengembalikan string kode
    public String kodetransaksi(String kodeasli, String kodenya){
        int banyaknol=3-kodenya.length();

        for(int i=0;i<banyaknol;i++){
            kodeasli+="0";
        }
        kodeasli+=kodenya;
        return kodeasli;
    }
    public History(String kode, String norekasal, String norektujuan, String jumlah) {
        this.kode=kode;
        this.norekasal = norekasal;
        this.norektujuan = norektujuan;
        this.jumlah = jumlah;
    }
    //melakukan filter terlama
    public void filterold(String noreknya){
        historyfilter.clear();
        for(int i=0;i<historys.size();i++){
            if(noreknya.equals(historys.get(i).norekasal)){
                historys.get(i).jenis="Credit";
                historyfilter.add(historys.get(i));
            }
            else if(noreknya.equals(historys.get(i).norektujuan)){
                historys.get(i).jenis="Debit";
                historyfilter.add(historys.get(i));
            }
        }
    }
    //melakukan filter terbaru
    public void filternew(String noreknya){
        historyfilter.clear();
        for(int i=historys.size()-1;i>=0;i--){
            if(noreknya.equals(historys.get(i).norekasal)){
                historys.get(i).jenis="Credit";
                historyfilter.add(historys.get(i));
            }
            else if(noreknya.equals(historys.get(i).norektujuan)){
                historys.get(i).jenis="Debit";
                historyfilter.add(historys.get(i));
            }
        }
    }
    //melakukan penyimpanan nilai waktu dan tanggal sekarang pada DateTime
    public void getCurrentDate(){
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
                hari="Sun";
                break;
            case 2:
                hari="Mon";
                break;
            case 3:
                hari="Tue";
                break;
            case 4:
                hari="Wed";
                break;
            case 5:
                hari="Thu";
                break;
            case 6:
                hari="Fri";
                break;
            case 7:
                hari="Sat";
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
        DateTime= hari+", "+tgl+" "+bln+" "+year+"\n"+jam+":"+menit+":"+detik;
    }
    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNoRekAsal() {
        return norekasal;
    }

    public void setNoRekAsal(String norekasal) {
        this.norekasal = norekasal;
    }

    public String getNoRekTujuan() {
        return norektujuan;
    }

    public void setNoRekTujuan(String norektujuan) {
        this.norektujuan = norektujuan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String DateTime) {
        this.DateTime = DateTime;
    }
}
