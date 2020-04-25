package com.balti.autoserve.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.balti.autoserve.entities.User;
import com.balti.autoserve.model.CustomUserDetails;
import com.balti.autoserve.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Optional<User> user = userRepository.findUserByUserName(username);
		
		user.orElseThrow(()-> new UsernameNotFoundException(username+" not found"));
		return user.map(CustomUserDetails::new).get();
	}
	
}
