package com.metcon.metconlovers.controlers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.metcon.metconlovers.UserRepository;
import com.metcon.metconlovers.entities.MetconUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserIdentifier {

    @Autowired
    private UserRepository userRepository;

    public MetconUser Identify(String Token) {
        Token = Token.replaceFirst("Bearer ", "");
        DecodedJWT decodedtoken = JWT.decode(Token);
        String username = decodedtoken.getSubject();


        return userRepository.getUserByLogin(username);

    }

}
