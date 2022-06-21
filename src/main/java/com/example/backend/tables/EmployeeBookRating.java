package com.example.backend.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Ratings")
public class EmployeeBookRating
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private float rating;
	
	@ManyToOne
	private Employee employee;
	
	@ManyToOne
	private BookTable book;
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	
	public float getRating()
	{
		return rating;
	}
	public void setRating(float rating)
	{
		this.rating = rating;
	}
	
	public Employee getEmployee()
	{
		return employee;
	}
	public void setEmployee(Employee employee)
	{
		this.employee = employee;
	}
	
	public BookTable getBook()
	{
		return book;
	}
	public void setBook(BookTable book)
	{
		this.book = book;
	}
}
