package com.mario.movietickets.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mario.movietickets.entities.User;
import com.mario.movietickets.services.UserService;


@Controller
@RequestMapping(path = "/users")
public class UserController {
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/create")
	public String viewRegisterPage(Model model) {
		model.addAttribute("user", new User());
		return "user/user-create";
	}

	@PostMapping("/create")
	public String createUser(@ModelAttribute("user") User user) {
		userService.saveUser(user);
		return "redirect:/users";
	}
	
	@GetMapping
	public String viewUsersPage(Model model) {
		model.addAttribute("users", userService.findAll());
		return viewPaginatedUsers(1, 10, model);
	}
	
	@GetMapping("/page/{page}/size/{size}")
	public String viewPaginatedUsers(@PathVariable("page") int page, @PathVariable("size") int size, Model model) {
		
		Page<User> currentPage = userService.findPaginated(page, size);
		List<User> users = currentPage.getContent();
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", currentPage.getTotalPages());
		model.addAttribute("totalItems", currentPage.getTotalElements());
		model.addAttribute("users", users);
		
		return "user/users";
	}
}
