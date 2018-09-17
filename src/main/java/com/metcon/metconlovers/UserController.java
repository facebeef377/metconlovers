package com.metcon.metconlovers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metcon.metconlovers.User;
import com.metcon.metconlovers.UserRepository;

@Controller
@RequestMapping(path="/")
public class UserController {
	
	@Autowired
	private UserRepository users;
	
	//Lista wszystkich uzytkownikow
	@PostMapping(path="/getAllUser")
	public @ResponseBody Iterable<User> getAllUser() {
		return users.findAll();
	}
	
	//Logowanie
	@PostMapping(path="/login")
	public @ResponseBody Map<String, Object> login(@RequestBody Map<String,Object> payload) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "OK");
		return result;
	}
	
	//Logowanie
	@PostMapping(path="/register")
	public @ResponseBody Map<String, Object> register(@RequestBody User user) {
		Map<String, Object> result = new HashMap<String, Object>();
		users.save(user);
		result.put("status", "OK");
		return result;
	}
	
	//Edycja uzytkownika
	@PostMapping(path="/save_user")
	public @ResponseBody Map<String, String> save_user(@RequestBody User user) {
		Map<String, String> result = new HashMap<String, String>();
		users.save(user);
		result.put("status", "OK");
		return result;
	}
	
	//Logowanie
	@PostMapping(path="/getUserByID")
	public @ResponseBody Iterable<User> getUserByID(@RequestBody Map<String,Object> payload) {
		return users.findAll();
	}

}