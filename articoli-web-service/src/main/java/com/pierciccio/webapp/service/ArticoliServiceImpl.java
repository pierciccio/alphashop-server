package com.pierciccio.webapp.service;

import com.pierciccio.webapp.entity.Articoli;
import com.pierciccio.webapp.repository.ArticoliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = {"articoli"})
public class ArticoliServiceImpl implements ArticoliService
{
	@Autowired
	ArticoliRepository articoliRepository;
	
	@Override
	@Cacheable
	public Iterable<Articoli> SelTutti()
	{
		return articoliRepository.findAll();
	}

	@Override
	@Cacheable
	public List<Articoli> SelByDescrizione(String descrizione, Pageable pageable)
	{
		return articoliRepository.findByDescrizioneLike(descrizione, pageable);
	}

	@Override
	@Cacheable
	public List<Articoli> SelByDescrizione(String descrizione)
	{
		return articoliRepository.SelByDescrizioneLike(descrizione);
	}
	
	@Override
	@Cacheable(value = "articolo", key = "#codArt", sync = true)
	public Articoli SelByCodArt(String codArt)
	{
		return articoliRepository.findByCodArt(codArt);
	}

	@Override
	@Transactional
	@Caching(evict = {
			@CacheEvict(cacheNames = "articoli", allEntries = true),
			@CacheEvict(cacheNames = "articolo", key = "#articolo.codArt")
	})
	public void DelArticolo(Articoli articolo)
	{
		articoliRepository.delete(articolo);
	}

	@Override
	@Transactional
	@Caching(evict = {
			@CacheEvict(cacheNames = "articoli", allEntries = true),
			@CacheEvict(cacheNames = "articolo", key = "#articolo.codArt")
	})
	public void InsArticolo(Articoli articolo)
	{
		articoliRepository.save(articolo);
	}
}
