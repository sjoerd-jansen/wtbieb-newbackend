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

import com.example.backend.RandomBookGenerator;
import com.example.backend.RandomBookHistoryGenerator;
import com.example.backend.RandomEmployeeGenerator;
import com.example.backend.RandomRatingGenerator;
import com.example.backend.RandomReservationGenerator;
import com.example.backend.StringResponse;
import com.example.backend.dto.Book;
import com.example.backend.dto.BookCopy;
import com.example.backend.repositories.BookTableRepository;
import com.example.backend.repositories.CopyBookTableRepository;
import com.example.backend.repositories.EmployeeBookRatingRepository;
import com.example.backend.repositories.EmployeeRepository;
import com.example.backend.repositories.LoanStatusLogRepository;
import com.example.backend.repositories.ReservedBookRepository;
import com.example.backend.tables.BookTable;
import com.example.backend.tables.CopyBookTable;
import com.example.backend.tables.Employee;
import com.example.backend.tables.EmployeeBookRating;
import com.example.backend.tables.LoanStatusLog;
import com.example.backend.tables.ReservedBook;

@CrossOrigin(maxAge = 3600)
@RestController
public class BookTableController
{
	@Autowired
	private BookTableRepository bookTableRepo;
	@Autowired
	private CopyBookTableController copyBookController;
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private LoanStatusLogRepository loanRepo;
	@Autowired
	private CopyBookTableRepository copyBookTableRepo;
	@Autowired
	private ReservedBookRepository reserveRepo;
	@Autowired
	private EmployeeBookRatingRepository rateRepo;
	
	// Store ID for duplicate book
	private long duplicateId;
	
	// Random generator classes
	private RandomBookGenerator bookGenerator = new RandomBookGenerator();
	private RandomEmployeeGenerator employeeGenerator = new RandomEmployeeGenerator();
	private RandomBookHistoryGenerator historyGenerator = new RandomBookHistoryGenerator();
	private RandomReservationGenerator reservationGenerator = new RandomReservationGenerator();
	private RandomRatingGenerator ratingGenerator = new RandomRatingGenerator();

	// Get all books in Database
	@GetMapping("book/findall")
	public List<Book> GetBooks()
	{
		// Find all books in database
		List<BookTable> bookList = bookTableRepo.findAll();
		// Create list to store all books with info
		List<Book> books = new ArrayList<Book>();
		
		for (BookTable book : bookList)
		{
			// Create new book with all book information
			Book newBook = new Book().CreateNewBook(book);
			
			// Add book to list to return
			books.add(newBook);
		}
		
		// Returns all books currently in the database
		return books;
	}
	
	// Find single book in database
	@GetMapping("book/findsingle/{id}")
	public Book GetBook(@PathVariable long id)
	{
		// Find all books in the database with given id
		Optional<BookTable> optional = bookTableRepo.findById(id);
		Book updateBook;
			
		// If book exists
		if (optional.isPresent())
		{
			// Set variable of book to be updated
			updateBook = new Book().CreateNewBook(optional.get());
			return updateBook;
		}
		
		// Returns all books currently in the database
		return null;
	}
	
	// Create new book in database
	@PostMapping("book/new/{amount}")
	@ResponseBody
	public StringResponse NewBook(@RequestBody BookCopy newBook, @PathVariable int amount)
	{
		// Check if book is unique
		if (IsBookUnique(newBook))
		{
			// Create new bookTable with info from front-end
			BookTable bookTable = new BookTable();

			// Set variables received from front-end (bookCopy)
			bookTable.setBookTitle(newBook.getBookTitle());
			bookTable.setBookAuthor(newBook.getBookAuthor());
			bookTable.setBookIsbn(newBook.getBookIsbn());
			bookTable.setCopiesAvailable(newBook.getBookCopies());
			bookTable.setBookTags(ConvertTags(newBook.getBookTags()));
			bookTable.setBookCover(newBook.getBookCover());
			
			// Save book to database
			bookTableRepo.save(bookTable);
			
			// Add copies to CopyBookTable
			copyBookController.newCopy(bookTable.getId(), amount);
			
			return StringResponse.NewResponse("Het boek " + newBook.getBookTitle() + " is toegevoegd met " + amount + " exemplaren.");
		}
		else
		{
			// Add copies to CopyBookTable
			return copyBookController.newCopy(duplicateId, amount);
		}
	}
	
	// Update book in database
	@PutMapping("book/{id}/update")
	@ResponseBody
	public StringResponse UpdateBook(@PathVariable long id, @RequestBody Book book)
	{
		// Find all books in the database with given id
		Optional<BookTable> optional = bookTableRepo.findById(id);
		BookTable updateBook;
		
		// If book exists
		if (optional.isPresent())
		{
			// Set variable of book to be updated
			updateBook = optional.get();
			
			// Update book properties (if info is given)
			if (book.getBookTitle() != "")
				updateBook.setBookTitle(book.getBookTitle());
			if (book.getBookAuthor() != "")
				updateBook.setBookAuthor(book.getBookAuthor());
			if (book.getBookIsbn() != "")
				updateBook.setBookIsbn(book.getBookIsbn());
			if (book.getBookTags().length > 0)
				updateBook.setBookTags(ConvertTags(book.getBookTags()));
			if (book.getBookCover() != "")
				updateBook.setBookCover(book.getBookCover());
			
			// Save new book to database
			bookTableRepo.save(updateBook);

			return StringResponse.NewResponse("Het boek " + updateBook.getBookTitle() + " is geüpdate.");
		}

		return StringResponse.NewResponse("Dit boek kan niet worden gevonden. Kies een ander boek of neem contact op met support.");
	}
	
	// Check if book already exists in database
	private boolean IsBookUnique(BookCopy newBook)
	{
		// Find the book with specific ISBN
		Optional<BookTable> books = bookTableRepo.findByBookIsbn(newBook.getBookIsbn());

		if (books.isPresent())
		{
			duplicateId = books.get().getId();
			return false;
		}
		else
			return true;
	}

	// Find books from input search query
	@GetMapping("book/search/{input}")
	public List<Book> GetBooksBySearch(@PathVariable String input)
	{
		// Create list to store all books with tag in
		List<Book> books = new ArrayList<Book>();
		// Find all books in the database with title, author or isbn that contains input
		List<BookTable> booksDatabase = bookTableRepo.findByBookTitleContainsOrBookAuthorContainsOrBookIsbnContainsOrBookTagsContains(input, input, input, input);
				
		// Add books with title, author, isbn or tags like input to list of books
		for (BookTable book : booksDatabase)
		{
			books.add(new Book().CreateNewBook(book));
		}

		// Show all books
		return books;
	}
	
	// Convert array of tags to string
	// Tags are stored in a single string for search purposes
	// You can search a string to check if it contains a certain word/letter(combination)
	// You cannot search through a string array in the database
	private String ConvertTags(String[] tags)
	{
		String tag = "";
		
		for (String string : tags)
			// Add each tag to a string, separate with _
			tag += string + "_";
		
		return tag;
	}

	// Generate random content
	@GetMapping("book/generate")
	public List<BookCopy> GenerateBooks()
	{
		List<BookCopy> books = new ArrayList<BookCopy>();
		
		if (bookTableRepo.findAll().size() == 0)
		{
			books = bookGenerator.GenerateBooks();
			
			for (BookCopy book : books)
			{
				int amount = (int) (Math.random() * 10 + 1);
				NewBook(book, amount);
			}
		}
		return books;
	}

	@GetMapping("employee/generate")
	public List<Employee> GenerateEmployee()
	{
		List<Employee> employees = new ArrayList<Employee>();
		if (employeeRepo.findAll().size() == 0)
		{
			employees = employeeGenerator.GenerateEmployees();

			for (Employee employee : employees)
			{
				employeeRepo.save(employee);
			}
		}

		return employees;
	}

	@GetMapping("history/generate")
	public List<LoanStatusLog> GenerateHistory()
	{
		List<LoanStatusLog> loanLogs = new ArrayList<LoanStatusLog>();
		if (employeeRepo.findAll().size() == 0)
		{
			GenerateEmployee();
		}
		if (loanRepo.findAll().size() == 0 && bookTableRepo.findAll().size() > 0 && employeeRepo.findAll().size() > 0)
		{
			loanLogs = historyGenerator.GenerateHistory(copyBookTableRepo.findAll(), employeeRepo.findAll());
			
			for (LoanStatusLog loanLog : loanLogs)
			{
				if (loanLog.getDateReturned() == null)
				{
					CopyBookTable copyBook = copyBookTableRepo.findByCopyId(loanLog.getCopyBook().getCopyId()).get();
					copyBook.setBookAvailable(false);
					copyBook.setBookLent(true);
					copyBookTableRepo.save(copyBook);
					
					copyBookController.ChangeAvailableCopies(copyBook.getMainBook(), -1);
					
					Employee employee = employeeRepo.findById(loanLog.getId()).get();
					employee.setBooksInPossession(employee.getBooksInPossession()+1);
					employeeRepo.save(employee);
				}
				
				loanRepo.save(loanLog);
			}
		}
		return loanLogs;
	}

	@GetMapping("reserve/generate")
	public List<ReservedBook> GenerateReservations()
	{
		List<Employee> employees = employeeRepo.findByEmployeeAdminFalse();
		List<LoanStatusLog> loans = loanRepo.findAll();
		List<BookTable> books = bookTableRepo.findAll();
		
		List<ReservedBook> reservations = new ArrayList<ReservedBook>();
		
		if (reserveRepo.findAll().size() == 0 && employees.size() > 0 && books.size() > 0)
		{
			reservations = reservationGenerator.GenerateReservations(loans, employees, books);
			
			for (ReservedBook reservation : reservations)
			{
				List<LoanStatusLog> employeeLoans = loanRepo.findByEmployeeIdAndDateLentNotNullAndDateReturnedNull(reservation.getEmployee().getId());
				
				boolean isUnique = true;
				
				for (LoanStatusLog loan : employeeLoans)
				{
					if (loan.getCopyBook().getMainBook().getId() == reservation.getBook().getId())
						isUnique = false;
				}
				
				if (isUnique)
				{
					reserveRepo.save(reservation);
				}
			}
		}
		
		return reservations;
	}

	@GetMapping("rating/generate")
	public List<EmployeeBookRating> GenerateRatings()
	{
		List<BookTable> books = bookTableRepo.findAll();
		List<Employee> employees = employeeRepo.findByEmployeeAdminFalse();
		
		List<EmployeeBookRating> ratings = new ArrayList<EmployeeBookRating>();
		
		if (rateRepo.findAll().size() == 0 && books.size() > 0 && employees.size() > 0)
		{
			ratings = ratingGenerator.GenerateRating(books, employees);
			
			for (EmployeeBookRating rating : ratings)
			{
				BookTable book = rating.getBook();
				Employee employee = rating.getEmployee();
				

				List<EmployeeBookRating> bookRating = book.getRatings();
				List<EmployeeBookRating> employeeRating = employee.getRatings();
				
				bookRating.add(rating);
				employeeRating.add(rating);
				
				book.setRatings(bookRating);
				employee.setRatings(employeeRating);

				rateRepo.save(rating);
				bookTableRepo.save(book);
				employeeRepo.save(employee);
			}
		}
		return ratings;
	}
}
