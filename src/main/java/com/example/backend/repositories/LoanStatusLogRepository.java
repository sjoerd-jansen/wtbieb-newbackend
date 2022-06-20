package com.example.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.tables.LoanStatusLog;

public interface LoanStatusLogRepository extends JpaRepository<LoanStatusLog, Long>
{
	// Find all books of copy
	List<LoanStatusLog> findByCopyBookCopyId(String copyId);
	
	// Find all copies of employee
	List<LoanStatusLog> findByEmployeeId(long id);
	
	// Find all copies currently in employees possession
	List<LoanStatusLog> findByEmployeeIdAndDateLentNotNullAndDateReturnedNull(long id);
	
	// Find all lent copies currently
	List<LoanStatusLog> findByDateReturnedNull();
	
	// Find loaned copy
	Optional<LoanStatusLog> findByCopyBookCopyIdAndDateLentNotNullAndDateReturnedNull(String copyId);
	
	List<LoanStatusLog> findByDateReturnedNullAndCopyBookMainBookId(long bookId);
}