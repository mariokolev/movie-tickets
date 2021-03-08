package com.mario.movietickets.repositories;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.mario.movietickets.entities.Genre;
import com.mario.movietickets.repositories.GenreRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
class GenreRepositoryTests {

	@Autowired
	private GenreRepository genreRepository;
	
	@Test
	public void saveGenreTest() {
		Genre genre = new Genre();
		genre.setGenreName("action");
		genreRepository.save(genre);
	}
}
