package com.example.lowlevelparking.dtos;

public class logindto {
    private String email;
    private String password;
    private boolean remember;

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public logindto(String email, String password, boolean remember) {
        this.email = email;
        this.password = password;
        this.remember = remember;
    }



    public logindto() {
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
