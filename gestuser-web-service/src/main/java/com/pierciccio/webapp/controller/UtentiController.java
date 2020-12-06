package com.pierciccio.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pierciccio.webapp.exception.BindingException;
import com.pierciccio.webapp.exception.NotFoundException;
import com.pierciccio.webapp.model.Utenti;
import com.pierciccio.webapp.service.UtentiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/utenti")
public class UtentiController
{
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	UtentiService utentiService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private ResourceBundleMessageSource errMessage;
	
	@GetMapping(value = "/cerca/tutti")
	public List<Utenti> getAllUser()
	{
		LOG.info("Otteniamo tutti gli utenti");

		return utentiService.SelTutti();
	}
	
	@GetMapping(value = "/cerca/userid/{userId}")
	public Utenti getUtente(@PathVariable("userId") String UserId) 
	{
		LOG.info("Otteniamo l'utente " + UserId);
		
		Utenti retVal = utentiService.SelUser(UserId);
		
		return retVal;
	}
	
	// ------------------- INSERIMENTO UTENTE ------------------------------------
	@PostMapping(value = "/inserisci")
	public ResponseEntity<?> addNewUser(@Valid @RequestBody Utenti utente, 
		BindingResult bindingResult) throws BindingException
	{
		LOG.info("Inserimento Nuovo Utente");

		if (bindingResult.hasErrors())
		{
			String MsgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			
			LOG.warn(MsgErr);

			throw new BindingException(MsgErr);
		}
		
		String encodedPassword = passwordEncoder.encode(utente.getPassword());
		utente.setPassword(encodedPassword);
		utentiService.Save(utente);

		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();

		headers.setContentType(MediaType.APPLICATION_JSON);

		ObjectNode responseNode = mapper.createObjectNode();

		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Inserimento Utente " + utente.getUserId() + " Eseguita Con Successo"); 
		
		return new ResponseEntity<>(responseNode, headers, HttpStatus.CREATED);
	}

	// ------------------- ELIMINAZIONE UTENTE ------------------------------------
	@DeleteMapping(value = "/elimina/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") String UserId)
			throws NotFoundException
	{
		LOG.info("Eliminiamo l'utente con id " + UserId);

		Utenti utente = utentiService.SelUser(UserId);

		if (utente == null)
		{
			String MsgErr = String.format("Utente %s non presente in anagrafica! ",UserId);
			
			LOG.warn(MsgErr);
			
			throw new NotFoundException(MsgErr);
		}

		utentiService.Delete(utente);
		
		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();

		headers.setContentType(MediaType.APPLICATION_JSON);

		ObjectNode responseNode = mapper.createObjectNode();

		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione Utente " + UserId + " Eseguita Con Successo");

		return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
	}
}
