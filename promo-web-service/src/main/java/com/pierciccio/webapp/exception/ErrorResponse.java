package com.pierciccio.webapp.exception;

import lombok.Data;

@Data
public class ErrorResponse 
{
	private int codice;
	private String messaggio;
	
}
