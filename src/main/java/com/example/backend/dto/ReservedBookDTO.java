package com.example.backend.dto;

import com.example.backend.tables.ReservedBook;

public class ReservedBookDTO
{
	private long reservationId;
	private long bookId;
	private String bookTitle;
	private String bookAuthor;
	private String bookIsbn;
	private int copiesAvailable;
	private long employeeId;
	private String employeeName;
	

	public long getReservationId()
	{
		return reservationId;
	}
	public void setReservationId(long reservationId)
	{
		this.reservationId = reservationId;
	}
	
	public long getBookId()
	{
		return bookId;
	}
	public void setBookId(long bookId)
	{
		this.bookId = bookId;
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
	
	public int getCopiesAvailable()
	{
		return copiesAvailable;
	}
	public void setCopiesAvailable(int copiesAvailable)
	{
		this.copiesAvailable = copiesAvailable;
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
	
	public ReservedBookDTO CreateNewReservedBook(ReservedBook reservedBook)
	{
		ReservedBookDTO book = new ReservedBookDTO();
		
		if (reservedBook != null)
		{
			book.setReservationId(reservedBook.getId());
			book.setBookId(reservedBook.getBook().getId());
			book.setBookTitle(reservedBook.getBook().getBookTitle());
			book.setBookAuthor(reservedBook.getBook().getBookAuthor());
			book.setBookIsbn(reservedBook.getBook().getBookIsbn());
			book.setCopiesAvailable(reservedBook.getBook().getCopiesAvailable());
			book.setEmployeeId(reservedBook.getEmployee().getId());
			book.setEmployeeName(reservedBook.getEmployee().getEmployeeName());
		}
		
		return book;
	}
}
