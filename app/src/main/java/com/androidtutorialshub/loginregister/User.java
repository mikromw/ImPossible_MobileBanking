package com.androidtutorialshub.loginregister;

import java.util.ArrayList;

public class User {
    public String username;
    public String name;
    public String email;
    public String norek;
    public String password;
    public String nohp;
    public String saldo;
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<String> paket=new ArrayList<>();
    public History history=new History();
    public User(){

    }
    public User(String username, String norek, String name, String email, String password, String nohp, String saldo) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.nohp = nohp;
        this.norek=norek;
        this.saldo=saldo;
    }

    public void pulsa(){
        paket.clear();
        paket.add("Pulsa Rp10000");
        paket.add("Pulsa Rp25000");
        paket.add("Pulsa Rp50000");
        paket.add("Pulsa Rp100000");
        paket.add("Pulsa Rp200000");
        paket.add("Pulsa Rp500000");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoRek() {
        return norek;
    }

    public void setNoRek(String norek) {
        this.norek = norek;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}
