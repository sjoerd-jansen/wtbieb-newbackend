package com.example.backend.dto;

import com.example.backend.tables.Employee;

public class EmployeeDTO
{
	private long employeeId;
	private String employeeName;
	private String employeeEmail;
	private String employeePassword;
	private boolean employeeAdmin;
	private String employeeAvatar;
	private int employeeCurrentlyLoaned;
	private boolean employeeActive;
	
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

	public int getEmployeeCurrentlyLoaned()
	{
		return employeeCurrentlyLoaned;
	}
	public void setEmployeeCurrentlyLoaned(int employeeCurrentlyLoaned)
	{
		this.employeeCurrentlyLoaned = employeeCurrentlyLoaned;
	}

	public boolean getEmployeeActive()
	{
		return employeeActive;
	}
	public void setEmployeeActive(boolean employeeActive)
	{
		this.employeeActive = employeeActive;
	}
	
	public EmployeeDTO CreateNewEmployee(Employee employee)
	{
		EmployeeDTO newEmployee = new EmployeeDTO();
		
		if (employee != null)
		{
			newEmployee.setEmployeeId(employee.getId());
			newEmployee.setEmployeeName(employee.getEmployeeName());
			newEmployee.setEmployeeEmail(employee.getEmployeeEmail());
			newEmployee.setEmployeeAdmin(employee.getEmployeeAdmin());
			newEmployee.setEmployeeAvatar(employee.getEmployeeAvatar());
			newEmployee.setEmployeeCurrentlyLoaned(employee.getBooksInPossession());
			newEmployee.setEmployeeActive(employee.getEmployeeActive());
		}
		
		return newEmployee;
	}
}
