package com.pierciccio.webapp.controller;

import com.pierciccio.webapp.entities.Articoli;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="ArticoliWebService", url="localhost:5051") //, configuration = FeignClientConfiguration.class)
public interface ArticoliClient 
{
	@GetMapping(value = "api/articoli/noauth/cerca/codice/{codart}")
    public Articoli getArticolo(@PathVariable("codart") String CodArt);
	
}
