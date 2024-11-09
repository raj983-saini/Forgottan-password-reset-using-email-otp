package com.example.lowlevelparking.Services;

import com.example.lowlevelparking.Entities.User;
import com.example.lowlevelparking.Repository.UserRepository;
import com.example.lowlevelparking.dtos.Registerdto;
import com.example.lowlevelparking.dtos.logindto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
@Autowired
    private  UserRepository userRepository;
    @Autowired
public AuthenticationManager authenticationManager;
    @Autowired
    public UserDetailsService userDetailsService;

@Autowired
private  PasswordEncoder passwordEncoder;

public void userRegister(Registerdto registerdto){
    if(userRepository.findByEmail(registerdto.getEmail()).isPresent()){
        throw  new RuntimeException("user is already exit");
    }
    User user = new User();
    user.setEmail(registerdto.getEmail());
    user.setPassword(passwordEncoder.encode(registerdto.getPassword()));
    user.setUsername(registerdto.getUsername());
    user.setContact(registerdto.getContact());
  //  user.setRole("User");

    userRepository.save(user);
}



}
