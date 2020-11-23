package com.pierciccio.webapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pierciccio.webapp.model.Utenti;
 
public interface UtentiRepository extends MongoRepository<Utenti, String> 
{
	public Utenti findByUserId(String UserId);
}
