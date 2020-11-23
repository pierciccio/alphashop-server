package com.pierciccio.webapp.service;

import java.util.List;

import com.pierciccio.webapp.repository.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pierciccio.webapp.model.Utenti;

@Service
@Transactional(readOnly = true)
public class UtentiServiceImpl implements UtentiService
{

	@Autowired
	UtentiRepository utentiRepository;
	
	@Override
	public List<Utenti> SelTutti()
	{
		return utentiRepository.findAll();
	}

	@Override
	public Utenti SelUser(String UserId)
	{
		return utentiRepository.findByUserId(UserId);
	}


	@Override
	public void Save(Utenti utente)
	{
		utentiRepository.save(utente);
	}

	@Override
	public void Delete(Utenti utente)
	{
		utentiRepository.delete(utente);
	}

}
