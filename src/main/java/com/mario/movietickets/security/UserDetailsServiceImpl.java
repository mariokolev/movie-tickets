package com.mario.movietickets.security;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.mario.movietickets.entities.User;
import com.mario.movietickets.repositories.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserName(username);
		
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("Username[" + username + "] was not found.");
		}
		return new UserDetailsImpl(user.get());
	}
}
