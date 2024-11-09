package com.example.lowlevelparking.dtos;

public class passwordreset {
    private  String email;
    private String otp;
private String newPassword;

    @Override
    public String toString() {
        return "passwordreset{" +
                "email='" + email + '\'' +
                ", otp='" + otp + '\'' +
                ", resetPassword='" + newPassword + '\'' +
                '}';
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

    public passwordreset(String email, String otp, String resetPassword) {
        this.email = email;
        this.otp = otp;
        this.newPassword = resetPassword;
    }

    public passwordreset() {
    }





    public String getnewPassword() {
        return newPassword;
    }

    public void setResetPassword(String resetPassword) {
        this.newPassword = resetPassword;
    }
}
