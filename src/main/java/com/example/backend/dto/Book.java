package com.example.backend.dto;

import com.example.backend.tables.BookTable;

public class Book
{
	private long bookId;
	private String bookTitle;
	private String bookAuthor;
	private String bookIsbn;
	private int bookCopies;
	private String[] bookTags;
	private String bookCover;
	
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
	public int getBookCopies()
	{
		return bookCopies;
	}
	public void setBookCopies(int bookCopies)
	{
		this.bookCopies = bookCopies;
	}

	public String[] getBookTags()
	{
		return bookTags;
	}
	public void setBookTags(String[] bookTags)
	{
		this.bookTags = bookTags;
	}
	
	public String getBookCover()
	{
		return bookCover;
	}
	public void setBookCover(String bookCover)
	{
		this.bookCover = bookCover;
	}
	
	// Create new book copy to send to front-end
	public Book CreateNewBook(BookTable bookTable)
	{
		// Create and instantiate object
		Book book = new Book();
		
		// Set variables from the main book (if it exists)
		if (bookTable != null)
		{
			book.setBookId(bookTable.getId());
			book.setBookTitle(bookTable.getBookTitle());
			book.setBookAuthor(bookTable.getBookAuthor());
			book.setBookIsbn(bookTable.getBookIsbn());
			book.setBookCopies(bookTable.getCopiesAvailable());
			if (bookTable.getBookTags().length() > 0)
				book.setBookTags(bookTable.getBookTags().split("_"));
			book.setBookCover(bookTable.getBookCover());
		}
		
		return book;
	}
}
