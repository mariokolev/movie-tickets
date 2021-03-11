package com.mario.movietickets.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
	
	@PostMapping(value ="/add", produces = "application/json")
	@ResponseBody
	public String addOrder(@RequestBody String json) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(json);

		orderService.saveOrder(jsonNode.get("movieId").asLong(), jsonNode.get("tickets").asInt());

		return movieService.findById(jsonNode.get("movieId").asLong()).getAvailableTickets().toString();
	}
}
