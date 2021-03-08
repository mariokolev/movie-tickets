package com.mario.movietickets.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.mario.movietickets.entities.Role;
import com.mario.movietickets.entities.User;
import com.mario.movietickets.repositories.RoleRepository;
import com.mario.movietickets.repositories.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;
	
	private final RoleRepository roleRepository;
	
	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}
	
	public void saveUser(User user) {
		Optional<User> userOptional = userRepository.findByUserName(user.getUsername());
		
		if(userOptional.isPresent()) {
			throw new IllegalArgumentException("user with username[" + user.getUsername() + "] already exists.");
		}
		
		if(user.getRoles().size() == 0) {
			user.getRoles().add(roleRepository.findByRoleName("USER").get());
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		userRepository.save(user);			
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public Page<User> findPaginated(int page, int size){
		Pageable pageable = PageRequest.of(page - 1, size);
		return this.userRepository.findAll(pageable);
	}
	
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "user with id[" + id + "] does not exists."
        ));
	}
	
	@Transactional
	public void updateUser(Long id, String username, String password, Boolean enabled, Role role) {
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "user with id[" + id + "] does not exists."
        ));
		
		if(username != null &&
                username.length() > 0 &&
                !Objects.equals(user.getUsername(), username)) {
			user.setUsername(username);
		}
		
		password = passwordEncoder.encode(password);
		
		if(password != null &&
				password.length() > 0 &&
				!Objects.equals(user.getPassword(), password)) {
			user.setPassword(password);
		}
	}
}
