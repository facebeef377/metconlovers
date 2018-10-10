package com.metcon.metconlovers.controlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metcon.metconlovers.UserRepository;
import com.metcon.metconlovers.emailer.Emailer;
import com.metcon.metconlovers.entities.MetconUser;
import com.metcon.metconlovers.resetpassword.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.metcon.metconlovers.resetpassword.PasswordGeneratorDict.*;
import static com.metcon.metconlovers.security.SecurityConstants.HEADER_STRING;


@Controller
@RequestMapping(path = "/")

public class UserController {


    @Autowired
    Emailer emailer;
    @Autowired
    UserIdentifier identifier;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    //Lista wszystkich uzytkownikow
    @PostMapping(path = "/getAllUser")
    public @ResponseBody
    Iterable<MetconUser> getAllUser() {
        return userRepository.findAll();
    }

    //Rejestracja
    @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json")
    public @ResponseBody
    Map<String, String> register(@RequestBody MetconUser user) {
        Map<String, String> result = new HashMap<String, String>();
        boolean loginTaken = !(userRepository.getUserByLogin(user.getLogin()) == null);
        boolean emailTaken = !(userRepository.getUserByEmail(user.getEmail()) == null);
        if (!loginTaken && !emailTaken) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setType("player");
            userRepository.save(user);
            emailer.sendEmailWelcome(user);
            result.put("Status", "OK");
            return result;
        } else {
            if (emailTaken)
                result.put("Status", "FAILED_EMAIL");
            else if (loginTaken)
                result.put("Status", "FAILED_LOGIN");
            return result;
        }
    }


    @PostMapping(path = "/identify")
    public @ResponseBody
    String identify(@RequestHeader(HEADER_STRING) String token) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(identifier.Identify(token));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "/update_user")
    public @ResponseBody
    Map<String, String> updateUser(@RequestBody MetconUser user) {
        if (user.getPassword().equals(null) && user.getPassword().equals(""))
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        else
            user.setPassword(userRepository.getUserByLogin(user.getLogin()).getPassword());
        userRepository.save(user);


        Map<String, String> result = new HashMap<String, String>();
        result.put("Status", "OK");
        return result;
    }

    @PostMapping(path = "/reset_password")
    public @ResponseBody
    Map<String, String> resetPassword(@RequestBody MetconUser user) {
        Map<String, String> result = new HashMap<String, String>();
        MetconUser n = userRepository.getUserByEmail(user.getEmail());
        if (n != null) {
            String newpassword = PasswordGenerator.generatePassword(6, ALPHA_CAPS + ALPHA + NUMERIC);
            n.setPassword(bCryptPasswordEncoder.encode(newpassword));
            userRepository.save(n);
            emailer.sendEmailReset(n, newpassword);
            result.put("Status", "OK");
            return result;
        } else {
            result.put("Status", "FAILED");
            return result;
        }
    }


    //Edycja uzytkownika
    @PostMapping(path = "/save_user")
    public @ResponseBody
    Map<String, String> save_user(@RequestBody MetconUser user) {
        Map<String, String> result = new HashMap<String, String>();
        userRepository.save(user);
        result.put("status", "OK");
        return result;
    }

    //Logowanie
    @PostMapping(path = "/getUserByID")
    public @ResponseBody
    Iterable<MetconUser> getUserByID(@RequestBody Map<String, Object> payload) {
        return userRepository.findAll();
    }

}