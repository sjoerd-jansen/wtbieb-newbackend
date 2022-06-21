package com.example.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.tables.EmployeeBookRating;

public interface EmployeeBookRatingRepository extends JpaRepository<EmployeeBookRating, Long>
{
	List<EmployeeBookRating> findByBookId(long bookId);
	List<EmployeeBookRating> findByEmployeeId(long employeeId);
	Optional<EmployeeBookRating> findByEmployeeIdAndBookId(long employeeId, long bookId);
}
