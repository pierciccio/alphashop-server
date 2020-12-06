package com.pierciccio.webapp.service;

import com.pierciccio.webapp.entities.DettPromo;
import com.pierciccio.webapp.repository.PrezziPromoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PrezzoServiceImpl implements PrezzoService
{
	private static final Logger logger = LoggerFactory.getLogger(PrezzoService.class);

	@Autowired
	PrezziPromoRepository prezziPromoRep;

	@Override
	public Double SelPromoByCodArt(String CodArt) 
	{
		double retVal = 0;

		DettPromo promo =  prezziPromoRep.SelByCodArt(CodArt);
		
		if (promo != null)
		{
			if (promo.getTipoPromo().getIdTipoPromo() == 1)
			{
				try
				{
					retVal = Double.parseDouble(promo.getOggetto().replace(",", "."));
				}
				catch(NumberFormatException ex)
				{
					logger.warn(ex.getMessage());
				}
			}
			else  //TODO Gestire gli altri tipi di promozione
			{
				retVal = 0;
			}
		}
		else
		{
			logger.warn("Promo Articolo Assente!!");
		}

		return retVal;
	}
	
	@Override
	public Double SelPromoByCodArtAndFid(String CodArt) 
	{
		double retVal = 0;

		DettPromo promo =  prezziPromoRep.SelByCodArtAndFid(CodArt);
		
		if (promo != null)
		{
			if (promo.getTipoPromo().getIdTipoPromo() == 1)
			{
				try
				{
					retVal = Double.parseDouble(promo.getOggetto().replace(",", "."));
				}
				catch(NumberFormatException ex)
				{
					logger.warn(ex.getMessage());
				}
			}
			else  //TODO Gestire gli altri tipi di promozione
			{
				retVal = 0;
			}
		}
		else
		{
			logger.warn("Promo Articolo Fidelity Assente!!");
		}

		return retVal;
	}
	
	@Override
	public Double SelByCodArtAndCodFid(String CodArt, String CodFid) 
	{
		double retVal = 0;

		DettPromo promo =  prezziPromoRep.SelByCodArtAndCodFid(CodArt, CodFid);
		
		if (promo != null)
		{
			if (promo.getTipoPromo().getIdTipoPromo() == 1)
			{
				try
				{
					retVal = Double.parseDouble(promo.getOggetto().replace(",", "."));
				}
				catch(NumberFormatException ex)
				{
					logger.warn(ex.getMessage());
				}
			}
		}
		else
		{
			logger.warn(String.format("Promo Riservata Fidelity %s Assente!!", CodFid) );
		}

		return retVal;
	}
	
	@Override
	public void UpdOggettoPromo(String Oggetto, Long Id) 
	{
		// TODO Auto-generated method stub
	}
	
}
