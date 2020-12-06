package com.pierciccio.webapp.service;

import com.pierciccio.webapp.entities.Promo;
import com.pierciccio.webapp.repository.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PromoServiceImpl implements PromoService
{
	@Autowired
	PromoRepository promoRepository;

	@Override
	public List<Promo> SelTutti() 
	{
		return promoRepository.findAll();
	}

	@Override
	public Promo SelByIdPromo(String IdPromo) 
	{
		return promoRepository.findByIdPromo(IdPromo);
	}

	@Override
	public Promo SelByAnnoCodice(String Anno, String Codice) 
	{
		return promoRepository.findByAnnoAndCodice(Integer.parseInt(Anno), Codice);
	}

	@Override
	public List<Promo> SelPromoActive() 
	{
		return promoRepository.SelPromoActive();
	}

	@Override
	public void InsPromo(Promo promo) 
	{
		promoRepository.saveAndFlush(promo);
	}

	@Override
	public void DelPromo(Promo promo) 
	{
		promoRepository.delete(promo);
	}

}
