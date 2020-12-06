package com.pierciccio.webapp.service;

import com.pierciccio.webapp.model.Utenti;

import java.util.List;

public interface UtentiService
{
	public List<Utenti> SelTutti();
	
	public Utenti SelUser(String UserId);
	
	public void Save(Utenti utente);
	
	public void Delete(Utenti utente);
	
}
