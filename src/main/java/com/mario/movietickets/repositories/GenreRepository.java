package com.mario.movietickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mario.movietickets.entities.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
