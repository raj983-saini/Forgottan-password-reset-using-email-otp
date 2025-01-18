package com.example.lowlevelparking.Repository;


import com.example.lowlevelparking.Entities.OtpToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OtpTokenRepository extends CrudRepository<OtpToken, Long> {
     OtpToken findByEmail(String email);
     OtpToken findByOtp(String otp);
}
