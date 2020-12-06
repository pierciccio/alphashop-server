package com.pierciccio.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pierciccio.webapp.appconf.AppConfig;
import com.pierciccio.webapp.entity.DettListini;
import com.pierciccio.webapp.service.PrezziService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/prezzi")
public class PrezziController
{
	private static final Logger logger = LoggerFactory.getLogger(PrezziController.class);

	@Autowired
	private PrezziService prezziService;
	
	@Autowired
	private AppConfig Config;
	
	// ------------------- SELECT PREZZO CODART SENZA AUTENTICAZIONE ------------------------------------
	@GetMapping(value = {"/noauth/{codart}/{idlist}", "noauth/{codart}"})
	public double getPriceCodArtNoAuth(@PathVariable("codart") String CodArt, @PathVariable("idlist") Optional<String> optIdList)  
	{
		double retVal = 0;

		String IdList = (optIdList.isPresent()) ? optIdList.get() : Config.getListino();
		
		logger.info("Listino di Riferimento: " + IdList);
		
		DettListini prezzo =  prezziService.SelPrezzo(CodArt, IdList);
		
		if (prezzo != null)
		{
			logger.info("Prezzo Articolo: " + prezzo.getPrezzo());
			retVal = prezzo.getPrezzo();
		}
		else
		{
			logger.warn("Prezzo Articolo Assente!!");
		}

		return retVal;
	}
	
	// ------------------- SELECT PREZZO CODART ------------------------------------
	@GetMapping(value = {"/{codart}/{idlist}", "/{codart}"})
	public double getPriceCodArt(@PathVariable("codart") String CodArt, @PathVariable("idlist") Optional<String> optIdList)  
	{
		double retVal = 0;

		String IdList = (optIdList.isPresent()) ? optIdList.get() : Config.getListino();
		
		logger.info("Listino di Riferimento: " + IdList);
		
		DettListini prezzo =  prezziService.SelPrezzo(CodArt, IdList);
		
		if (prezzo != null)
		{
			logger.info("Prezzo Articolo: " + prezzo.getPrezzo());
			retVal = prezzo.getPrezzo();
		}
		else
		{
			logger.warn("Prezzo Articolo Assente!!");
		}

		return retVal;
	}
	
	// ------------------- DELETE PREZZO LISTINO ------------------------------------
	@RequestMapping(value = "/elimina/{codart}/{idlist}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePrice(@PathVariable("codart") String CodArt, @PathVariable("idlist") String IdList)
	{
		logger.info(String.format("Eliminazione prezzo listino %s dell'articolo %s",IdList,CodArt));

		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();

		headers.setContentType(MediaType.APPLICATION_JSON);

		ObjectNode responseNode = mapper.createObjectNode();

		prezziService.DelPrezzo(CodArt, IdList);

		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione Prezzo Eseguita Con Successo");
		
		logger.info("Eliminazione Prezzo Eseguita Con Successo");

		return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
	}
	

}
