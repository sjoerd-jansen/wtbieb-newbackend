package com.example.backend;

public class StringResponse
{
	private String response;

	public String getResponse()
	{
		return response;
	}

	public void setResponse(String response)
	{
		this.response = response;
	}
	
	public static StringResponse NewResponse(String str)
	{
		StringResponse strResponse = new StringResponse();
		strResponse.setResponse(str);
		return strResponse;
	}
}
