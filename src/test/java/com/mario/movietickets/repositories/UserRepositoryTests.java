package com.mario.movietickets.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.mario.movietickets.entities.User;
import com.mario.movietickets.repositories.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void findByUserNameTest() {
		Optional<User> userOptional = userRepository.findByUserName("user1");
		assertThat(userOptional.get()).isNotNull();
	}
	
	@Test
	public void saveUserTest() {
		User user = new User();
		user.setUsername("user2");
		user.setPassword("$2a$10$qalK5BD99Hmej6nSR.dO.O//E9l7dozdl63BIVzgCZwCs9Z08XYb6");
		userRepository.save(user);
	}
	
	@Test
	public void findAllTest() {
		List<User> users = userRepository.findAll();
		assertThat(users).isNotEmpty();
	}
}
