package com.example.backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.example.backend.tables.CopyBookTable;
import com.example.backend.tables.Employee;
import com.example.backend.tables.LoanStatusLog;

public class RandomBookHistoryGenerator
{
	List<String> lentBooks = new ArrayList<String>();
	
	public List<LoanStatusLog> GenerateHistory(List<CopyBookTable> copyBookTable, List<Employee> employees)
	{		
		List<LoanStatusLog> loanLogs = new ArrayList<LoanStatusLog>();
		
		int amountOfHistory =  (int)(Math.random() * (150-30) + 30);
		
		for (int i = 0; i < amountOfHistory; i++)
		{
			LoanStatusLog loanLog = new LoanStatusLog();
			
			LocalDate startDate = GenerateDate(LocalDate.EPOCH, LocalDate.now());
			LocalDate endDate = GenerateDate(startDate, LocalDate.now());
			
			loanLog.setCopyBook(GetCopy(copyBookTable));
			loanLog.setEmployee(GetEmployee(employees));
			loanLog.setDateLent(startDate);
			
			if (IsLent(loanLog.getCopyBook().getCopyId()))
				loanLog.setDateReturned(endDate);
			
			loanLogs.add(loanLog);
		}
		
		return loanLogs;
	}
	
	private LocalDate GenerateDate(LocalDate startInclusive, LocalDate endExclusive)
	{
	    long startEpochDay = startInclusive.toEpochDay();
	    long endEpochDay = endExclusive.toEpochDay();
	    long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
	    
	    return LocalDate.ofEpochDay(randomDay);
	}
	
	private CopyBookTable GetCopy(List<CopyBookTable> copyBookTable)
	{
		List<CopyBookTable> copyTable = copyBookTable;
		
		int randomBook = (int)(Math.random() * copyTable.size());
		
		return copyTable.get(randomBook);
	}
	
	private boolean IsLent(String copyId)
	{
		for (String book : lentBooks)
		{
			if (book.contains(copyId))
			{
				return true;
			}
		}
		if (Math.random() > 0.8)
		{
			lentBooks.add(copyId);
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private Employee GetEmployee(List<Employee> employeeList)
	{
		List<Employee> employees = employeeList;
		
		int randomEmployee = (int)(Math.random() * employees.size());
		
		return employees.get(randomEmployee);
	}
}
