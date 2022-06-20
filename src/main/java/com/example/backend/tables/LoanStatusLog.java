package com.example.backend.tables;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class LoanStatusLog
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private CopyBookTable copyBook;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateLent;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateReturned;
	
	@ManyToOne
	private Employee employee;
	
	// Getters & setters	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	
	public LocalDate getDateLent()
	{
		return dateLent;
	}
	public void setDateLent(LocalDate dateLent)
	{
		this.dateLent = dateLent;
	}

	public LocalDate getDateReturned()
	{
		return dateReturned;
	}
	public void setDateReturned(LocalDate dateReturned)
	{
		this.dateReturned = dateReturned;
	}
	
	public CopyBookTable getCopyBook()
	{
		return copyBook;
	}
	public void setCopyBook(CopyBookTable copyBook)
	{
		this.copyBook = copyBook;
	}

	public Employee getEmployee()
	{
		return employee;
	}
	public void setEmployee(Employee employee)
	{
		this.employee = employee;
	}
}
