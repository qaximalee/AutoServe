package com.balti.autoserve.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.balti.autoserve.entities.User;
import com.balti.autoserve.enums.Gender;
import com.balti.autoserve.exceptions.UserException;
import com.balti.autoserve.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping("/checkUsernameExists")
	public ResponseEntity<Map<String, String>> isUserNameExists(@RequestParam String userName){
		Map<String, String> exist = new HashMap<>();
		if(userService.isUsernameExists(userName)) { 
			exist.put("exist", "true");
			return new ResponseEntity<Map<String, String>>(exist,null, HttpStatus.BAD_REQUEST);
		}else {
			exist.put("exist", "false");
			return new ResponseEntity<Map<String, String>>(exist,null, HttpStatus.OK);
		}
	}
	
	@GetMapping("/checkEmailExists")
	public ResponseEntity<Map<String, String>> isEmailExists(@RequestParam String email){
		Map<String, String> exist = new HashMap<>();
		if(userService.isEmailExists(email)) { 
			exist.put("exist", "true");
			return new ResponseEntity<Map<String, String>>(exist,null, HttpStatus.BAD_REQUEST);
		}else {
			exist.put("exist", "false");
			return new ResponseEntity<Map<String, String>>(exist,null, HttpStatus.OK);
		}
	}
	
	@PostMapping("/rest_admin/all-users/pageable")
	public List<User> getAllUserByRoles(
			@RequestParam String roles, 
			@RequestParam Integer pageNo, 
			@RequestParam Integer pageSize, 
			@RequestParam String sortBy){
		try {
			return userService.getAllUsers(roles, pageNo, pageSize, sortBy);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	@PostMapping("/user/get/userByFilter")
	public User getUserByFilters(
			@RequestParam Long id, 
			@RequestParam String userName,
			@RequestParam String email,
			@RequestParam String roles,
			@RequestParam boolean active
			) {
		try {
			return userService.getUserByFilter(id, userName, email, roles, active);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping("/user/add/user")
	public ResponseEntity<User> addUser(
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String userName,
			@RequestParam String email,
			@RequestParam String password,
			@RequestParam boolean active,
			@RequestParam String roles,
			@RequestParam String gender
			) {
		
		User user = new User();
		user.setUserName(userName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setActive(active);
		user.setRoles(roles);
		if(gender.equalsIgnoreCase("male"))
			user.setGender(Gender.MALE);
		else
			user.setGender(Gender.FEMAL);
		
		try {
			User addedUser = userService.addUser(user);
			return new ResponseEntity<User>(addedUser, null, HttpStatus.CREATED);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping("/user/update/user")
	public User updateUser(
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String userName,
			@RequestParam String email,
			@RequestParam String password,
			@RequestParam boolean active,
			@RequestParam String roles,
			@RequestParam String gender
			) {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setActive(active);
		user.setEmail(email);
		user.setUserName(userName);
		user.setPassword(password);
		user.setRoles(roles);
		
		if(gender.equalsIgnoreCase(Gender.MALE.name()))
			user.setGender(Gender.MALE);
		else
			user.setGender(Gender.FEMAL);
		
		try {
			return userService.updateUser(user);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/user/delete/user")
	public void deleteUser(
			@RequestParam Long id
			){
		try {
			userService.deleteUser(id);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
