package com.mario.movietickets.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.mario.movietickets.entities.Order;
import com.mario.movietickets.repositories.MovieRepository;
import com.mario.movietickets.repositories.OrderRepository;
import com.mario.movietickets.repositories.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class OrderRepositoryTests {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Test
	public void saveOrderTest() {
		Order order = new Order();
		order.setMovie(movieRepository.findById(2L).get());
		order.setUser(userRepository.findById(1L).get());
		order.setBoughtTickets(2);
		
		orderRepository.save(order);
	}
}
