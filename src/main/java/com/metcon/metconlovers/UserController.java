package com.metcon.metconlovers;

import java.util.HashMap;
import java.util.Map;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.simplejavamail.mailer.MailerBuilder.buildMailer;

@Controller
@RequestMapping(path="/")
public class UserController {
	
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
	@PostMapping(path="/getAllUser")
	public @ResponseBody Iterable<MetconUser> getAllUser() {
		return userRepository.findAll();
	}
	
	//Logowanie
	@PostMapping(path="/login")
	public @ResponseBody Map<String, Object> login(@RequestBody Map<String,Object> payload) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "OK");
		return result;
	}
	
	//Logowanie
	@PostMapping(path="/register",produces = MediaType.APPLICATION_JSON_VALUE,consumes="application/json")
	public @ResponseBody Map<String, String> register(@RequestBody MetconUser user) {
        Map<String, String> result = new HashMap<String, String>();
        MetconUser n =userRepository.findByLogin(user.getLogin());
        if (n == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setType("player");
            userRepository.save(user);
            sendEmail(user);
            result.put("Status", "OK");
            return result;
        } else {
            result.put("Status", "FAILED");
            return result;
        }
	}
    @Bean
    public Mailer inhouseMailer() {
        return MailerBuilder
                .withSMTPServer("smtp.gmail.com",587,"metconlovers@gmail.com","Dupa1234")
            .buildMailer();
    }

	private void sendEmail(MetconUser recipient) {
        Email email = EmailBuilder.startingBlank()
                .from("MetconLovers", "metconlovers@gmail.com")
                .to(recipient.getName()+" "+recipient.getSurname(), recipient.getEmail())
                .withSubject("Witaj w serwisie MetconLovers")
                .withPlainText("ELo")
                .buildEmail();
        inhouseMailer().sendMail(email);

    }

	//Edycja uzytkownika
	@PostMapping(path="/save_user")
	public @ResponseBody Map<String, String> save_user(@RequestBody MetconUser user) {
		Map<String, String> result = new HashMap<String, String>();
		userRepository.save(user);
		result.put("status", "OK");
		return result;
	}
	
	//Logowanie
	@PostMapping(path="/getUserByID")
	public @ResponseBody Iterable<MetconUser> getUserByID(@RequestBody Map<String,Object> payload) {
		return userRepository.findAll();
	}

}