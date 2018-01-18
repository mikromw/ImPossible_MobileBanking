package com.androidtutorialshub.loginregister;

/**
 * Created by hp on 12/9/2017.
 */

public class Transfer {
    public User user;
    public History history;
    public Transfer(){
        user=new User();
        history=new History();
    }
    //mengembalikan nilai saldo akhir pengirim
    public Integer saldoakhirpengirim(String saldoAnda1,String jlhdana){
        Integer jumlahdana=Integer.parseInt(jlhdana);
        String dana=saldoAnda1.substring(2,saldoAnda1.length());
        String danaasli=dana.replace(".","");
        Integer saldoasli=Integer.parseInt(danaasli);
        return biayaadmin(saldoasli-jumlahdana);
    }
    //mengembalikan nilai saldo akhir penerima
    public Integer saldoakhirpenerima(String saldoAnda1,String jlhdana){
        Integer jumlahdana=Integer.parseInt(jlhdana);
        String dana=saldoAnda1.substring(2,saldoAnda1.length());
        String danaasli=dana.replace(".","");
        Integer saldoasli=Integer.parseInt(danaasli);
        return saldoasli+jumlahdana;
    }
    //mengkonversi string menjadi integer
    public Integer konversiangka(String angka){
        String dana=angka.substring(2,angka.length());
        String danaasli=dana.replace(".","");
        return Integer.parseInt(danaasli);
    }
    //mengkonversi string berupa angka menjadi string berupa mata uang dan angka
    public String konversiuang(String saldoakhir){
        char sisasaldopengirim[]=saldoakhir.toCharArray();
        String saldoefektif="";
        int j=0;
        for(int i=saldoakhir.length()-1;i>=0;i--){
            if(j%3==0 && j!=0){
                saldoefektif=sisasaldopengirim[i]+"."+saldoefektif;
            }
            else{
                saldoefektif=sisasaldopengirim[i]+saldoefektif;
            }
            j++;
        }
        return "Rp"+saldoefektif;
    }
    //mengembalikan no rekening dan saldo pada user
    public User transaksi(String norekAnda1, Integer saldotransfer){
        user.setNoRek(norekAnda1);
        String saldoakhir=saldotransfer.toString();
        user.setSaldo(konversiuang(saldoakhir));
        return user;
    }
    //mengembalikan nilai biaya admin tergantung jumlah saldo setelah transaksi
    public Integer biayaadmin(Integer saldoakhir){
        if (saldoakhir>=10000000) {
            return saldoakhir;
        }
        return saldoakhir-5000;
    }
    //mengembalikan nilai benar atau salah tergantung jumlah saldo setelah transaksi
    public boolean Saldomin_bebastransfer(String saldoAnda1,String jlhdana){
        String dana=saldoAnda1.substring(2,saldoAnda1.length());
        String danaasli=dana.replace(".","");
        Integer saldoasli=Integer.parseInt(danaasli);
        Integer jumlahdana=Integer.parseInt(jlhdana);
        if (saldoasli-jumlahdana>=10000000) {
            return true;
        }
        return false;
    }
    //mengembalikan nilai benar atau salah tergantung jumlah transaksi yang telah dilakukan 1 hari
    public boolean BatasTransfer(String norekasal, String datetime, String jlhdana){
        Integer jlhtransfer=0;
        String date=datetime.substring(0,15);
        for(int i=0;i<history.historys.size();i++){
            if(norekasal.equals(history.historys.get(i).norekasal)){
                if(date.equals(history.historys.get(i).DateTime.substring(0,15))){
                    jlhtransfer+=konversiangka(history.historys.get(i).jumlah);
                }
            }
        }
        Integer jumlahdana=Integer.parseInt(jlhdana);
        if(jlhtransfer+jumlahdana<=100000000){
            return true;
        }
        return false;
    }
}
