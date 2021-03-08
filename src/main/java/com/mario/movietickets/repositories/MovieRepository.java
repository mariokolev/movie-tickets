package com.mario.movietickets.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.mario.movietickets.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("SELECT m FROM Movie m where m.movieName = :movieName")
	public Optional<Movie> findByMovieName(@Param("movieName") String movieName);
	
	public List<Movie> findAllByOrderByDateAddedDesc();
}
