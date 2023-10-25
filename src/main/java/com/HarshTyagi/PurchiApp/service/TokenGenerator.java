package com.HarshTyagi.PurchiApp.service;





import com.HarshTyagi.PurchiApp.domain.User;

import java.util.Map;

public interface TokenGenerator {
    public Map<String,String> generateToken(User user);

}
