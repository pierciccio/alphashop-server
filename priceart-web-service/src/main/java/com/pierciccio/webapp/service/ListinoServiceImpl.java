package com.pierciccio.webapp.service;

import com.pierciccio.webapp.entity.Listini;
import com.pierciccio.webapp.repository.ListinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ListinoServiceImpl implements ListinoService
{
	@Autowired
	ListinoRepository listinoRepository;

	@Override
	public void InsListino(Listini listino) 
	{
		listinoRepository.save(listino);
	}

	@Override
	public void DelListino(Listini listino) 
	{
		listinoRepository.delete(listino);
	}

	@Override
	public Optional<Listini> SelById(String Id) 
	{
		return listinoRepository.findById(Id);
	}
	
	
}
