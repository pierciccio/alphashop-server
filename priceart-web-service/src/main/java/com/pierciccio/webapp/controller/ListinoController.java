package com.pierciccio.webapp.controller;

import java.util.Optional;

import javax.swing.text.html.Option;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pierciccio.webapp.entity.Listini;
import com.pierciccio.webapp.exception.BindingException;
import com.pierciccio.webapp.exception.NotFoundException;
import com.pierciccio.webapp.service.ListinoService;

@RestController
@RequestMapping("/api/listino")
public class ListinoController 
{
	private static final Logger logger = LoggerFactory.getLogger(ListinoController.class);

	@Autowired
	private ListinoService listiniService;
	
	@Autowired
	private ResourceBundleMessageSource errMessage;
	
	// ------------------- CERCA LISTINO X ID ------------------------------------
	@GetMapping(value = "/cerca/id/{idList}")
	public ResponseEntity<Optional<Listini>> getListById(@PathVariable("idList") String IdList)  
			throws NotFoundException
	{
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		logger.info("Otteniamo il Listino Numero: " + IdList);
		
		Optional<Listini> listini = listiniService.SelById(IdList);
		
		if (listini == null)
		{
			String ErrMsg = String.format("Il listino %s non è stato trovato!", IdList);
			
			logger.warn(ErrMsg);
			
			throw new NotFoundException(ErrMsg);
		}
		
		return new ResponseEntity<Optional<Listini>>(listini, HttpStatus.OK);
	}
	
	// ------------------- INSERISCI LISTINO ------------------------------------
	@RequestMapping(value = "/inserisci", method = RequestMethod.POST)
	public ResponseEntity<?> createList(@Valid @RequestBody Listini listino, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) 
			throws BindingException 
	{
		logger.info(String.format("Salviamo il listino", listino.getId()));
		
		if (bindingResult.hasErrors())
		{
			String MsgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			
			logger.warn(MsgErr);

			throw new BindingException(MsgErr);
		}
		 
		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();
		
		headers.setContentType(MediaType.APPLICATION_JSON);

		ObjectNode responseNode = mapper.createObjectNode();

		listiniService.InsListino(listino);
		
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Inserimento Listino " + listino.getId() + " Eseguito Con Successo");

		return new ResponseEntity<>(responseNode, headers, HttpStatus.CREATED);
	}
	
	// ------------------- ELIMINAZIONE LISTINO ------------------------------------
	@RequestMapping(value = "/elimina/{idList}", method = RequestMethod.DELETE, produces = "application/json" )
	public ResponseEntity<?> deleteList(@PathVariable("idList") String IdList)
		throws NotFoundException 
	{
		logger.info("Eliminiamo il listino " + IdList);
		
		Optional<Listini> listino = listiniService.SelById(IdList);
		
		if (!listino.isPresent())
		{
			String MsgErr = String.format("Listino %s non presente in anagrafica!",IdList);
			
			logger.warn(MsgErr);
			
			throw new NotFoundException(MsgErr);
		}
		
		listiniService.DelListino(listino.get());
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione Listino " + IdList + " Eseguita Con Successo");
		
		return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.OK);
				
	}

	 
}
