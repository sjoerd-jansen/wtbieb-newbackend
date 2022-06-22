package com.example.backend.search;

public class EmployeeSearch
{
	private String employeeEmail;
	private boolean employeeAdmin;
	private boolean employeeActive;

	public String getEmployeeEmail()
	{
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail)
	{
		this.employeeEmail = employeeEmail;
	}

	public boolean getEmployeeActive()
	{
		return employeeActive;
	}

	public void setEmployeeActive(boolean employeeActive)
	{
		this.employeeActive = employeeActive;
	}

	public boolean getEmployeeAdmin()
	{
		return employeeAdmin;
	}

	public void setEmployeeAdmin(boolean employeeAdmin)
	{
		this.employeeAdmin = employeeAdmin;
	}
	
}
