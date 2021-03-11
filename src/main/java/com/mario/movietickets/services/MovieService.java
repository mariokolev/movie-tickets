package com.mario.movietickets.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;

import com.mario.movietickets.entities.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.mario.movietickets.entities.Genre;
import com.mario.movietickets.entities.Movie;
import com.mario.movietickets.repositories.GenreRepository;
import com.mario.movietickets.repositories.MovieRepository;

@Service
public class MovieService {

	private final MovieRepository movieRepository;
	
	private final GenreRepository genreRepository;
	
	@Autowired
	public MovieService(MovieRepository movieRepository, GenreRepository genreRepository) {
		this.movieRepository = movieRepository;
		this.genreRepository = genreRepository;
	}
	
	public List<Movie> findAll(){
		return movieRepository.findAllByOrderByDateAddedDesc();
	}
	
	public Page<Movie> findPaginated(int page, int size){
		Pageable pageable = PageRequest.of(page - 1, size);
		return this.movieRepository.findAll(pageable);
	}
	
	public Movie findById(Long id) {
		return movieRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException("Movie with id[" + id + "] doesn't exist"));
	}
	
	public void saveMovie(Movie movie) {
		Optional<Movie> movieOptional = movieRepository.findByMovieName(movie.getMovieName());
		
		if(movieOptional.isPresent()) {
			throw new IllegalStateException("Movie with name[" + movie.getMovieName() + "] already exists!");
		}
		
		movieRepository.save(movie);
	}
	
	@Transactional
	public void updateMovie(Movie movie) {
		
		Optional<Movie> movieOptional = movieRepository.findById(movie.getId());

		if(movieOptional.isEmpty()) {
			throw new IllegalStateException("Movie with id[" + movie.getId() + "] doesn't exists!");
		}
		
		updateMovieName(movie.getMovieName(), movieOptional.get());
		updateMovieDescription(movie.getDescription(), movieOptional.get());
		updateAvailableTickets(movie.getAvailableTickets(), movieOptional.get());
		updateTicketPrice(movie.getTicketPrice(), movieOptional.get());
		updateMovieGenre(movie.getGenre().getId(), movieOptional.get());
		updateMovieImage(movie.getImage(), movieOptional.get());
	}
	
	private void updateMovieName(String movieName, Movie movie) {
		if(movieName != null &&
				movieName.length() > 0 &&
				!Objects.equals(movieName, movie.getMovieName())){
					movie.setMovieName(movieName);
		}
	}
	
	private void updateMovieDescription(String description, Movie movie) {
		if(description != null && 
				description.length() > 0 &&
				!Objects.equals(description, movie.getDescription())) {
			movie.setDescription(description);
		}
	}

	private void updateAvailableTickets(Integer tickets, Movie movie) {
		if(tickets != null &&
				tickets >= 0 &&
				!Objects.equals(tickets, movie.getAvailableTickets())) {
			movie.setAvailableTickets(tickets);
		}
	}
	
	private void updateTicketPrice(Double ticketPrice, Movie movie) {
		if(ticketPrice != null && 
				ticketPrice >= 0 && 
				!Objects.equals(ticketPrice, movie.getTicketPrice())) {
			movie.setTicketPrice(ticketPrice);
		}
	}
	
	private void updateMovieGenre(Long genreId, Movie movie) {
		if(genreId != null &&
				genreId >= 0 &&
				!Objects.equals(genreId, movie.getGenre().getId())) {
			
			Optional<Genre> genreOptional = genreRepository.findById(genreId);
			
			if(genreOptional.isPresent()) {
				movie.setGenre(genreOptional.get());
			}else {
				throw new IllegalStateException("Genre with id[" + genreId + "] doesn't exists!");
			}
		}
	}

	private void updateMovieImage(Image image, Movie movie){
		if(image != null &&
				image.getImageBytes().length > 0 &&
			!Objects.equals(image, movie.getImage())){
			movie.setImage(image);
		}
	}
}
