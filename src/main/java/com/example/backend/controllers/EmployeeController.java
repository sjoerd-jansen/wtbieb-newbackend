package com.example.backend.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.StringResponse;
import com.example.backend.dto.EmployeeDTO;
import com.example.backend.dto.LoanedCopy;
import com.example.backend.dto.LoginDTO;
import com.example.backend.repositories.EmployeeRepository;
import com.example.backend.repositories.LoanStatusLogRepository;
import com.example.backend.security.PasswordHashing;
import com.example.backend.tables.Employee;
import com.example.backend.tables.LoanStatusLog;

@CrossOrigin(maxAge = 3600)
@RestController
public class EmployeeController
{
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private LoanStatusLogRepository loanLogRepo;

	// Get all employees in Database
	@GetMapping("employee")
	public List<EmployeeDTO> GetEmployee()
	{
		List<Employee> allEmployees = employeeRepo.findByEmployeeActiveTrue();
		
		List<EmployeeDTO> employees = new ArrayList<EmployeeDTO>();

		for (Employee employee : allEmployees)
		{			
			EmployeeDTO newEmployee = new EmployeeDTO().CreateNewEmployee(employee);
			employees.add(newEmployee);
		}

		// Returns all employees currently in the database
		return employees;
	}
	
	// Get single employees in Database
	@GetMapping("employee/{employeeId}")
	public EmployeeDTO GetSingleEmployee(@PathVariable long employeeId)
	{
		EmployeeDTO newEmployee = new EmployeeDTO();
		// Returns all employees in database with given id
		Optional<Employee> optional = employeeRepo.findById(employeeId);
		
		if (optional.isPresent())
		{
			newEmployee = newEmployee.CreateNewEmployee(optional.get());
			newEmployee.setEmployeeCurrentlyLoaned(optional.get().getBooksInPossession());
		}
		
		return newEmployee;
	}
	
	// Create new employee in database
	@PostMapping("employee/new")
	@ResponseBody
	public StringResponse NewEmployee(@RequestBody EmployeeDTO newEmployee)
	{
		Employee employee = new Employee();
		
		// Store all info in employee database
		employee.setEmployeeName(newEmployee.getEmployeeName());
		employee.setEmployeeEmail(newEmployee.getEmployeeEmail().toLowerCase());
		String password = PasswordHashing.encryptThisString(newEmployee.getEmployeePassword());
		employee.setEmployeePassword(password);
		employee.setEmployeeAdmin(newEmployee.getEmployeeAdmin());
		employee.setEmployeeAvatar(newEmployee.getEmployeeAvatar());
		employee.setEmployeeActive(true);
		
		// Save employee to database
		employeeRepo.save(employee);

		return StringResponse.NewResponse("De medewerker " + employee.getEmployeeName() + " is toegevoegd.");
	}
	
	// Update employee in database
	@PutMapping("employee/{employeeId}/update")
	@ResponseBody
	public StringResponse UpdateEmployee(@PathVariable long employeeId, @RequestBody EmployeeDTO employee)
	{
		// Find all employees in the database with given id
		Optional<Employee> optional = employeeRepo.findById(employeeId);
		Employee updateEmployee;
		
		// If employee exists
		if (optional.isPresent())
		{
			// Set variable of employee to be updated
			updateEmployee = optional.get();
			
			// Update employee properties (if input is given)
			if (employee.getEmployeeName() != "")
				updateEmployee.setEmployeeName(employee.getEmployeeName());
			if (employee.getEmployeeEmail() != "")
				updateEmployee.setEmployeeEmail(employee.getEmployeeEmail());
			if (employee.getEmployeeAvatar() != "")
				updateEmployee.setEmployeeAvatar(employee.getEmployeeAvatar());
			
			updateEmployee.setEmployeeAdmin(employee.getEmployeeAdmin());
			System.out.println("employee pass " + employee.getEmployeePassword());
			if (employee.getEmployeePassword() != null && employee.getEmployeePassword() != "")
			{
				String hashedPass = PasswordHashing.encryptThisString(employee.getEmployeePassword());					
				updateEmployee.setEmployeePassword(hashedPass);
			}
			
			// Save new employee to database
			employeeRepo.save(updateEmployee);
			
			return StringResponse.NewResponse("De medewerker " + employee.getEmployeeName() + " is geüpdate.");
		}
		
		return StringResponse.NewResponse("Deze medewerker kan niet worden gevonden. Kies een andere medewerker of neem contact op met support.");
	}
	
	// In this function we check (in the database)
	// whether an employee can log in, aka whether the email and password
	// match an entry in the database
	@GetMapping("employee/login/{email}/{password}")
	private LoginDTO logIn(@PathVariable String email, @PathVariable String password) 
	{
		// an initial check whether the email matches one in the database,
		// otherwise we don't have to check the password
		Optional<Employee> employees = employeeRepo.findByEmployeeEmail(email.toLowerCase());
		
		// If employee exists
		if(employees.isPresent())
		{
			Employee employee = employees.get();

			String hashedPass = PasswordHashing.encryptThisString(password);	

			// Check if employee password matches
			if (employee.getEmployeePassword().equals(hashedPass) && employee.getEmployeeActive())
			{
				return new LoginDTO().CreateNewLogin(employee);
			}
		}
		// If email doesn't exist, return empty
		return new LoginDTO();
	}
	
	// Find all books owned by employee
	@GetMapping("employee/{employeeId}/findbooks")
	private List<LoanedCopy> AllEmployeeBooks(@PathVariable long employeeId)
	{
		// Create a list to store all found books in
		List<LoanedCopy> books = new ArrayList<LoanedCopy>();
		
		// Find and go through all loaned books currently loaned by employee
		for (LoanStatusLog loanedBook : loanLogRepo.findByEmployeeIdAndDateLentNotNullAndDateReturnedNull(employeeId))
		{
			// Create new bookCopy to add to the list, add all book info to the bookCopy
			LoanedCopy lentBook = new LoanedCopy().CreateNewLoanedCopy(loanedBook);
			// Add copy to the list
			books.add(lentBook);
		}

		return books;
	}
	
	// Find history of single employee
	@GetMapping("employee/{employeeId}/history")
	private List<LoanedCopy> EmployeeHistory(@PathVariable long employeeId)
	{
		// Create list to store all books in
		List<LoanedCopy> books = new ArrayList<LoanedCopy>();
		
		// Find and go through all books ever loaned by employee
		for (LoanStatusLog loanedBook : loanLogRepo.findByEmployeeId(employeeId))
		{
			// Create new bookCopy to add to the list, add all book info to the bookCopy
			LoanedCopy book = new LoanedCopy().CreateNewLoanedCopy(loanedBook);
			// Add copy to the list
			books.add(book);
		}
		
		
		return books;
	}
	
	// "Delete" a user, remove all information from the database
	@PutMapping("employee/{employeeId}/delete")
	private StringResponse DeleteEmployee(@PathVariable long employeeId)
	{
		// Find all employees in the database with given id
		Optional<Employee> optional = employeeRepo.findById(employeeId);
		Employee deleteEmployee;
		
		// If employee exists
		if (optional.isPresent())
		{
			// Set variable of employee to be updated
			deleteEmployee = optional.get();

			String name = deleteEmployee.getEmployeeName();
			
			// Update employee properties (if input is given)
			deleteEmployee.setEmployeeName("Verwijderde gebruiker");
			deleteEmployee.setEmployeeEmail("");
			deleteEmployee.setEmployeeAvatar("https://cargonaut.nl/wp-content/uploads/2019/06/person-icon-silhouette-png-0.png");
			deleteEmployee.setEmployeeAdmin(false);
			deleteEmployee.setEmployeePassword("");
			deleteEmployee.setEmployeeActive(false);
			
			// Save new employee to database
			employeeRepo.save(deleteEmployee);
			
			return StringResponse.NewResponse("De medewerker " + name + " is verwijderd.");
		}
		
		return StringResponse.NewResponse("Deze medewerker kan niet worden gevonden. Kies een andere medewerker of neem contact op met support.");
	}
}