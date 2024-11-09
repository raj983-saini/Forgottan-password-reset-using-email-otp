package com.example.lowlevelparking.Entities;

import jakarta.persistence.*;

@Entity
public class OtpToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String otp;
    private Long createdAt;

    public OtpToken() {
    }

    public OtpToken(Long id, String email, String otp, Long createdAt) {
        this.id = id;
        this.email = email;
        this.otp = otp;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
// Getters and Setters
}
