package com.pierciccio.webapp.service;

import java.util.List;

import com.pierciccio.webapp.model.Utenti;
 
public interface UtentiService
{
	public List<Utenti> SelTutti();
	
	public Utenti SelUser(String UserId);
	
	public void Save(Utenti utente);
	
	public void Delete(Utenti utente);
	
}
