package com.example.backend.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.StringResponse;
import com.example.backend.dto.BugReportDTO;
import com.example.backend.repositories.BookTableRepository;
import com.example.backend.repositories.EmployeeRepository;
import com.example.backend.tables.BookTable;
import com.example.backend.tables.Employee;

@CrossOrigin(maxAge = 3600)
@RestController
public class ContactController
{
	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private BookTableRepository bookRepo;
	
	@PostMapping("/bugreport")
	public StringResponse SendBugReport(@RequestBody BugReportDTO bugReport)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(System.getenv("emai_bieb"));
		message.setFrom(bugReport.getContactEmail());
		message.setSubject(bugReport.getBugReportTitle());
		message.setText(bugReport.getBugReportMessage());
		
		emailSender.send(message);
		
		System.out.println("Bug report title: " + bugReport.getBugReportTitle());
		System.out.println("Bug report message: " + bugReport.getBugReportMessage());
		System.out.println("Bug report email: " + bugReport.getContactEmail());
		
		return StringResponse.NewResponse("Een bug report is verstuurd naar support");
	}
	
	@PostMapping("/returnemail/{bookId}/{employeeId}")
	public StringResponse ReturnBookRequest(@PathVariable long bookId, @PathVariable long employeeId)
	{
		// Find book and employee
		Optional<BookTable> optionalBook = bookRepo.findById(bookId);
		Optional<Employee> optionalEmployee = employeeRepo.findById(employeeId);
		
		// Check if they exist in the database
		if (optionalBook.isPresent() && optionalEmployee.isPresent())
		{
			// If they exist, set the book and employee to grab the data easily
			BookTable book = optionalBook.get();
			Employee employee = optionalEmployee.get();
			
			// Start creating the message
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(employee.getEmployeeEmail());
			message.setFrom(System.getenv("email_bieb"));
			message.setSubject("Boek " + book.getBookTitle() + " lang in bezit");
			message.setText(ReturnMessage(employee.getEmployeeName(), book.getBookTitle()));

			// Send the email
			emailSender.send(message);
			return StringResponse.NewResponse("Een verzoek om het boek " + book.getBookTitle() + " in te leveren is verstuurd naar " + employee.getEmployeeName());
		}
		
		return StringResponse.NewResponse("Het boek of de medewerker konden niet worden gevonden. Probeer opnieuw of neem contact op met support");
	}
	
	private String ReturnMessage(String employeeName, String bookTitle)
	{
		String returnMessage = "Hoi " + employeeName + ",\r\n"
				+ "\r\n"
				+ "je hebt het boek " + bookTitle + " al langere tijd in het bezit.\r\n"
						+ "Indien je deze niet meer nodig hebt zou ik je willen verzoeken deze zo snel mogelijk te retourneren in onze bibliotheek zodat andere collega's er ook gebruik van kunnen maken.\r\n"
						+ "Mocht je het boek nog nodig hebben, laat het dan even weten door te reageren op deze email.\r\n"
						+ "\r\n"
						+ "Met vriendelijke groeten,\r\n"
						+ "Uw heer en meester, overlord Jorrit";
		
		return returnMessage;
	}
}