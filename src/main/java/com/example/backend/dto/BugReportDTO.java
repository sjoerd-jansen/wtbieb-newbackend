package com.example.backend.dto;

public class BugReportDTO
{
	private String bugReportTitle;
	private String bugReportMessage;
	private String contactEmail;
	
	public String getBugReportTitle()
	{
		return bugReportTitle;
	}
	public void setBugReportTitle(String bugReportTitle)
	{
		this.bugReportTitle = bugReportTitle;
	}
	
	public String getBugReportMessage()
	{
		return bugReportMessage;
	}
	public void setBugReportMessage(String bugReportMessage)
	{
		this.bugReportMessage = bugReportMessage;
	}
	
	public String getContactEmail()
	{
		return contactEmail;
	}
	public void setContactEmail(String contactEmail)
	{
		this.contactEmail = contactEmail;
	}
	
}
