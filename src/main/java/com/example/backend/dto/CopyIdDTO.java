package com.example.backend.dto;

public class CopyIdDTO
{
	private String copyId;
	private boolean copyAvailable;

	public String getCopyId()
	{
		return copyId;
	}

	public void setCopyId(String copyId)
	{
		this.copyId = copyId;
	}

	public boolean getCopyAvailable()
	{
		return copyAvailable;
	}

	public void setCopyAvailable(boolean copyAvailable)
	{
		this.copyAvailable = copyAvailable;
	}	
}
