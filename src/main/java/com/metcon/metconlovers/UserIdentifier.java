package com.metcon.metconlovers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;

public class UserIdentifier {

    @Autowired
    private UserRepository userRepository;

    public MetconUser Identify(String token){

        DecodedJWT decodedtoken = JWT.decode("Bearer "+token);
        String username = decodedtoken.getSubject();
        MetconUser user = userRepository.findByLogin(username);
        return user;

    }

}
