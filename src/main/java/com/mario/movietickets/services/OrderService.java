package com.mario.movietickets.services;

import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.mario.movietickets.entities.Movie;
import com.mario.movietickets.entities.Order;
import com.mario.movietickets.entities.User;
import com.mario.movietickets.repositories.MovieRepository;
import com.mario.movietickets.repositories.OrderRepository;
import com.mario.movietickets.repositories.UserRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	
	private final MovieRepository movieRepository;

	private final UserRepository userRepository;
	
	@Autowired
	public OrderService(OrderRepository orderRepository, MovieRepository movieRepository, UserRepository userRepository) {
		this.orderRepository = orderRepository;
		this.movieRepository = movieRepository;
		this.userRepository = userRepository;
	}
	
	public Set<Order> findAllOrdersById(Long id) {
		return orderRepository.findAllByUserId(id);
	}
	
	public List<Order> findAll(){
		return orderRepository.findAllByOrderByDateOfOrderDesc();
	}
	
	public Page<Order> findPaginated(int page, int size){
		Pageable pageable = PageRequest.of(page - 1, size);
		return this.orderRepository.findAll(pageable);
	}
	
	@Transactional
	public void saveOrder(Long movieId, Integer tickets) {
		// Retrieve logged in user
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		
		System.out.println(username);

		Order order = new Order();
		Movie movieOptional = movieRepository.findById(movieId).get();
		
		if(tickets.intValue() > movieOptional.getAvailableTickets().intValue()) {
			throw new IllegalStateException("Not enough tickets");
		}
		
		User user = userRepository.findByUserName(username).get();
		movieOptional.setAvailableTickets(movieOptional.getAvailableTickets() - tickets);
		order.setMovie(movieOptional);
		order.setBoughtTickets(tickets);
		order.setUser(user);
		
		orderRepository.save(order);
	}
}
