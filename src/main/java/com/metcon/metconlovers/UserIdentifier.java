package com.metcon.metconlovers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserIdentifier {

    @Autowired
    private UserRepository userRepository;

    public MetconUser Identify(String token){
        token = token.replaceFirst("Bearer ", "");
        DecodedJWT decodedtoken = JWT.decode(token);
        String username = decodedtoken.getSubject();
        MetconUser user = userRepository.getUserByLogin(username);
        return user;

    }

}
