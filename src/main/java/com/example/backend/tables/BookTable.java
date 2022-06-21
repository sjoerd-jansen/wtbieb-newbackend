package com.example.backend.tables;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Boeken")
public class BookTable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 100, nullable = false)
	private String bookTitle;

	@Column(length = 50, nullable = false)
	private String bookAuthor;

	@Column(length = 17, nullable = false)
	private String bookIsbn;

	@Column(nullable = false)
	private int copiesAvailable;
	
	private String bookTags;
	
	private String bookCover;
	
	@OneToMany
	List<EmployeeBookRating> ratings;
	
	// Getters & setters
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}

	public String getBookTitle()
	{
		return bookTitle;
	}
	public void setBookTitle(String bookTitle)
	{
		this.bookTitle = bookTitle;
	}

	public String getBookAuthor()
	{
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor)
	{
		this.bookAuthor = bookAuthor;
	}

	public String getBookIsbn()
	{
		return bookIsbn;
	}
	public void setBookIsbn(String bookIsbn)
	{
		this.bookIsbn = bookIsbn;
	}

	public int getCopiesAvailable()
	{
		return copiesAvailable;
	}
	public void setCopiesAvailable(int copiesAvailable)
	{
		this.copiesAvailable = copiesAvailable;
	}
	
	public String getBookTags()
	{
		return bookTags;
	}
	public void setBookTags(String bookTags)
	{
		this.bookTags = bookTags;
	}
	
	public String getBookCover()
	{
		return bookCover;
	}
	public void setBookCover(String bookCover)
	{
		this.bookCover = bookCover;
	}	

	public List<EmployeeBookRating> getRatings()
	{
		return ratings;
	}
	public void setRatings(List<EmployeeBookRating> ratings)
	{
		this.ratings = ratings;
	}

}
