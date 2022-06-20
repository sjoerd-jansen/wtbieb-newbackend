package com.example.backend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.BugReportDTO;

@CrossOrigin(maxAge = 3600)
@RestController
public class BugReportController
{
	@PostMapping("/bugreport")
	public boolean SendBugReport(@RequestBody BugReportDTO bugReport)
	{
		System.out.println("Bug report title: " + bugReport.getBugReportTitle());
		System.out.println("Bug report message: " + bugReport.getBugReportMessage());
		System.out.println("Bug report email: " + bugReport.getContactEmail());
		
		return true;
	}
}