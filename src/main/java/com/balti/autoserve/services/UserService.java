package com.balti.autoserve.services;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.balti.autoserve.constants.ExceptionConstants;
import com.balti.autoserve.entities.User;
import com.balti.autoserve.exceptions.UserException;
import com.balti.autoserve.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private PasswordEncoder passEncode;
	
	@Autowired
	private UserRepository userRepo;
	
	/**
	 * Check whether the Username exists or not
	 * */
	public boolean isUsernameExists(String userName) {
		if(userRepo.existsByUserName(userName))
			return true;
		else
			return false;
	}
	
	/**
	 * Check whether the Email exists or not
	 * */
	public boolean isEmailExists(String email) {
		if(userRepo.existsByEmail(email))
			return true;
		else
			return false;
	}
	
	/**
	 * Only For Admin 
	 * This method will return all Users by their Roles
	 * @param String roles
	 * @param boolean active
	 * @param Integer pageNo
	 * @param Integer pageSize
	 * @param String sortBy
	 * 
	 * @return List<User>
	 * @throws UserException 
	 * */
	public List<User> getAllUsers(String roles, Integer pageNo, Integer pageSize, String sortBy) throws UserException{
		Pageable paging = null;
		if(sortBy.equals(""))
			paging = PageRequest.of(pageNo, pageSize);
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		
		Page<User> pagedResult = userRepo.findByRoles(roles, paging); 
		
		if(pagedResult.hasContent()) {
			return pagedResult.getContent();
		}else {
			throw new UserException(ExceptionConstants.USER_NOT_FOUND);
		}
	}
	
	/**
	 * For All
	 * This method will return user by there id, userName or email
	 * @param Long id
	 * @param String userName
	 * @param String email
	 * 
	 * @return User
	 * @throws UserException 
	 * */
	public User getUserByFilter(Long id, String userName, String email, String roles, boolean active) throws UserException{
		Optional<User> user = null;
		if(id != null) {
			user = userRepo.findById(id);
			user.orElseThrow(()-> new UserException(ExceptionConstants.USER_NOT_FOUND + "ID: " + id));
		}else if(null != userName || !userName.equals("")) {
			user = userRepo.findUserByUserName(userName);
			user.orElseThrow(()-> new UserException(ExceptionConstants.USER_NOT_FOUND + "Username: " + userName));
		}else if(null != email || !email.equals("")) {
			user = userRepo.findUserByEmail(email);
			user.orElseThrow(()-> new UserException(ExceptionConstants.USER_NOT_FOUND + "Email: " + email));
		}
		
		if(user.get().isActive()) {
			if(!user.get().getRoles().equals(roles)) 
				throw new UserException(ExceptionConstants.USER_ROLES_NOT_FOUND);
			else
				return user.get();
		}else {
			throw new UserException(ExceptionConstants.USER_IS_NOT_ACTIVE);
		}
	}
	
	public User addUser(User user) throws UserException {
		if(userRepo.existsById(user.getId()))
			throw new UserException(ExceptionConstants.USER_ALREADY_EXIST + "ID: " + user.getId());
		else {
			user.setPassword(passEncode.encode(user.getPassword()));
			return userRepo.save(user);
		}
	}
	
	public User updateUser(User user) throws UserException {
		if(userRepo.findById(user.getId()).isPresent()) {
			User updateUser = userRepo.findById(user.getId()).get();
			
			updateUser.setUserName(user.getUserName());
			updateUser.setPassword(passEncode.encode(user.getPassword()));
			updateUser.setFirstName(user.getFirstName());
			updateUser.setLastName(user.getLastName());
			updateUser.setEmail(user.getEmail());
			updateUser.setActive(user.isActive());
			updateUser.setGender(user.getGender());
			updateUser.setRoles(user.getRoles());
			
			return userRepo.save(updateUser);
		}else {
			throw new UserException(ExceptionConstants.USER_NOT_FOUND);
		}
	}
	
	public void deleteUser(Long userId) throws UserException {
		if(userRepo.findById(userId).isPresent()) {
			userRepo.deleteById(userId);
		}else {
			throw new UserException(ExceptionConstants.USER_NOT_FOUND + "ID: " + userId);
		}				
	}
}
