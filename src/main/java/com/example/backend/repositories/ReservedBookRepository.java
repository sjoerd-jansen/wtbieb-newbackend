package com.example.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.tables.ReservedBook;

public interface ReservedBookRepository extends JpaRepository<ReservedBook, Long>
{
	Optional<ReservedBook> findByBookIdAndEmployeeId(long bookId, long employeeId);
	List<ReservedBook> findByEmployeeId(long employeeId);
	List<ReservedBook> findByBookId(long bookId);
}
