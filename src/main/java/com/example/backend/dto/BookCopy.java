package com.example.backend.dto;

import com.example.backend.tables.CopyBookTable;

public class BookCopy extends Book
{
	private String copyId;
	private boolean copyAvailable;
	private boolean copyLent;
	private boolean copyArchived;
	
	
	public String getCopyId()
	{
		return copyId;
	}
	public void setCopyId(String copyId)
	{
		this.copyId = copyId;
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
	
	// Create new book copy to send to front-end
	public BookCopy CreateNewBookCopy(CopyBookTable copyBook)
	{
		// Create and instantiate object
		BookCopy bookCopy = new BookCopy();
		
		// Set variables from the copy table (if it exists)
		if (copyBook != null)
		{
			bookCopy.setCopyId(copyBook.getCopyId());
			bookCopy.setCopyAvailable(copyBook.getBookAvailable());
			bookCopy.setCopyLent(copyBook.getBookLent());
			bookCopy.setCopyArchived(copyBook.getBookArchived());
			bookCopy.setBookId(copyBook.getMainBook().getId());
			bookCopy.setBookTitle(copyBook.getMainBook().getBookTitle());
			bookCopy.setBookAuthor(copyBook.getMainBook().getBookAuthor());
			bookCopy.setBookIsbn(copyBook.getMainBook().getBookIsbn());
		}
		
		return bookCopy;
	}
}
