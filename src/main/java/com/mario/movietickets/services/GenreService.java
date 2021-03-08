package com.mario.movietickets.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mario.movietickets.entities.Genre;
import com.mario.movietickets.repositories.GenreRepository;

@Service
public class GenreService {

	private final GenreRepository genreRepository;
	
	@Autowired
	public GenreService(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}
	
	public List<Genre> findAll(){
		return this.genreRepository.findAll();
	}
}
