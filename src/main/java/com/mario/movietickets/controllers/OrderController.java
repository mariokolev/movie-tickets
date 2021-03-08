package com.mario.movietickets.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mario.movietickets.entities.Movie;
import com.mario.movietickets.entities.Order;
import com.mario.movietickets.services.MovieService;
import com.mario.movietickets.services.OrderService;
import com.mario.movietickets.services.UserService;

@Controller
@RequestMapping(path = "/orders")
public class OrderController {

	private final OrderService orderService;
	
	private final MovieService movieService;
	
	private final UserService userService;
	
	@Autowired
	public OrderController(OrderService orderService, MovieService movieService, UserService userService) {

		this.orderService = orderService;
		this.movieService = movieService;
		this.userService = userService;
	}
	
	@GetMapping
	public String viewOrders(Model model) {
		model.addAttribute("orders", orderService.findAll());
		return viewPaginatedOrders(1, 10, model);
	}

	@GetMapping("/page/{page}/size/{size}")
	public String viewPaginatedOrders(@PathVariable("page") int page,@PathVariable("size") int size, Model model) {
		Page <Order> currentPage = orderService.findPaginated(page, size);
		List<Order> orders = currentPage.getContent();
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", currentPage.getTotalPages());
		model.addAttribute("totalItems", currentPage.getTotalElements());
		model.addAttribute("size", size);
		model.addAttribute("orders", orders);
		return "order/orders";
	}
	
	@GetMapping("/user/{id}")
	public String viewOrders(@PathVariable("id") Long id, Model model) {
		model.addAttribute("orders", orderService.findAllOrdersById(id));
		return "order/orders-user";
	}
	
	@PostMapping("/add")
	public String addOrder(@ModelAttribute("movie") Movie movie,
							@RequestParam("boughtTickets") Integer tickets){
		
		orderService.saveOrder(movie.getId(), tickets);
		return "order/order-success";
	}
}
