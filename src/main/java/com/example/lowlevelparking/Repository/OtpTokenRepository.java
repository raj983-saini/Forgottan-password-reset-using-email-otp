package com.example.lowlevelparking.Repository;


import com.example.lowlevelparking.Entities.OtpToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OtpTokenRepository extends CrudRepository<OtpToken, Long> {
    public List<OtpToken> findByEmail(String email);
}
