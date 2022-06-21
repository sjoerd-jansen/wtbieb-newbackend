package com.example.backend.search;

import com.example.backend.tables.BookTable;
import com.example.backend.tables.Employee;

public class EmployeeBookRatingSearch
{
	private long id;
	private BookTable book;
	private Employee employee;
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	
	public BookTable getBook()
	{
		return book;
	}
	public void setBook(BookTable book)
	{
		this.book = book;
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
