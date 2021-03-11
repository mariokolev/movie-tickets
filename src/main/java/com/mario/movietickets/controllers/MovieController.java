package com.mario.movietickets.controllers;

import java.io.IOException;
import java.util.List;
import com.mario.movietickets.entities.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
		return viewPaginatedMovies(1,10, model);
	}
	
	@GetMapping("/page/{page}/size/{size}")
	public String viewPaginatedMovies(@PathVariable("page")int page, @PathVariable("size") int size, Model model) {
		Page<Movie> currentPage = movieService.findPaginated(page, size);
		List<Movie> movies = currentPage.getContent();
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", currentPage.getTotalPages());
		model.addAttribute("totalItems", currentPage.getTotalElements());
		model.addAttribute("size", size);
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
    public String updateMovie(@ModelAttribute("movie") Movie movie, @RequestParam("movieImage") MultipartFile file) throws IOException {
		Image image = new Image();
		image.setName(file.getName());
		image.setImageBytes(file.getBytes());
		image.setType(file.getContentType());

		movie.setImage(image);
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
	
	@PostMapping(value = "/add",  headers = "content-type=multipart/form-data")
	public String addMovie(@ModelAttribute("movie") Movie movie,@RequestParam("movieImage") MultipartFile file) throws IOException{
		Image image = new Image();
		image.setName(file.getName());
		image.setImageBytes(file.getBytes());
		image.setType(file.getContentType());

		movie.setImage(image);
		movieService.saveMovie(movie);
		return "redirect:/movies";
	}

	@GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable("id") Long movieId){
		byte[] bytes = movieService.findById(movieId).getImage().getImageBytes();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
	}

	
	@GetMapping("/{id}")
	public String viewMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("movie", movieService.findById(id));
		return "movie/movie";
	}
}
