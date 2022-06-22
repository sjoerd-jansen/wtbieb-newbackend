package com.example.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.StringResponse;
import com.example.backend.repositories.BookTableRepository;
import com.example.backend.repositories.EmployeeBookRatingRepository;
import com.example.backend.repositories.EmployeeRepository;
import com.example.backend.tables.BookTable;
import com.example.backend.tables.Employee;
import com.example.backend.tables.EmployeeBookRating;

@CrossOrigin(maxAge = 3600)
@RestController
public class EmployeeBookRatingController
{
	@Autowired
	private BookTableRepository bookRepo;
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private EmployeeBookRatingRepository ratingRepo;

	// Find average rating of book
	@GetMapping("book/{bookId}/rating/average")
	public float AverageRating(@PathVariable long bookId)
	{
		// Set average rating variable
		float averageRating = 0.0f;
		
		// Find book
		Optional<BookTable> optional = bookRepo.findById(bookId);
	
		if (optional.isPresent())
		{
			BookTable book = optional.get();
			
			if (book.getRatings().size() > 0)
			{
				// Check the total ratings combined
				float total = 0.0f;
				
				for (EmployeeBookRating rating : book.getRatings())
					total += rating.getRating();

				// Calculate average rating
				averageRating = total/book.getRatings().size();
			}
		}

		// Return average rating
		return averageRating;
	}
	
	// Add rating to a book
	@PostMapping("book/{bookId}/{employeeId}/rating")
	public StringResponse AddRating(@PathVariable long bookId, @PathVariable long employeeId, @RequestParam int rating)
	{
		Optional<BookTable> optionalBook = bookRepo.findById(bookId);
		Optional<Employee> optionalEmployee = employeeRepo.findById(employeeId);
		Optional<EmployeeBookRating> optionalRating = ratingRepo.findByEmployeeIdAndBookId(employeeId, bookId);
		
		if (optionalBook.isPresent() && optionalEmployee.isPresent() && optionalRating.isEmpty())
		{
			BookTable book = optionalBook.get();
			Employee employee = optionalEmployee.get();
			
			EmployeeBookRating newRating = new EmployeeBookRating();
			newRating.setBook(book);
			newRating.setEmployee(employee);
			newRating.setRating(rating);
			
			List<EmployeeBookRating> bookRating = book.getRatings();
			List<EmployeeBookRating> employeeRating = employee.getRatings();
			
			bookRating.add(newRating);
			employeeRating.add(newRating);
			
			book.setRatings(bookRating);
			employee.setRatings(employeeRating);
			
			ratingRepo.save(newRating);
			bookRepo.save(book);
			employeeRepo.save(employee);
			return StringResponse.NewResponse("Je hebt het boek " + book.getBookTitle() + " een rating gegeven van " + rating);
		}
		else if (optionalRating.isPresent())
		{
			return StringResponse.NewResponse("Je hebt dit boek al een rating van " + optionalRating.get().getRating() + " gegeven.");
		}
		
		return StringResponse.NewResponse("Dit boek kan niet worden gevonden. Kies een ander boek of neem contact op met support.");
	}
	
	@GetMapping("book/rating/findall")
	public List<EmployeeBookRating> AllRatings()
	{
		return ratingRepo.findAll();
	}
}
