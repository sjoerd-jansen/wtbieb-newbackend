package com.example.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.tables.CopyBookTable;

public interface CopyBookTableRepository extends JpaRepository<CopyBookTable, Long>
{
	// Find single copy
	Optional<CopyBookTable> findByCopyId(String copyId);
	
	// Find all copies of a book
	List<CopyBookTable> findByCopyIdStartsWith(String mainId);
	
	// Find all available books
	List<CopyBookTable> findByBookAvailableTrueAndMainBookId(long mainId);
	
	// Find all archived books
	List<CopyBookTable> findByBookArchivedTrue();
	
	// Find all copies of main book
	List<CopyBookTable> findByMainBookId(long mainId);
}
