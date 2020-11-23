package com.pierciccio.webapp.service;

import com.pierciccio.webapp.entity.Listini;

import java.util.Optional;

public interface ListinoService 
{
	public Optional<Listini> SelById(String Id);
	
	public void InsListino(Listini listino);

	public void DelListino(Listini listino);
}
