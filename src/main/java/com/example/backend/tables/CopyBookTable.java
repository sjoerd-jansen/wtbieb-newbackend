package com.example.backend.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Boek_exemplaren")
public class CopyBookTable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	BookTable mainBook;
	
	@Column(nullable = false)
	private String copyId;

	@Column(nullable = false)
	private boolean bookAvailable;

	@Column(nullable = false)
	private boolean bookLent;

	@Column(nullable = false)
	private boolean bookArchived;

	// Getters & setters
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}

	public String getCopyId()
	{
		return copyId;
	}
	public void setCopyId(String copyId)
	{
		this.copyId = copyId;
	}

	public boolean getBookAvailable()
	{
		return bookAvailable;
	}
	public void setBookAvailable(boolean bookAvailable)
	{
		this.bookAvailable = bookAvailable;
	}

	public boolean getBookLent()
	{
		return bookLent;
	}
	public void setBookLent(boolean bookLent)
	{
		this.bookLent = bookLent;
	}

	public boolean getBookArchived()
	{
		return bookArchived;
	}
	public void setBookArchived(boolean bookArchived)
	{
		this.bookArchived = bookArchived;
	}

	public BookTable getMainBook()
	{
		return mainBook;
	}
	public void setMainBook(BookTable mainBook)
	{
		this.mainBook = mainBook;
	}
}
