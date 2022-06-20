package com.example.backend.search;

public class CopyBookTableSearch
{
	private String copyId;
	private boolean bookAvailable;
	private boolean bookLent;
	private boolean bookArchived;
	private boolean bookReserved;
	
	// Getters & setters
	public String getCopyId()
	{
		return copyId;
	}
	public void setCopyId(String copyId)
	{
		this.copyId = copyId;
	}
	public boolean isBookAvailable()
	{
		return bookAvailable;
	}
	public void setBookAvailable(boolean bookAvailable)
	{
		this.bookAvailable = bookAvailable;
	}
	public boolean isBookLent()
	{
		return bookLent;
	}
	public void setBookLent(boolean bookLent)
	{
		this.bookLent = bookLent;
	}
	public boolean isBookArchived()
	{
		return bookArchived;
	}
	public void setBookArchived(boolean bookArchived)
	{
		this.bookArchived = bookArchived;
	}
	public boolean isBookReserved()
	{
		return bookReserved;
	}
	public void setBookReserved(boolean bookReserved)
	{
		this.bookReserved = bookReserved;
	}
}
