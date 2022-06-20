package com.example.backend.dto;

import com.example.backend.tables.Employee;

public class LoginDTO
{
	private long employeeId;
	private String employeeName;
	private boolean employeeAdmin;
	
	public long getEmployeeId()
	{
		return employeeId;
	}
	public void setEmployeeId(long employeeId)
	{
		this.employeeId = employeeId;
	}
	
	public String getEmployeeName()
	{
		return employeeName;
	}
	public void setEmployeeName(String employeeName)
	{
		this.employeeName = employeeName;
	}
	
	public boolean getEmployeeAdmin()
	{
		return employeeAdmin;
	}
	public void setEmployeeAdmin(boolean employeeAdmin)
	{
		this.employeeAdmin = employeeAdmin;
	}
	
	public LoginDTO CreateNewLogin(Employee employee)
	{
		LoginDTO newLogin = new LoginDTO();
		
		if (employee != null)
		{
			newLogin.setEmployeeId(employee.getId());
			newLogin.setEmployeeName(employee.getEmployeeName());
			newLogin.setEmployeeAdmin(employee.getEmployeeAdmin());
		}
		
		return newLogin;
	}
}
