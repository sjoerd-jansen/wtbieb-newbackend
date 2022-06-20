package com.example.backend.search;

public class BookTableSearch
{
	private String bookTitle;
	private String bookAuthor;
	private String bookIsbn;
	private String[] bookTags;
	
	// Getters & setters
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
	
	public String[] getBookTags()
	{
		return bookTags;
	}
	public void setBookTags(String[] bookTags)
	{
		this.bookTags = bookTags;
	}
	
	
}
