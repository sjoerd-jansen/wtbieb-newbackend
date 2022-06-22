package com.example.backend.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.tables.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>
{
	Optional<Employee> findByEmployeeEmail(String employeeEmail);
	
	List<Employee> findByEmployeeActiveTrue();
	
	List<Employee> findByEmployeeAdminFalse();
}
