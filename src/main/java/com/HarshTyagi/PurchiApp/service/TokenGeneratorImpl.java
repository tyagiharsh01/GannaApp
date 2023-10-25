package com.HarshTyagi.PurchiApp.service;


import com.HarshTyagi.PurchiApp.Repository.UserRepo;
import com.HarshTyagi.PurchiApp.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class TokenGeneratorImpl implements TokenGenerator{
    @Autowired
    private UserRepo userRepo;
    @Override
    public Map<String, String> generateToken(User user) {
        user.setFirstName(userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword()).get().getFirstName());
        Map<String,String> result = new HashMap<>();
        Map<String,Object> userData = new HashMap<>();
        userData.put("firstName",user.getFirstName());
        userData.put("email",user.getEmail());
        userData.put("password",user.getPassword());

        String token = Jwts.builder()
                .setClaims(userData)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"This is the secret key for GannaApp")
                .compact();

        result.put("firstName",user.getFirstName());
        result.put("email", user.getEmail());
        result.put("token",token);
        result.put("Massage","Successfully Log in...");
       return result;
    }
}
