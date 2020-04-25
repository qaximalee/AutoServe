package com.balti.autoserve.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.balti.autoserve.entities.User;
import com.balti.autoserve.enums.Roles;
import com.balti.autoserve.model.TestJSON;
import com.balti.autoserve.repositories.UserRepository;

@RestController
public class HomeResource {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passEncode;
	
	
	@GetMapping("/")
	public TestJSON home() {
		TestJSON test = new TestJSON(); 
		test.setHome("Home");
		return test;
	}
	
	@GetMapping("/user")
	public TestJSON[] user() {
		TestJSON[] sendData = new TestJSON[3];  
		TestJSON test1 = new TestJSON(); 
		test1.setHome("User1");
		TestJSON test2 = new TestJSON(); 
		test2.setHome("User2");
		TestJSON test3 = new TestJSON(); 
		test3.setHome("Home3");
		sendData[0] = test1;
		sendData[1] = test2;
		sendData[2] = test3;
		
		return sendData;
	}

	@GetMapping("/admin")
	public TestJSON admin() {		
		TestJSON test = new TestJSON(); 
		test.setHome("ADMIN");
		return test;
	}
	
	@PostMapping("/register/user")
	public User addUser(@RequestBody User user) {
		user.setRoles(Roles.ROLE_USER.name());
		user.setPassword(passEncode.encode(user.getPassword()));
		userRepo.save(user);
		return user;
	}
	
	@PostMapping("/admin/register/restaurant_admin")
	public User addRestaurantAdmin(@RequestBody User user) {
		user.setPassword(passEncode.encode(user.getPassword()));
		userRepo.save(user);
		return user;
	}
	
	@PostMapping("/rest_admin/register/counter_or_kitchen_admin")
	public User addCounterAdmin(@RequestBody User user) {
		user.setPassword(passEncode.encode(user.getPassword()));
		userRepo.save(user);
		return user;
	}
}
