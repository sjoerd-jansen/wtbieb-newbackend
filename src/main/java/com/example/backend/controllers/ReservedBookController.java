package com.example.backend.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.StringResponse;
import com.example.backend.dto.ReservedBookDTO;
import com.example.backend.repositories.BookTableRepository;
import com.example.backend.repositories.CopyBookTableRepository;
import com.example.backend.repositories.EmployeeRepository;
import com.example.backend.repositories.ReservedBookRepository;
import com.example.backend.tables.BookTable;
import com.example.backend.tables.CopyBookTable;
import com.example.backend.tables.Employee;
import com.example.backend.tables.ReservedBook;

@CrossOrigin(maxAge = 3600)
@RestController
public class ReservedBookController
{
	// Repositories
	@Autowired
	ReservedBookRepository reservedRepo;
	@Autowired
	BookTableRepository bookRepo;
	@Autowired
	CopyBookTableRepository copyRepo;
	@Autowired
	EmployeeRepository employeeRepo;
	
	// Controllers
	@Autowired
	CopyBookTableController copyController;
	
	@PostMapping("book/reserve/{bookId}/{employeeId}")
	@ResponseBody
	public StringResponse NewReservation(@PathVariable long bookId, @PathVariable long employeeId)
	{
		Optional<BookTable> book = bookRepo.findById(bookId);
		Optional<Employee> employee = employeeRepo.findById(employeeId);
		Optional<ReservedBook> checkReservation = reservedRepo.findByBookIdAndEmployeeId(bookId, employeeId);
		
		if (book.isPresent() && employee.isPresent() && checkReservation.isEmpty())
		{
			ReservedBook reservation = new ReservedBook();
			reservation.setBook(book.get());
			reservation.setEmployee(employee.get());
			
			reservedRepo.save(reservation);

			return StringResponse.NewResponse("Boek gereserveerd!");
		}
		else if (checkReservation.isPresent())
		{
			return StringResponse.NewResponse("Je hebt dit boek al gereserveerd.");
		}
		
		return StringResponse.NewResponse("Dit boek of deze medewerker bestaat niet. Neem aub contact op met support.");
	}
	
	@DeleteMapping("book/loan/reserved/{reservationId}/{copyId}")
	@ResponseBody
	public StringResponse LoanReservedBook(@PathVariable long reservationId, @PathVariable String copyId)
	{		
		ReservedBook reservation;
		CopyBookTable copyBook;
		
		if (copyRepo.findByCopyId(copyId).isPresent() && reservedRepo.findById(reservationId).isPresent())
		{
			copyBook = copyRepo.findByCopyId(copyId).get();
			reservation = reservedRepo.findById(reservationId).get();

			if (copyBook.getBookAvailable())
			{
				copyBook.setBookAvailable(false);
				copyBook.setBookLent(true);
				
				copyController.LoanReservation(copyBook, reservation.getEmployee());
				
				reservedRepo.delete(reservation);
				
				return StringResponse.NewResponse("Het boek " + copyBook.getMainBook().getBookTitle() + " is uitgeleend aan " + reservation.getEmployee().getEmployeeName());
			}
			else
			{
				return StringResponse.NewResponse("Dit exemplaar is niet beschikbaar. Kies een ander exemplaar of neem contact op met support.");
			}
		}
		
		return StringResponse.NewResponse("Deze reservering of dit exemplaar konden niet worden gevonden. Neem aub contact op met support.");
	}
	
	@DeleteMapping("book/reservation/{reservationId}/cancel")
	@ResponseBody
	public StringResponse CancelReservation(@PathVariable long reservationId)
	{
		Optional<ReservedBook> reservation = reservedRepo.findById(reservationId);
		
		if (reservation.isPresent())
		{
			reservedRepo.delete(reservation.get());
			return StringResponse.NewResponse("De reservering van " + reservation.get().getEmployee().getEmployeeName() + " voor het boek " + reservation.get().getBook().getBookTitle() + " is geannuleerd");
		}
		
		return StringResponse.NewResponse("Deze reservering kan niet worden gevonden. Neem aub contact op met support.");
	}
	
	@GetMapping("book/reservations/findall")
	public List<ReservedBookDTO> GetAllReservations()
	{
		List<ReservedBook> allReservations = reservedRepo.findAll();
		List<ReservedBookDTO> reservedBooks = new ArrayList<ReservedBookDTO>();

		for (ReservedBook book : allReservations)
		{
			ReservedBookDTO newBook = new ReservedBookDTO().CreateNewReservedBook(book);
			reservedBooks.add(newBook);
		}

		return reservedBooks;
	}
	
	@GetMapping("book/{employeeId}/myreservations")
	public List<ReservedBookDTO> GetEmployeeReservations(@PathVariable long employeeId)
	{
		List<ReservedBookDTO> reservedBooks = new ArrayList<ReservedBookDTO>();
		
		for (ReservedBook book : reservedRepo.findByEmployeeId(employeeId))
		{
			ReservedBookDTO newBook = new ReservedBookDTO().CreateNewReservedBook(book);
			reservedBooks.add(newBook);
		}
		
		return reservedBooks;
	}
	
	@GetMapping("book/{bookId}/bookreservations")
	public List<ReservedBookDTO> GetBookReservations(@PathVariable long bookId)
	{
		List<ReservedBookDTO> reservedBooks = new ArrayList<ReservedBookDTO>();
	
		for (ReservedBook book : reservedRepo.findByBookId(bookId))
		{
			ReservedBookDTO newBook = new ReservedBookDTO().CreateNewReservedBook(book);
			reservedBooks.add(newBook);
		}
	
		return reservedBooks;
	}
}
