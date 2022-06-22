package com.example.backend;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.tables.BookTable;
import com.example.backend.tables.Employee;
import com.example.backend.tables.LoanStatusLog;
import com.example.backend.tables.ReservedBook;

public class RandomReservationGenerator
{
	public List<ReservedBook> GenerateReservations(List<LoanStatusLog> loans, List<Employee> employees, List<BookTable> books)
	{
		List<ReservedBook> reservations = new ArrayList<ReservedBook>();
		
		for (Employee employee : employees)
		{
			List<Long> employeeBooks = new ArrayList<Long>();
			
			int randBooks = (int)(Math.random() * 5 + 1);
			
			for (int i=0; i<randBooks; i++)
			{
				BookTable book = GetBook(books);
				
				if (!employeeBooks.contains(book.getId()))
				{
					employeeBooks.add(book.getId());
					ReservedBook reservation = Reservation(book, employee);
					reservations.add(reservation);
				}
			}
		}
		
		return reservations;
	}
	
	private ReservedBook Reservation(BookTable book, Employee employee)
	{
		ReservedBook reservation = new ReservedBook();
		
		reservation.setBook(book);
		reservation.setEmployee(employee);
		
		return reservation;
	}
	
	private BookTable GetBook(List<BookTable> books)
	{
		int randomBook = (int)(Math.random() * books.size());
		
		return books.get(randomBook);
	}
}
