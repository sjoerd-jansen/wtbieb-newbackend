package com.example.backend.dto;

import java.time.LocalDate;

import com.example.backend.tables.LoanStatusLog;

public class LoanedCopy
{
	private String copyId;
	private String bookTitle;
	private String bookAuthor;
	private String bookIsbn;
	private boolean copyAvailable;
	private boolean copyLent;
	private boolean copyArchived;
	private long employeeId;
	private String employeeName;
	private LocalDate dateLent;
	private LocalDate dateReturned;
	
	public String getCopyId()
	{
		return copyId;
	}
	public void setCopyId(String copyId)
	{
		this.copyId = copyId;
	}
	
	public String getBookTitle()
	{
		return bookTitle;
	}
	public void setBookTitle(String bookTitle)
	{
		this.bookTitle = bookTitle;
	}
	
	public String getBookAuthor()
	{
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor)
	{
		this.bookAuthor = bookAuthor;
	}
	
	public String getBookIsbn()
	{
		return bookIsbn;
	}
	public void setBookIsbn(String bookIsbn)
	{
		this.bookIsbn = bookIsbn;
	}
	
	public boolean getCopyAvailable()
	{
		return copyAvailable;
	}
	public void setCopyAvailable(boolean copyAvailable)
	{
		this.copyAvailable = copyAvailable;
	}
	
	public boolean getCopyLent()
	{
		return copyLent;
	}
	public void setCopyLent(boolean copyLent)
	{
		this.copyLent = copyLent;
	}
	
	public boolean getCopyArchived()
	{
		return copyArchived;
	}
	public void setCopyArchived(boolean copyArchived)
	{
		this.copyArchived = copyArchived;
	}
	
	public long getEmployeeId()
	{
		return employeeId;
	}
	public void setEmployeeId(long employeeId)
	{
		this.employeeId = employeeId;
	}
	
	public String getEmployeeName()
	{
		return employeeName;
	}
	public void setEmployeeName(String employeeName)
	{
		this.employeeName = employeeName;
	}
	
	public LocalDate getDateLent()
	{
		return dateLent;
	}
	public void setDateLent(LocalDate dateLent)
	{
		this.dateLent = dateLent;
	}
	
	public LocalDate getDateReturned()
	{
		return dateReturned;
	}
	public void setDateReturned(LocalDate dateReturned)
	{
		this.dateReturned = dateReturned;
	}
	
	// Create new book copy to send to front-end
	public LoanedCopy CreateNewLoanedCopy(LoanStatusLog loanLog)
	{
		// Create and instantiate object
		LoanedCopy loanedCopy = new LoanedCopy();
		
		// Set variables from the loan status log table (if it exists)
		if (loanLog != null)
		{
			loanedCopy.setCopyId(loanLog.getCopyBook().getCopyId());
			loanedCopy.setBookTitle(loanLog.getCopyBook().getMainBook().getBookTitle());
			loanedCopy.setBookAuthor(loanLog.getCopyBook().getMainBook().getBookAuthor());
			loanedCopy.setBookIsbn(loanLog.getCopyBook().getMainBook().getBookIsbn());
			loanedCopy.setCopyAvailable(loanLog.getCopyBook().getBookAvailable());
			loanedCopy.setCopyLent(loanLog.getCopyBook().getBookLent());
			loanedCopy.setCopyArchived(loanLog.getCopyBook().getBookArchived());
			loanedCopy.setEmployeeId(loanLog.getEmployee().getId());
			loanedCopy.setEmployeeName(loanLog.getEmployee().getEmployeeName());
			loanedCopy.setDateLent(loanLog.getDateLent());
			loanedCopy.setDateReturned(loanLog.getDateReturned());
		}
		
		return loanedCopy;
	}
}
