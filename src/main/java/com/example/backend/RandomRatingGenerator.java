package com.example.backend;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.tables.BookTable;
import com.example.backend.tables.Employee;
import com.example.backend.tables.EmployeeBookRating;

public class RandomRatingGenerator
{
	public List<EmployeeBookRating> GenerateRating(List<BookTable> books, List<Employee> employees)
	{
		List<EmployeeBookRating> ratings = new ArrayList<EmployeeBookRating>();
		
		for (Employee employee : employees)
		{
			for (BookTable book : books)
			{
				if (Math.random() > 0.4f)
				{
					EmployeeBookRating rating = new EmployeeBookRating();
					rating.setBook(book);
					rating.setEmployee(employee);
					rating.setRating(GetRating());
					
					ratings.add(rating);
				}				
			}
		}
		
		return ratings;
	}
	
	private int GetRating()
	{
		int rate = 0;
		
		rate = (int)((Math.random() * 5) + 1);
		if (rate > 5)
			rate = 5;

		
		return rate;
	}
}
