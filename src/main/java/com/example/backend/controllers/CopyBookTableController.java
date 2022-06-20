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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.StringResponse;
import com.example.backend.dto.BookCopy;
import com.example.backend.repositories.BookTableRepository;
import com.example.backend.repositories.CopyBookTableRepository;
import com.example.backend.repositories.EmployeeRepository;
import com.example.backend.repositories.LoanStatusLogRepository;
import com.example.backend.tables.BookTable;
import com.example.backend.tables.CopyBookTable;
import com.example.backend.tables.Employee;

@CrossOrigin(maxAge = 3600)
@RestController
public class CopyBookTableController
{
	@Autowired
	private CopyBookTableRepository copyBookTableRepo;
	@Autowired
	private BookTableRepository bookTableRepo;
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private LoanStatusLogRepository loanRepo;
	
	@Autowired
	// Link with LoanStatus database to add/remove loaned books
	private LoanStatusLogController loanStatusControl;

	// Create new book copy
	@PostMapping("book/{id}/copy/new/{amount}") // id = bookId | amount = amount of books to add
	@ResponseBody
	public StringResponse newCopy(@PathVariable long id, @PathVariable int amount)
	{
		// Check how many copies of this book are already in database
		int copiesAlreadyInDatabase = copyBookTableRepo.findByMainBookId(id).size();
		
		// Create new BookTable object, find book in book database
		BookTable mainBook = bookTableRepo.findById(id).get();
		
		// Add correct amount of copies
		for (int i = 0; i < amount; i++)
		{
			CopyBookTable copyBook = new CopyBookTable();
			// Set correct id, composed of bookId and the amount of copies already in the database
			String copyId = id + "." + (copiesAlreadyInDatabase+i+1);
			
			// Set info to the copy
			copyBook.setCopyId(copyId);
			copyBook.setBookAvailable(true);
			copyBook.setBookLent(false);
			copyBook.setBookArchived(false);
			copyBook.setMainBook(mainBook);
			
			// Save copy to database
			copyBookTableRepo.save(copyBook);
		}
		
		// Set amount of available copies in book database
		ChangeAvailableCopies(mainBook, amount);
		
		return StringResponse.NewResponse("Er zijn " + amount + " exemplaren van het boek " + mainBook.getBookTitle() + " toegevoegd.");
	}
	
	// Get all copies of a certain book
	@GetMapping("book/{id}/copies")
	public List<BookCopy> GetBook(@PathVariable long id)
	{
		// Find all copies in database, store in list (add "." behind id to avoid finding 10 or above as well)
		List<CopyBookTable> copyBooks = copyBookTableRepo.findByMainBookId(id);
		// Create new list to store all book copies in
		List<BookCopy> copies = new ArrayList<BookCopy>();
		
		for (CopyBookTable copy : copyBooks)
		{
			BookCopy bookCopy = new BookCopy();
			// Add book info to the book copy (title, author etc.)
			bookCopy = bookCopy.CreateNewBookCopy(copy);
			// Add copy to the list
			copies.add(bookCopy);
		}
		
		// Returns all books currently in the database
		return copies;
	}
	
	// Loan reserved book
	public void LoanReservation(CopyBookTable copyBook, Employee employee)
	{
		// Change available copies of main book
		ChangeAvailableCopies(copyBook.getMainBook(), -1);
		// Add book to LoanStatusLog database (in LoanStatusLogController)
		loanStatusControl.LoanBook(copyBook, employee);
		// Save CopyBook to database as lent
		copyBookTableRepo.save(copyBook);
		
		// Save employee with new amount of books
		employee.setBooksInPossession(employee.getBooksInPossession() + 1);
		employeeRepo.save(employee);
	}
	
	// Loan book that has not been reserved
	@PutMapping("book/loan/new/{copyId}/{employeeId}")
	public StringResponse LoanBookCopy(@PathVariable String copyId, @PathVariable long employeeId)
	{
		// Find copy in the database with given id
		Optional<CopyBookTable> optional = copyBookTableRepo.findByCopyId(copyId);
		// Find employee in database with given id
		Optional<Employee> optionalEmployee = employeeRepo.findById(employeeId);
		
		// Check if copy exists
		if (optional.isPresent() && optionalEmployee.isPresent())
		{
			CopyBookTable copyBook = optional.get();
			Employee employee = optionalEmployee.get();
			
			// Check if copy is available
			if (copyBook.getBookAvailable())
			{
				loanStatusControl.LoanBook(copyBook, employee);
				
				copyBook.setBookAvailable(false);
				copyBook.setBookLent(true);
				
				copyBookTableRepo.save(copyBook);

				employee.setBooksInPossession(employee.getBooksInPossession() + 1);
				employeeRepo.save(employee);
				
				return StringResponse.NewResponse("Exemplaar " + copyId + " is uitgeleend aan " + employee.getEmployeeName());
			}
			
			return StringResponse.NewResponse("Dit exemplaar is niet beschikbaar. Probeer een ander exemplaar of neem contact op met support.");
		}
		
		return StringResponse.NewResponse("Het boek exemplaar of de medewerker konden niet gevonden worden. Probeer opnieuw of neem contact op met support.");
	}
	
	// Return book that has been loaned
	@PutMapping("book/return/{copyId}")
	@ResponseBody
	public StringResponse ReturnBookCopy(@PathVariable String copyId)
	{
		// Find all books in the database with given id
		Optional<CopyBookTable> optional = copyBookTableRepo.findByCopyId(copyId);
		
		if (optional.isPresent())
		{
			CopyBookTable returnBook = optional.get();
			
			if (returnBook.getBookLent())
			{
				returnBook.setBookAvailable(true);
				returnBook.setBookLent(false);
				
				// Find employee currently in possession of the book
				Employee employee = loanRepo.findByCopyBookCopyIdAndDateLentNotNullAndDateReturnedNull(copyId).get().getEmployee();
				
				// Remove book of list of books in possession
				employee.setBooksInPossession(employee.getBooksInPossession() - 1);
				// Save employee
				employeeRepo.save(employee);
				
				loanStatusControl.ReturnBook(copyId);
				ChangeAvailableCopies(returnBook.getMainBook(), 1);
				
				copyBookTableRepo.save(returnBook);
				
				return StringResponse.NewResponse("Exemplaar " + copyId + " van het boek " + returnBook.getMainBook().getBookTitle() + " is geretourneerd.");
			}
			else
			{
				return StringResponse.NewResponse("Dit exemplaar is momenteel niet uitgeleend. Kies een ander exemplaar of neem contact op met support.");
			}
		}

		return StringResponse.NewResponse("Dit exemplaar kan niet worden gevonden. Kies een ander exemplaar of neem contact op met support.");
	}
	
	// Archive copy in database
	@PutMapping("book/archive/{copyId}")
	@ResponseBody
	public StringResponse ArchiveBookCopy(@PathVariable String copyId)
	{
		// Find all books in the database with given id
		Optional<CopyBookTable> optional = copyBookTableRepo.findByCopyId(copyId);
		
		if (optional.isPresent())
		{
			// Set variable to copy found in database
			CopyBookTable archiveBook = optional.get();
			
			ChangeAvailableCopies(archiveBook.getMainBook(), -1);						
			
			// Change values of book that is loaned/archived/returned
			archiveBook.setBookAvailable(false);
			archiveBook.setBookArchived(true);
			archiveBook.setBookLent(false);
			
			// Update book to database
			copyBookTableRepo.save(archiveBook);
			
			return StringResponse.NewResponse("Exemplaar " + copyId + " van het boek " + archiveBook.getMainBook().getBookTitle() + " is gearchiveerd");
		}

		return StringResponse.NewResponse("Dit exemplaar kan niet worden gevonden. Kies een ander exemplaar of neem contact op met support.");
	}
	
	// Changes available copies in Book table
	public void ChangeAvailableCopies(BookTable book, int changeAmount)
	{
		// Set the changed amount of available copies
		book.setCopiesAvailable(book.getCopiesAvailable() + changeAmount);
		
		// Save update book to database
		bookTableRepo.save(book);
	}
	
	// Find all available copies of single book
	@GetMapping("book/{bookId}/findavailable")
	public List<BookCopy> FindAvailableCopies(@PathVariable long bookId)
	{
		// Find all available books
		List <CopyBookTable> availableBooks = copyBookTableRepo.findByBookAvailableTrueAndMainBookId(bookId);
		// Create list to store all book info in of available books
		List<BookCopy> availableList = new ArrayList<BookCopy>();

		for (CopyBookTable copy : availableBooks)
		{
			// Create new BookCopy with all book info
			BookCopy availableCopy = new BookCopy().CreateNewBookCopy(copy);
			// Add BookCopy to list to return
			availableList.add(availableCopy);
		}
				
		return availableList;
	}
	
	// Find all archived copies
	@GetMapping("book/findarchived")
	public List<BookCopy> FindArchivedCopies()
	{
		// Find all archived books
		List <CopyBookTable> archivedBooks = copyBookTableRepo.findByBookArchivedTrue();
		// Create list to store all book info in of archived books
		List<BookCopy> archivedList = new ArrayList<BookCopy>();

		for (CopyBookTable copy : archivedBooks)
		{
			// Create new BookCopy with all book info
			BookCopy newArchivedCopy = new BookCopy().CreateNewBookCopy(copy);
			// Add BookCopy to list to return
			archivedList.add(newArchivedCopy);
		}
		
		return archivedList;
	}
}
