package com.pierciccio.webapp.service;

import com.pierciccio.webapp.entities.Promo;

import java.util.List;

public interface PromoService 
{
	public List<Promo> SelTutti();
	
	public Promo SelByIdPromo(String IdPromo);
	
	public Promo SelByAnnoCodice(String Anno, String Codice);
	
	List<Promo> SelPromoActive();
	
	public void InsPromo(Promo promo);
	
	public void DelPromo(Promo promo);
}
