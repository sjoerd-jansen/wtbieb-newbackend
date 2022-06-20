package com.example.backend.search;

import java.time.LocalDate;

public class LoanStatusLogSearch
{
	private long employeeId;
	private String copyId;
	private LocalDate dateLent;
	private LocalDate dateReturned;
	
	public long getEmployeeId()
	{
		return employeeId;
	}
	public void setEmployeeId(long employeeId)
	{
		this.employeeId = employeeId;
	}
	
	public String getCopyId()
	{
		return copyId;
	}
	public void setCopyId(String copyId)
	{
		this.copyId = copyId;
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
}
