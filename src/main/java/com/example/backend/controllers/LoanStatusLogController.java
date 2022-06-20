package com.example.backend.controllers;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.LoanedCopy;
import com.example.backend.repositories.LoanStatusLogRepository;
import com.example.backend.tables.CopyBookTable;
import com.example.backend.tables.Employee;
import com.example.backend.tables.LoanStatusLog;

@CrossOrigin(maxAge = 3600)
@RestController
public class LoanStatusLogController
{
	@Autowired
	private LoanStatusLogRepository loanStatusLogRepo;

	public static String ts()
	{
	    return "Timestamp: " + new Timestamp(System.currentTimeMillis());
	}
	
	// Get all currently loaned books (not returned ones)
	@GetMapping("book/findloaned")
	public List<LoanedCopy> GetLoanStatusLog()
	{
		// Find all books that have a lent date, but not a return date
		List<LoanStatusLog> stillLoaned = loanStatusLogRepo.findByDateReturnedNull();
		// Create list to store all loaned copies in
		List<LoanedCopy> loanedBooks = new ArrayList<LoanedCopy>();

		// Search in all loaned books
		for (LoanStatusLog loan : stillLoaned)
		{
			// Create DTO to pass information to front-end
			LoanedCopy book = new LoanedCopy().CreateNewLoanedCopy(loan);

			// Add copy to list
			loanedBooks.add(book);
		}

		// Returns all loanStatusLogs currently in the database
		return loanedBooks;
	}
	
	// Get all currently loaned books (not returned ones)
	@GetMapping("book/findloaned/{bookId}")
	public List<LoanedCopy> GetLoanStatusLogBook(@PathVariable long bookId)
	{
		// Find all books that have a lent date, but not a return date
		List<LoanStatusLog> stillLoaned = loanStatusLogRepo.findByDateReturnedNullAndCopyBookMainBookId(bookId);
		// Create list to store all loaned copies in
		List<LoanedCopy> loanedBooks = new ArrayList<LoanedCopy>();

		// Search in all loaned books
		for (LoanStatusLog loan : stillLoaned)
		{
			// Create DTO to pass information to front-end
			LoanedCopy book = new LoanedCopy().CreateNewLoanedCopy(loan);

			// Add copy to list
			loanedBooks.add(book);
		}

		// Returns all loanStatusLogs currently in the database
		return loanedBooks;
	}
	
	// Get all books in loaned Database
	@GetMapping("book/history")
	public List<LoanedCopy> GetLoanStatusLogAll()
	{
		// Find all books in the loanLog
		List<LoanStatusLog> loanLog = loanStatusLogRepo.findAll();
		// Create list to store all loaned copies in
		List<LoanedCopy> loanedBooks = new ArrayList<LoanedCopy>();

		// Search in all loaned books
		for (LoanStatusLog loan : loanLog)
		{
			// Create DTO to pass information to front-end
			LoanedCopy book = new LoanedCopy().CreateNewLoanedCopy(loan);

			// Add copy to list
			loanedBooks.add(book);	
		}

		// Returns all loanStatusLogs currently in the database
		return loanedBooks;
	}

	// Find loan history of single book
	@GetMapping("book/{copyId}/history")
	private List<LoanedCopy> GetBookHistory(@PathVariable String copyId)
	{
		// Find all loaned inputs of copy
		List<LoanStatusLog> loanLog = loanStatusLogRepo.findByCopyBookCopyId(copyId);
		// Get all books loaned at any time
		List<LoanedCopy> loanedBooks = new ArrayList<LoanedCopy>();
		
		for (LoanStatusLog copy : loanLog)
		{
			LoanedCopy loanedCopy = new LoanedCopy().CreateNewLoanedCopy(copy);
			loanedBooks.add(loanedCopy);
		}
		
		return loanedBooks;
	}

	// Loan book to employee
	public void LoanBook(CopyBookTable copyBook, Employee employee)
	{
		// Create new LoanStatusLog
		LoanStatusLog loanLog = new LoanStatusLog();
		
		// Set info to loanLog
		loanLog.setCopyBook(copyBook);
		loanLog.setEmployee(employee);
		loanLog.setDateLent(LocalDate.now());

		// Save to loanLog
		loanStatusLogRepo.save(loanLog);
	}
	
	// Return book that has been loaned
	public void ReturnBook(String copyId)
	{		
		LoanStatusLog loanLog = new LoanStatusLog();

		// Find loaned copy in the loan database (no return date)
		Optional<LoanStatusLog> loanStatus = loanStatusLogRepo.findByCopyBookCopyIdAndDateLentNotNullAndDateReturnedNull(copyId);

		// Check if copy is present
		if (loanStatus.isPresent())
		{
			// Current book is being returned
			loanLog = loanStatus.get();
			loanLog.setDateReturned(LocalDate.now());

			// Save to loanLog
			loanStatusLogRepo.save(loanLog);
		}
		else
		{
			System.out.println("Book is not present in loan database");
		}
	}
}
