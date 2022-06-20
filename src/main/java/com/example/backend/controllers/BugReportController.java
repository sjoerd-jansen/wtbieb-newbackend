package com.example.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.BugReportDTO;

@CrossOrigin(maxAge = 3600)
@RestController
public class BugReportController
{
	@Autowired
	private JavaMailSender emailSender;
	
	@PostMapping("/bugreport")
	public boolean SendBugReport(@RequestBody BugReportDTO bugReport)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("overlordjorrit@wt.nl");
		message.setFrom(bugReport.getContactEmail());
		message.setSubject(bugReport.getBugReportTitle());
		message.setText(bugReport.getBugReportMessage());
		
		emailSender.send(message);
		
		System.out.println("Bug report title: " + bugReport.getBugReportTitle());
		System.out.println("Bug report message: " + bugReport.getBugReportMessage());
		System.out.println("Bug report email: " + bugReport.getContactEmail());
		
		return true;
	}
}