package com.mario.movietickets.controllers;

import java.io.IOException;
import java.util.Base64;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.mario.movietickets.entities.Movie;
import com.mario.movietickets.services.GenreService;
import com.mario.movietickets.services.MovieService;

@Controller
@RequestMapping(path = "/movies")
public class MovieController {

	private final MovieService movieService;
	
	private final GenreService genreService;
	
	@Autowired
	public MovieController(MovieService movieService, GenreService genreService) {
		this.movieService = movieService;
		this.genreService = genreService;
	}
	
	@GetMapping
	public String viewAllMovies(Model model) {
		return viewPaginatedMovies(1, model);
	}
	
	@GetMapping("/page/{page}")
	public String viewPaginatedMovies(@PathVariable("page")int page, Model model) {
		int size = 5;
		Page<Movie> currentPage = movieService.findPaginated(page, size);
		List<Movie> movies = currentPage.getContent();
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", currentPage.getTotalPages());
		model.addAttribute("totalItems", currentPage.getTotalElements());
		model.addAttribute("movies", movies);
		return "movie/movies";
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView editMovie(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("movie/movie-edit");
		modelAndView.addObject("movie", movieService.findById(id));
		modelAndView.addObject("genres", genreService.findAll());
		return modelAndView;
	}

	@PostMapping("/update")
    public String updateMovie(@ModelAttribute("movie") Movie movie){
        movieService.updateMovie(movie);
        return "redirect:/movies";
    }
	
	@GetMapping("/add")
	public String getAddMovieView(Model model){
		Movie movie = new Movie();
		model.addAttribute("movie",movie);
		model.addAttribute("genres", genreService.findAll());
		return "movie/movie-add";
	}
	
	@PostMapping(value = "/add")
	public String addMovie(@ModelAttribute("movie") Movie movie,@RequestParam("image") MultipartFile file) throws IOException{
//		movie.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		movieService.saveMovie(movie);
		return "redirect:/movies";
	}
	
	@GetMapping("/{id}")
	public String viewMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("movie", movieService.findById(id));
		return "movie/movie";
	}
}