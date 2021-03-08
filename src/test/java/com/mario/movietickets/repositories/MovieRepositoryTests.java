package com.mario.movietickets.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.mario.movietickets.entities.Movie;
import com.mario.movietickets.repositories.MovieRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
class MovieRepositoryTests {

	@Autowired
	private MovieRepository movieRepository;

	@Test
	public void findByIdTest() {
		Movie movie = movieRepository.findById(2l).get();
		assertThat(movie).isNotNull();
	}
}
