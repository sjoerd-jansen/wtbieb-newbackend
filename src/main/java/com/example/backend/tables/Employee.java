package com.example.backend.tables;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Employee
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 50, nullable = false)
	private String employeeName;

	@Column(length = 50, nullable = false)
	private String employeeEmail;

	@Column(nullable = false)
	private String employeePassword;

	@Column(nullable = false)
	private boolean employeeAdmin;
	
	private String employeeAvatar;

	private int booksInPossession;
	
	private boolean employeeActive;
	
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

	public String getEmployeeName()
	{
		return employeeName;
	}
	public void setEmployeeName(String employeeName)
	{
		this.employeeName = employeeName;
	}

	public String getEmployeeEmail()
	{
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail)
	{
		this.employeeEmail = employeeEmail;
	}

	public String getEmployeePassword()
	{
		return employeePassword;
	}
	public void setEmployeePassword(String employeePassword)
	{
		this.employeePassword = employeePassword;
	}

	public boolean getEmployeeAdmin()
	{
		return employeeAdmin;
	}
	public void setEmployeeAdmin(boolean employeeAdmin)
	{
		this.employeeAdmin = employeeAdmin;
	}
	
	public String getEmployeeAvatar()
	{
		return employeeAvatar;
	}
	public void setEmployeeAvatar(String employeeAvatar)
	{
		this.employeeAvatar = employeeAvatar;
	}
	
	public int getBooksInPossession()
	{
		return booksInPossession;
	}
	public void setBooksInPossession(int booksInPossession)
	{
		this.booksInPossession = booksInPossession;
	}
	
	public boolean getEmployeeActive()
	{
		return employeeActive;
	}
	public void setEmployeeActive(boolean employeeActive)
	{
		this.employeeActive = employeeActive;
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
