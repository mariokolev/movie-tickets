package com.mario.movietickets.repositories;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mario.movietickets.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o where o.user.id = :id ORDER BY o.dateOfOrder DESC")
	public Set<Order> findAllByUserId(@Param("id") Long id);
	
	public List<Order> findAllByOrderByDateOfOrderDesc();
}
