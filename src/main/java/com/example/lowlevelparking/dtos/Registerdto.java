package com.example.lowlevelparking.dtos;

public class Registerdto {
    public String username;
    public String email;
    public  String  password;
    public String contact;

    public Registerdto() {
    }

    @Override
    public String toString() {
        return "Registerdto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }

    public Registerdto(String username, String email, String password, String contact) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
