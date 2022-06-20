package com.example.backend.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.tables.BookTable;

public interface BookTableRepository extends JpaRepository<BookTable, Long>
{
	// Find book by ISBN
	Optional<BookTable> findByBookIsbn(String isbn);
	
	// Find book if title, author, ISBN or a tag contains the input
	List<BookTable> findByBookTitleContainsOrBookAuthorContainsOrBookIsbnContainsOrBookTagsContains(String title, String author, String isbn, String tags);
}
