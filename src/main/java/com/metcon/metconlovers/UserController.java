package com.metcon.metconlovers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.metcon.metconlovers.resetpassword.PasswordGenerator;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.metcon.metconlovers.resetpassword.PasswordGeneratorDict.ALPHA;
import static com.metcon.metconlovers.resetpassword.PasswordGeneratorDict.ALPHA_CAPS;
import static com.metcon.metconlovers.resetpassword.PasswordGeneratorDict.SPECIAL_CHARS;
import static com.metcon.metconlovers.security.SecurityConstants.HEADER_STRING;



@Controller
@RequestMapping(path="/")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserIdentifier identifier;

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
	
	//Rejestracja
	@PostMapping(path="/register",produces = MediaType.APPLICATION_JSON_VALUE,consumes="application/json")
	public @ResponseBody Map<String, String> register(@RequestBody MetconUser user) {
        Map<String, String> result = new HashMap<String, String>();
        if (userRepository.getUserByLogin(user.getLogin()) == null && userRepository.getUserByEmail(user.getEmail())== null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setType("player");
            userRepository.save(user);
            sendEmailWelcome(user);
            result.put("Status", "OK");
            return result;
        } else {
            result.put("Status", "FAILED");
            return result;
        }
	}



	@PostMapping(path="/identify")
    public @ResponseBody String identify(@RequestHeader(HEADER_STRING)String token)
    {
        Gson gson = new Gson();
        return gson.toJson(identifier.Identify(token));
    }

    @PostMapping(path="/reset_password")
    public @ResponseBody Map<String,String> resetPassword(@RequestBody MetconUser user){
        Map<String, String> result = new HashMap<String, String>();
        MetconUser n=userRepository.getUserByEmail(user.getEmail());
        if (n != null) {
            String newpassword=new PasswordGenerator().generatePassword(6,ALPHA_CAPS + ALPHA + SPECIAL_CHARS);
            n.setPassword(bCryptPasswordEncoder.encode(newpassword));
            userRepository.save(n);
            sendEmailReset(n,newpassword);
            result.put("Status", "OK");
            return result;
        } else {
            result.put("Status", "FAILED");
            return result;
        }
    }

    private void sendEmailReset(MetconUser user,String password) {

        Email email = null;

            email = EmailBuilder.startingBlank()
                    .from("MetconLovers", "metconlovers@gmail.com")
                    .to(user.getName()+" "+user.getSurname(), user.getEmail())
                    .withSubject("Haseło zresetowane")
                    .withPlainText(password)
                    .buildEmail();
            inhouseMailer().sendMail(email, true);

        }


    @Bean
    public Mailer inhouseMailer() {
        return MailerBuilder
                .withSMTPServer("smtp.gmail.com",587,"metconlovers@gmail.com","Dupa1234")
            .buildMailer();
    }

	private void sendEmailWelcome(MetconUser recipient) {

        Email email = null;
        try {
            email = EmailBuilder.startingBlank()
                    .from("MetconLovers", "metconlovers@gmail.com")
                    .to(recipient.getName()+" "+recipient.getSurname(), recipient.getEmail())
                    .withSubject("Witaj w serwisie MetconLovers")
                    .withHTMLText(new String(Files.readAllBytes(Paths.get("mail/email.html")), "UTF-8"))
                    .buildEmail();
            inhouseMailer().sendMail(email, true);
        } catch (IOException e) {
            e.printStackTrace();
        }


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