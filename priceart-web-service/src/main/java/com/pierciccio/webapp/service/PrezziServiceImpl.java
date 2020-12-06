package com.pierciccio.webapp.service;

import com.pierciccio.webapp.entity.DettListini;
import com.pierciccio.webapp.repository.PrezziRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PrezziServiceImpl implements PrezziService
{
	@Autowired
	PrezziRepository prezziRepository;

	@Override
	@Cacheable(value = "prezzo", key = "#CodArt.concat('-').concat(#Listino)", sync = true)
	public DettListini SelPrezzo(String CodArt, String Listino)
	{
		return prezziRepository.SelByCodArtAndList(CodArt, Listino);
	}
	
	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = "prezzo", key = "#CodArt.concat('-').concat(#Listino)")
	})
	public void DelPrezzo(String CodArt, String IdList) 
	{
		prezziRepository.DelRowDettList(CodArt, IdList);
	}

}
