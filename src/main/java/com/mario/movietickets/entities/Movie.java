package com.mario.movietickets.entities;

import java.time.LocalDateTime;
import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "movies")
public class Movie{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "movie_name")
	private String movieName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "available_tickets")
	private Integer availableTickets;
	
	@Column(name = "ticket_price")
	private Double ticketPrice;
	
	@Column(name = "date_added")
	@CreationTimestamp
	private LocalDateTime dateAdded;
	
	@ManyToOne
	@JoinColumn(name = "genre_id")
	private Genre genre;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;

	public Movie() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAvailableTickets() {
		return availableTickets;
	}

	public void setAvailableTickets(Integer availableTickets) {
		this.availableTickets = availableTickets;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public LocalDateTime getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(LocalDateTime dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
